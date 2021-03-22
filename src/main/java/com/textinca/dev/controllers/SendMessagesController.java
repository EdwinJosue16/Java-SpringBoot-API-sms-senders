package com.textinca.dev.controllers;

import static com.textinca.dev.configs.SecurityConstants.AUTHORIZATION_TYPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.textinca.dev.managers.MessageSenderManager;
import com.textinca.dev.models.CampaignMessage;
import com.textinca.dev.models.JwtObject;

@RestController
@RequestMapping("/sending-side")
public class SendMessagesController {

	@Autowired
	private MessageSenderManager senderManager;
	
	@GetMapping("/send-one-message")
	public String getAllCompanyCampaigns(
									@RequestHeader(AUTHORIZATION_TYPE) String requestedTokenHeader
								)
	{
		String companyEmail = new JwtObject(requestedTokenHeader).getEmail();
		return "Entro por token valido " + companyEmail;
	}
	
	@PostMapping("send-campaign-messages")
	public ResponseEntity <String> sendCampaignMessages(@RequestBody CampaignMessage campaignToSend)
	{
		return null;
	}
	
}
