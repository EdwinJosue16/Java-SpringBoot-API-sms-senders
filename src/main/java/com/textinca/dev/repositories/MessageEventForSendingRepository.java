package com.textinca.dev.repositories;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.models.SingleMessageEvent;

import static com.textinca.dev.configs.MessageEventsConstans.SENDING_STATUS_PENDING;

@Component
public class MessageEventForSendingRepository {
	
	private final static String RESCHEDULE_MESSAGE_EVENTS = "{call RE-SCHEDULE_MESSAGE_EVENTS(?,?)}";
	
	@Autowired
	private DatabaseConnector dbConnector;
	
	
	public int reScheduleCampaign(MessageEventForSending events, String intervalBetweenShipments)
	{
		int affectedRows = 0;
		CallableStatement statement = dbConnector.prepareCall(RESCHEDULE_MESSAGE_EVENTS);
		if(statement!=null)
		{
			try 
			{
				statement.setString(0, intervalBetweenShipments);
				statement.setString(1, events.covertSingleEventsToProcedureParameter());
				affectedRows = statement.executeUpdate();
				statement.close();
			} 
			catch (SQLException error) 
			{
				System.out.println("While you try insert campaign events (re-schedule):" + error.toString());
				affectedRows = 0;
			}
		}	
		return affectedRows;
	}
	
	public MessageEventForSending getEventsFromCampaign(CampaignMessageToSend campaign)
	{
		MessageEventForSending eventsForSending = new MessageEventForSending();
		
		eventsForSending.setCompanysCountryPhonePrefix(
				findComapanysCuountryPhonePrefix(campaign.getAssociatedCompanyEmail())
		);
		
		eventsForSending.setMessageToSend(
				campaign.getTextToSend()
		);
		
		eventsForSending.setEvents(
				getSingleMessageEventsFilterBy(campaign.getAssociatedCompanyEmail(), campaign.getCampaignName())	
		);
		
		return eventsForSending ;
	}
	
	private List<SingleMessageEvent> getSingleMessageEventsFilterBy(String companyEmail, String campaignName)
	{
		Query builtQuery = dbConnector.getFactory()
				.select(
						field("phoneNumberFK")
						.as("destinationPhoneNumber"),
						field("code")
						.as("code"),
						field("shippingDate")
				)
				.from(table(name(TablesNames.MESSAGE_EVENT_LOG)))
				.where(	
					field(TablesNames.MESSAGE_EVENT_LOG+".companyEmailFK").eq(companyEmail)
					.and(	
						field(TablesNames.MESSAGE_EVENT_LOG+".campaignNameFK").eq(campaignName)
					.and(	
						field(TablesNames.MESSAGE_EVENT_LOG+".sendingStatus").eq(SENDING_STATUS_PENDING)))
					
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
