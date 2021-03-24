package com.textinca.dev.repositories;

import static org.jooq.impl.DSL.field;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;


import java.util.List;

import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.models.SingleMessageEvent;


@Component
public class MessageEventForSendingRepository {
	
	@Autowired
	private DatabaseConnector dbConnector;
	
	public MessageEventForSending getEventsOfCampaingToSendByCampaign(CampaignMessageToSend campaign)
	{
		
		this.dbConnector = new DatabaseConnector();
		MessageEventForSending eventsForSending = new MessageEventForSending();
		
		eventsForSending.setCompanysCountryPhonePrefix(
				findComapanysCuountryPhonePrefix(campaign.getAssociatedCompanyEmail())
		);
		
		eventsForSending.setMessageToSend(
				getOnlyMainTextByCampaign(campaign)
		);
		
		eventsForSending.setEvents(
				getSingleMessageEventsFilterBy(campaign.getAssociatedCompanyEmail(), campaign.getCampaignName())	
		);
		
		return eventsForSending ;
	}
	
	private String getOnlyMainTextByCampaign(CampaignMessageToSend campaign)
	{
		Query builtQuery = dbConnector.getFactory()
				.select(field("mainText"))
				.from(table(name(TablesNames.CAMPAIGN_MESSAGE)))
				.where(
						field(TablesNames.CAMPAIGN_MESSAGE+".companyEmailFK")
						.eq(campaign.getAssociatedCompanyEmail())
						.and(	
							field(TablesNames.CAMPAIGN_MESSAGE+".campaignName")
							.eq(campaign.getCampaignName())	
						)
				);
		return 	dbConnector
				.getFactory()
				.fetchOne(builtQuery.getSQL())
				.into(String.class);
	}
	
	private List<SingleMessageEvent> getSingleMessageEventsFilterBy(String companyEmail, String campaignName)
	{
		Query builtQuery = dbConnector.getFactory()
				.select(
						field("phoneNumberFK")
						.as("destinationPhoneNumber"),
						field("code")
						.as("code")
				)
				.from(table(name(TablesNames.MESSAGE_EVENT_LOG)))
				.where(	
					field(TablesNames.MESSAGE_EVENT_LOG+".companyEmailFK")
					.eq(companyEmail)
					.and(	
						field(TablesNames.MESSAGE_EVENT_LOG+".campaignNameFK")
						.eq(campaignName)	
					)
				);
		return 	dbConnector
				.getFactory()
				.fetch(builtQuery.getSQL())
				.into(SingleMessageEvent.class);
	}
	
	private String findComapanysCuountryPhonePrefix(String companyEmail)
	{
		Query builtQuery = dbConnector.getFactory()
				.select(
						field("phonePrefixFK")
				)
				.from(table(name(TablesNames.COMPANIES)))
				.where(	
					field(TablesNames.COMPANIES+".companyEmailPK")
					.eq(companyEmail)
				);
		return dbConnector
				.getFactory()
				.fetchOne(builtQuery.getSQL())
				.into(String.class);
	}
}
