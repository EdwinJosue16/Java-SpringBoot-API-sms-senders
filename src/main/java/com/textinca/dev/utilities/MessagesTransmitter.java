package com.textinca.dev.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.managers.MessageSenderManager;
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.services.MessageEventsForSendingServie;

@Component
public class MessagesTransmitter {
	
	@Autowired
	MessageSenderManager messageSenderManager;
	
	@Autowired
	MessageEventsForSendingServie messageEventService;
	
	public void doImmediateSend(CampaignMessageToSend campaign)
	{
		MessageEventForSending eventsToSend = messageEventService
												.getImmediateEventsForSending(campaign);
		
		messageSenderManager.sendMessage(
					eventsToSend.getCompanysCountryPhonePrefix(), 
					eventsToSend
		);
	}
	
}
