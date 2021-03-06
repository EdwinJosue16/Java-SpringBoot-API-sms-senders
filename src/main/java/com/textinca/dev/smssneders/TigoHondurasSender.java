package com.textinca.dev.smssneders;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.textinca.dev.models.SingleMessageEvent;
import com.textinca.dev.models.TigoMassiveMessagesFormat;
import com.textinca.dev.repositories.MessageEventForSendingRepository;

@Component
public class TigoHondurasSender extends SmsSender{
	
	private static final String AUTHORIZATION_TYPE = "Authorization";
	private static final int NON_FLASH_FORMAT = 1;
	private static final String TIGO_HN_POST_BASE_URL = "https://apismsi.aldeamo.com/SmsiWS/smsSendPost/";

	@Autowired MessageEventForSendingRepository sendingRepo;
	
	public TigoHondurasSender() 
	{
		super();
	}
	
	@Override
	protected void doSend(SingleMessageEvent event, String messageToSend) {
		
		try
		{
			HttpEntity<String> request = buildRequest(event, messageToSend);
			ResponseEntity <String> answer = restClient.exchange(
						TIGO_HN_POST_BASE_URL, 
						HttpMethod.POST, 
						request, 
						String.class
					);
			
			this.sendingRepo.updateMessageEventLog(event.getCode(), SENT); 
			checkAnswer(answer, event.getCode());
		}
		catch(Exception error)
		{
			System.out.println("Tigo sender:" + error);
		}
	}

	private HttpEntity<String> buildRequest(SingleMessageEvent event, String messageToSend)
	{
		String jsonRequest = builBodyOfRequest(event, messageToSend);
		String usernameAndPassword ="user"+":"+"password"; //TODO obtener estos valores de la base de datos (la tabla que los contiene es CommunicationChannelSMS)
		//hay un username and password para cada numero de tigo, se puede elegir uno aletorio para enviar los mensajes,
		HttpHeaders headers = new HttpHeaders();
		
		headers.add(
				AUTHORIZATION_TYPE, 
				"Basic "+ Base64.getEncoder().encodeToString(usernameAndPassword.getBytes())
		);
		
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new HttpEntity<String> (jsonRequest,headers);
	}
	
	private String builBodyOfRequest(SingleMessageEvent event, String messageToSend)
	{
		TigoMassiveMessagesFormat jsonRequest = new TigoMassiveMessagesFormat();
		jsonRequest.setCountry(HONDURAS_PREFIX);
		jsonRequest.setMessageFormat(Integer.toString(NON_FLASH_FORMAT));
		jsonRequest.setMessage(messageToSend);
		jsonRequest.appendNewDestination(event);
		return jsonRequest.toJSON();
	}
	
	private void checkAnswer(ResponseEntity <String> answer, Long code)
	{
		if(answer.getStatusCode() == HttpStatus.OK) {
			this.sendingRepo.updateMessageEventLog(code, SUCCESS); 
		}else {
			this.sendingRepo.updateMessageEventLog(code, FAIL); 
		}
		System.out.println("******" + answer.toString());
		
		//TODO tigo responde de inmediato un json con un codigo para el mensaje y su estado, actualizar estos valores en la db
	}
}
