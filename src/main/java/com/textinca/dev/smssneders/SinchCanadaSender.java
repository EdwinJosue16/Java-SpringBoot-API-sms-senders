package com.textinca.dev.smssneders;


import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sinch.xms.ApiConnection;
import com.sinch.xms.SinchSMSApi;
import com.sinch.xms.api.MtBatchTextSmsResult;
import com.sinch.xms.api.ReportType;
import com.textinca.dev.models.SingleMessageEvent;
import com.textinca.dev.repositories.MessageEventForSendingRepository;

@Component
public class SinchCanadaSender extends SmsSender  {
	//TODO obtener estos valores de la base de datos (la tabla que los contiene es CommunicationChannelSMS)
	private static final String SERVICE_PLAN_ID = "PeopleConn_RA"; // este seria el username
	private static final String TOKEN = "008ca0cac06e4c6b8605d90ddc7abb9a"; // este seria el password
	private static final String URL_CALLBACK = "https://www.pconnection.net/Sinch/WS/sinchDLR.php"; // este seria el dlr
	private static final String SENDER = "24470"; // este seria el sending path
	private ApiConnection conn;
	
	@Autowired MessageEventForSendingRepository sendingRepo;
	
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
			this.sendingRepo.updateMessageEventLog(event.getCode(), SENT); 
			checkAnswer(batch, event.getCode());
		}
		catch(Exception error)
		{
			System.out.println("Error sinch provider: " + error);
		}
	}
		
	private void checkAnswer(MtBatchTextSmsResult batch, Long code)
	{
		System.out.println("********" + batch.toString());
		if(!batch.canceled()) {
			this.sendingRepo.updateMessageEventLog(code, SUCCESS); 
		}else {
			this.sendingRepo.updateMessageEventLog(code, FAIL); 
		}
		//actualizar codigo de estado y estado como enviado cuando se llega a este punto y con base al batch
	}

}
