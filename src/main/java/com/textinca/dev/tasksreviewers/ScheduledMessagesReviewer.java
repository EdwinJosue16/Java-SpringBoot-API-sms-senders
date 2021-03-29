package com.textinca.dev.tasksreviewers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.services.CampaignMessagesService;
import com.textinca.dev.services.MessageEventsForSendingService;
import com.textinca.dev.utilities.MessagesTransmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.textinca.dev.configs.DateTimeConstants.ONE_TIME_EACH_1_MINUTES;

import java.util.List;

@Component
public class ScheduledMessagesReviewer {
	
	
	
	@Autowired
	private CampaignMessagesService campaignsService;
	
	@Autowired 
	private MessagesTransmitter transmitter;
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledMessagesReviewer.class);
	
//	@Async
//	@Scheduled(fixedRate = ONE_TIME_EACH_2_MINUTES)
//	public void checkMassiveScheduledEvents()
//	{
//		log.info("Corriendo 2 minutos");
//	}
	
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_1_MINUTES)
	public void otro()
	{
		log.info("Corriendo cada minuto");
		//checkScheduledNonRecurringEvents();
	}
	
	
	private void checkScheduledNonRecurringEvents()
	{
		List<CampaignMessageToSend> nonRecurringScheduledCampaigns = 
				campaignsService
				.getScheduledNonRecurringCampaigns();
		for(CampaignMessageToSend campaign : nonRecurringScheduledCampaigns)
		{
			transmitter.doImmediateSend(campaign);
		}
	}
	
	
}
