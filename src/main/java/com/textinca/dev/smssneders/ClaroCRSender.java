package com.textinca.dev.smssneders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.textinca.dev.models.SingleMessageEvent;
import com.textinca.dev.repositories.MessageEventForSendingRepository;

@Component
public class ClaroCRSender extends SmsSender {
	
	private static final String CLARO_BASE_URL = "https://notificame.claro.cr/api/http/send_to_contact";
	//TODO obtener estos valores de la base de datos (la tabla que los contiene es CommunicationChannelSMS)
	
	@Autowired MessageEventForSendingRepository sendingRepo;
	
	public ClaroCRSender()
	{
		super();
	}
	
	@Override
	public void doSend(SingleMessageEvent event, String messageToSend) {
		try
		{
			String urlForSendingMessage = buildRequestUrl(event, messageToSend);
			ResponseEntity<String> answer = restClient.exchange(
											urlForSendingMessage, 
											HttpMethod.GET, 
											null, //headers are not necessary
											String.class
										);
			//Mensaje enviado pero no entregado
			this.sendingRepo.updateMessageEventLog(event.getCode(), SENT); 
			checkAnswer(answer, event.getCode());
		}
		catch(Exception error)
		{
			System.out.println("Claro sender:" + error);
		}

	}
	
	private String buildRequestUrl(SingleMessageEvent singleMessageEvent, String messageToSend)
	{

		String url =
				  CLARO_BASE_URL
				  +"?msisdn="+COSTA_RICA_PREFIX+singleMessageEvent.getDestinationPhoneNumber()+"&"
				  +"message="+messageToSend+"&"
				  +"id="+singleMessageEvent.getCode()+"&"
				  +"api_key="+CLARO_API_KEY;
		return url;
	}
	
	private void checkAnswer(ResponseEntity<String> answer, Long code)
	{
		//Si se recibe el codigo correcto de entrega se actualiza a success sino a fail
		//TODO actualizar el estado de envio de este mensaje a sent y poner el codigo de estado con el mismo valor del code de single message event
		if(answer.getStatusCode() == HttpStatus.OK) {
			this.sendingRepo.updateMessageEventLog(code, SUCCESS); 
		}else {
			this.sendingRepo.updateMessageEventLog(code, FAIL); 
		}
	    System.out.println("************"+answer.toString());
	}
	
}
