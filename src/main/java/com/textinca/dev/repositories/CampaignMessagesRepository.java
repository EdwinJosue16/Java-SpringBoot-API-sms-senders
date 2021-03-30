package com.textinca.dev.repositories;

//
//import static org.jooq.impl.DSL.field;
//import static org.jooq.impl.DSL.name;
//import static org.jooq.impl.DSL.table;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//
//import org.jooq.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.CampaignMessageToSend;


@Repository
public class CampaignMessagesRepository {
	
	private static final String GET_SCHEDULED_CAMPAIGNS = "{call GET_SCHEDULED_CAMPAIGNS(?,?,?)}";
	@Autowired
	private DatabaseConnector dbConnector;
	
	public List<CampaignMessageToSend> getScheduledCampaigns(
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
}
