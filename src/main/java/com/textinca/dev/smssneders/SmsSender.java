package com.textinca.dev.smssneders;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.models.SingleMessageEvent;

@Component
public abstract class SmsSender {
	
	public static final String COSTA_RICA_PREFIX = "506";
	public static final String HONDURAS_PREFIX = "504";
	public static final String CANADA_PREFIX = "1";
	
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
