package com.textinca.dev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.repositories.CampaignMessagesRepository;

import static com.textinca.dev.configs.CampaignMessagesConstants.*;

@Service
public class CampaignMessagesService {
	
	@Autowired
	CampaignMessagesRepository campaignsRepo;
	
	public void markCampaignAsReviewed(CampaignMessageToSend campaign)
	{
		campaignsRepo.markCampaignStatusAsAReviewed(campaign);
		//TODO HANDLE returned affected-rows
	}
	
	public List<CampaignMessageToSend> getScheduledNonRecurringCampaigns()
	{
		return campaignsRepo.getScheduledCampaign(
				SCHEDULE_TYPE_LATER, 
				EVENT_TYPE_NON_RECURRING, 
				INTERVAL_BETWEEN_SHIPMENTS_NONE
			);
	}
	
	public List<CampaignMessageToSend> getAnniversaries()
	{
		return getRecurrentCamapigns(INTERVAL_BETWEEN_SHIPMENTS_ANNUAL);
	}
	
	public List<CampaignMessageToSend> getDailyCampaigns()
	{
		return getRecurrentCamapigns(INTERVAL_BETWEEN_SHIPMENTS_DAILY);
	}
	
	public List<CampaignMessageToSend> getMonthlyCampaigns()
	{
		return getRecurrentCamapigns(INTERVAL_BETWEEN_SHIPMENTS_MONTHLY);
	}
	
	public List<CampaignMessageToSend> getQuaterlyCampaigns()
	{
		return getRecurrentCamapigns(INTERVAL_BETWEEN_SHIPMENTS_QUARTERLY);
	}
	public List<CampaignMessageToSend> getAnnualCampaigns()
	{
		return getRecurrentCamapigns(INTERVAL_BETWEEN_SHIPMENTS_ANNUAL);
	}
	
	private List<CampaignMessageToSend> getRecurrentCamapigns(String interval)
	{
		return campaignsRepo.getScheduledCampaign( 
				SCHEDULE_TYPE_LATER, 
				EVENT_TYPE_RECURRENT, 
				interval
			);
	}
	
}
