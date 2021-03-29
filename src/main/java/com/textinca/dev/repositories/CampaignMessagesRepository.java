package com.textinca.dev.repositories;

import static com.textinca.dev.configs.CampaignMessagesConstants.REVISION_STATUS_PENDING;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.CampaignMessageToSend;

import static com.textinca.dev.configs.CampaignMessagesConstants.REVISION_STATUS_OK;

@Repository
public class CampaignMessagesRepository {
	
	private static final String GET_SCHEDULED_CAMPAIGNS = "{call GET_SCHEDULED_CAMPAIGNS(?,?,?)}";
		
	@Autowired
	private DatabaseConnector dbConnector;
	
	public List<CampaignMessageToSend> getScheduledCampaign(
					String scheduleType,
					String eventType,
					String intervalBetweenShipments)
	{
			Condition conditions = this
									.buildScheduleConditionBy(
											scheduleType, 
											eventType,
											intervalBetweenShipments);
			Query builtQuery = dbConnector
								.getFactory()
								.select(
									field("companyEmailFK")
									.as("associatedCompanyEmail"),
									field("campaignName")
									.as("campaignName"),
									field("mainText")
									.as("textToSend")
								)
								.from(table(name(TablesNames.CAMPAIGN_MESSAGE)))
								.where(conditions);
			
			return 	dbConnector
					.getFactory()
					.fetch(builtQuery.getSQL())
					.into(CampaignMessageToSend.class);
	}
	
	public List<CampaignMessageToSend> getScheduledCampaignSP(
			String scheduleType,
			String eventType,
			String intervalBetweenShipments)
	{

		CallableStatement statement = dbConnector.prepareCall(GET_SCHEDULED_CAMPAIGNS);
		List<CampaignMessageToSend> campaignsToSend = new ArrayList<CampaignMessageToSend>();

		try 
		{
			statement.setString(1, scheduleType);
			statement.setString(2, eventType);
			statement.setString(3,intervalBetweenShipments);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				campaignsToSend.add(
						new CampaignMessageToSend(
								result.getString("companyEmailFK"),
								result.getString("campaignName"),
								result.getString("mainText")
						));
			}
		} 
		catch (SQLException error) 
		{
			System.out.println("Error while executing SP GET SCHEDULED CAMPAIGNS: " + error);
		}
		return campaignsToSend;
	}
	
	public int markCampaignStatusAsAReviewed(CampaignMessageToSend campaign)
	{
		
		return  dbConnector
				.getFactory()
				.update(table(name(TablesNames.CAMPAIGN_MESSAGE)))
				.set(field("revisionStatus"), REVISION_STATUS_OK)
				.where(
					field("companyEmailFK").eq(campaign.getAssociatedCompanyEmail())
					.and(
						field("campaignName").eq(campaign.getCampaignName())
					)
				
				)
				.execute();
	}
	
	private Condition buildScheduleConditionBy(
					String scheduleType,
					String eventType,
					String intervalBetweenShipments)
	{
		Condition condition = 
		field(name("revisionStatus")).eq(REVISION_STATUS_PENDING)
		.and(
			field(name("scheduleType")).eq(scheduleType)
		.and(
			field(name("eventType")).eq(eventType))		
		.and(
			field(name("eventType")).eq(eventType))
		.and(
			field(name("intervalBetweenShipments")).eq(intervalBetweenShipments))
		);
		return condition;
	}
}
