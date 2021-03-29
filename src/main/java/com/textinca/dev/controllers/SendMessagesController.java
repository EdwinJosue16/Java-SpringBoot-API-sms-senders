package com.textinca.dev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.repositories.CampaignMessagesRepository;
import com.textinca.dev.repositories.MessageEventForSendingRepository;
import com.textinca.dev.utilities.MessagesTransmitter;
import com.textinca.dev.utilities.ResponseBuilder;

@RestController
@RequestMapping("/sending-side")
public class SendMessagesController {


	@Autowired MessagesTransmitter transmitter;
	@Autowired MessageEventForSendingRepository repo;
	@Autowired CampaignMessagesRepository campaignRepo;
	
	@PostMapping("/send-one-message")
	public MessageEventForSending sendIndividualMessage(@RequestBody CampaignMessageToSend campaignToSend)
	{
		return null;
	}
	
	@PostMapping("send-campaign-messages")
	public ResponseEntity <CampaignMessageToSend> sendCampaignMessages(@RequestBody CampaignMessageToSend campaignToSend)
	{
		transmitter.doImmediateSend(campaignToSend);
		
		ResponseBuilder <CampaignMessageToSend> response = new ResponseBuilder <CampaignMessageToSend>();
		
		response.code = HttpStatus.ACCEPTED;
		response.content = campaignToSend;
		
		return response.buildResponse();
	}
	
	@GetMapping("/test")
	public List<CampaignMessageToSend> test()
	{
		return campaignRepo.getScheduledCampaignSP("immediate","non-recurring","none");
	}
	
}
