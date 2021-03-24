package com.textinca.dev.smssneders;


import java.io.IOException;
import java.net.URI;

import com.sinch.xms.ApiConnection;
import com.sinch.xms.SinchSMSApi;
import com.sinch.xms.api.MtBatchTextSmsResult;
import com.sinch.xms.api.ReportType;
import com.textinca.dev.models.SingleMessageEvent;

public class SinchCanadaSender extends SmsSender  {
	
	private static final String SERVICE_PLAN_ID = "PeopleConn_RA";
	private static final String TOKEN = "008ca0cac06e4c6b8605d90ddc7abb9a";
	private static final String URL_CALLBACK = "https://www.pconnection.net/Sinch/WS/sinchDLR.php";
	private static final String SENDER = "24470";
	private ApiConnection conn;
	
	public SinchCanadaSender() {
		super();
		this.initiaizeConnection();
	}
	
	public void initiaizeConnection()
	{
		conn = 	ApiConnection.builder()
				.servicePlanId(SERVICE_PLAN_ID)
				.token(TOKEN)
				.start();
	}
	
	public void closeConnection()
	{
		try
		{
			conn.close();
			System.out.println("Error sinch provider: close connection");
		}
		catch(IOException error)
		{
			System.out.println("Error sinch provider (while trying close connection): " + error);
		}
	}
	
	
	@Override
	protected void doSend(SingleMessageEvent event, String messageToSend)
	{
		
		try {
			MtBatchTextSmsResult batch =
			          conn.createBatch(
			              SinchSMSApi.batchTextSms()
			              	  .sender(SENDER)
			                  .addRecipient(
			                		  CANADA_PREFIX +
			                		  event.getDestinationPhoneNumber()
			                   )
			                  .body(messageToSend)
			                  .deliveryReport(ReportType.FULL)
			                  .callbackUrl(URI.create(URL_CALLBACK))
			                  .build());
			checkAnswer(batch);
		}
		catch(Exception error)
		{
			System.out.println("Error sinch provider: " + error);
		}
	}
		
	private void checkAnswer(MtBatchTextSmsResult batch)
	{
		//System.out.println("Successfully sent batch " + batch.toString());
	}

}
