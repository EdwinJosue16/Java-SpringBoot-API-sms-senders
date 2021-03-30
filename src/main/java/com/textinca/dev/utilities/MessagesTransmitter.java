package com.textinca.dev.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.managers.MessageSenderManager;
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.services.MessageEventsForSendingService;

@Component
public class MessagesTransmitter {
	
	@Autowired
	MessageSenderManager messageSenderManager;
	
	@Autowired
	MessageEventsForSendingService messageEventService;
	
	public MessageEventForSending doImmediateSend(CampaignMessageToSend campaign)
	{
		MessageEventForSending eventsToSend = messageEventService
												.getEventsForSending(campaign);
		
		messageSenderManager.sendMessage(
					eventsToSend.getCompanysCountryPhonePrefix(), 
					eventsToSend
		);
		
		return eventsToSend;
	}
	
}
