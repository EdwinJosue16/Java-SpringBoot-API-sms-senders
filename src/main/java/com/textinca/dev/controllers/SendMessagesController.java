package com.textinca.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.utilities.MessagesTransmitter;
import com.textinca.dev.utilities.ResponseBuilder;

@RestController
@RequestMapping("/sending-side")
public class SendMessagesController {


	@Autowired MessagesTransmitter transmitter;
	
	
	@GetMapping("/send-one-message")
	public void getAllCompanyCampaigns()
	{
	}
	
	@PostMapping("send-campaign-messages")
	public ResponseEntity <CampaignMessageToSend > sendCampaignMessages(@RequestBody CampaignMessageToSend campaignToSend)
	{
		transmitter.doImmediateSend(campaignToSend);
		
		ResponseBuilder <CampaignMessageToSend> response = new ResponseBuilder <CampaignMessageToSend>();
		
		response.code = HttpStatus.ACCEPTED;
		response.content = campaignToSend;
		
		return response.buildResponse();
	}
	
}
