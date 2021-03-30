package com.textinca.dev.smssneders;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.textinca.dev.models.SingleMessageEvent;

public class ClaroCRSender extends SmsSender {
	
	private static final String CLARO_BASE_URL = "https://notificame.claro.cr/api/http/send_to_contact";
	//TODO obtener estos valores de la base de datos (la tabla que los contiene es CommunicationChannelSMS)
	private static final String CLARO_API_KEY = "cpBWGPqIyqulLihQJpDPAZpmdFhYLMHP"; // esto seria el username en CommunicationChannelSMS, no requiere password
	
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
			checkAnswer(answer);
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
	
	private void checkAnswer(ResponseEntity<String> answer)
	{
		//TODO actualizar el estado de envio de este mensaje a sent y poner el codigo de estado con el mismo valor del code de single message event
		//System.out.println("************"+answer.toString());
	}
}
