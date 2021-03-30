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

import static com.textinca.dev.configs.DateTimeConstants.ONE_TIME_EACH_30_MINUTES;

import java.util.List;

import static com.textinca.dev.configs.CampaignMessagesConstants.*;
@Component
public class ScheduledMessagesReviewer {
	
	@Autowired 
	private MessageEventsForSendingService eventsService;
	
	@Autowired
	private CampaignMessagesService campaignsService;
	
	@Autowired 
	private MessagesTransmitter transmitter;
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledMessagesReviewer.class);
		
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_30_MINUTES)
	public void checkScheduledNonRecurringEvents()
	{
		log.info("checkScheduledNonRecurringEvents");
		List<CampaignMessageToSend> nonRecurringScheduledCampaigns = 
				campaignsService
				.getScheduledNonRecurringCampaigns();
		sendAndReScheduleMessageEvents(nonRecurringScheduledCampaigns, INTERVAL_BETWEEN_SHIPMENTS_NONE);
	}
		
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_30_MINUTES)
	public void checkDailyEvents()
	{
		log.info("checkDailyEvents");
		List<CampaignMessageToSend> dailyScheduledCampaigns = 
				campaignsService
				.getDailyCampaigns();
		sendAndReScheduleMessageEvents(dailyScheduledCampaigns, INTERVAL_BETWEEN_SHIPMENTS_DAILY);
	}
	
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_30_MINUTES)
	public void checkMothlyEvents()
	{
		log.info("checkMothlyEvents");
		List<CampaignMessageToSend> monthlyScheduledCampaigns = 
				campaignsService
				.getMonthlyCampaigns();
		sendAndReScheduleMessageEvents(monthlyScheduledCampaigns, INTERVAL_BETWEEN_SHIPMENTS_MONTHLY);
	}
	
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_30_MINUTES)
	public void checkQuaterlyEvents()
	{
		log.info("checkQuaterlyEvents");
		List<CampaignMessageToSend> quaterlyScheduledCampaigns = 
				campaignsService
				.getQuaterlyCampaigns();
		sendAndReScheduleMessageEvents(quaterlyScheduledCampaigns,INTERVAL_BETWEEN_SHIPMENTS_QUARTERLY);
	}
	
	
	@Async
	@Scheduled(fixedRate = ONE_TIME_EACH_30_MINUTES)
	public void checkAnnualEvents()
	{
		log.info("checkAnnualEvents");
		List<CampaignMessageToSend> annualScheduledCampaigns = 
				campaignsService
				.getAnnualCampaigns();
		sendAndReScheduleMessageEvents(annualScheduledCampaigns, INTERVAL_BETWEEN_SHIPMENTS_ANNUAL);
	}
	
	private void sendAndReScheduleMessageEvents(List<CampaignMessageToSend> scheduledCampaigns, String intervalBetweenShipments)
	{
		scheduledCampaigns
		.parallelStream()
        .forEach( 
			campaign -> { 
				MessageEventForSending sentEvents = transmitter.doImmediateSend(campaign);
				if(!intervalBetweenShipments.equals(INTERVAL_BETWEEN_SHIPMENTS_NONE))
				{
					eventsService.reScheduleCampaignEvents(sentEvents, intervalBetweenShipments);
				}
			}); 
	}
	
	
}
