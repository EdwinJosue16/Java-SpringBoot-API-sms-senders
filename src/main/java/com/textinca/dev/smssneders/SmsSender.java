package com.textinca.dev.smssneders;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.models.SingleMessageEvent;
import com.textinca.dev.repositories.MessageEventForSendingRepository;
import com.textinca.dev.repositories.TablesNames;
import com.textinca.dev.services.MessageEventsForSendingService;

@Component
public abstract class SmsSender {
	
	//TODO Cambiar a nombres de los paises respectivos y no su codigo
	public static final String COSTA_RICA_PREFIX = "506";
	public static final String HONDURAS_PREFIX = "504";
	public static final String CANADA_PREFIX = "1";
	public static final String CLARO_API_KEY = "cpBWGPqIyqulLihQJpDPAZpmdFhYLMHP"; // esto seria el username en CommunicationChannelSMS, no requiere password
	public static final String SUCCESS = "success"; //mensaje enviado por claro entregado
	public static final String FAIL = "fail"; //mensaje fallido
	public static final String SENT = "sent"; //mensaje enviado por nosotros pero no se sabe si se entrego
	
	protected  RestTemplate restClient;
	
	public SmsSender()
	{
		this.restClient =  new RestTemplate();
	}
	
	protected abstract void doSend(SingleMessageEvent event, String messageToSend);
	
	@Async
	public void sendSms(MessageEventForSending messageEvents) 
	{
		try 
		{
			messageEvents
			.getEvents()
	        .parallelStream() 
	        .forEach( 
	        		event -> { 
	        			this.doSend(event,messageEvents.getMessageToSend());
	        		}); 
		}
		catch(Exception error)
		{
			System.out.println("Sender error: " + error);
		
		}
	}
	
}
