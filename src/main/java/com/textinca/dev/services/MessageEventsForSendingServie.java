package com.textinca.dev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.repositories.MessageEventForSendingRepository;

@Service
public class MessageEventsForSendingServie {
	
	@Autowired
	MessageEventForSendingRepository messageEventRepo;
	
	public MessageEventForSending getImmediateEventsForSending(CampaignMessageToSend campaign)
	{
		return messageEventRepo.getEventsOfCampaingToSendByCampaign(campaign);
	}
}
