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
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.JwtObject;
import com.textinca.dev.models.MessageEventForSending;

@RestController
@RequestMapping("/sending-side")
public class SendMessagesController {

	@Autowired
	private MessageSenderManager senderManager;
	
	@GetMapping("/send-one-message")
	public void getAllCompanyCampaigns(
									@RequestHeader(AUTHORIZATION_TYPE) String requestedTokenHeader
								)
	{
		String companyEmail = new JwtObject(requestedTokenHeader).getEmail();
		MessageEventForSending messageEvent = new MessageEventForSending("Hola de parte de "+companyEmail+", es de prueba sendOneCR ");
		messageEvent.addEvent("84295799", 196800); 
		senderManager.sendMessage("506",messageEvent);
	}
	
	@PostMapping("send-campaign-messages")
	public ResponseEntity <String> sendCampaignMessages(@RequestBody CampaignMessageToSend campaignToSend)
	{
		String texto = "Desde "+campaignToSend.getCampaignName()+
					   ". De la empresa con correo " + campaignToSend.getAssociatedCompanyEmail()+
					   " diciendo " + campaignToSend.getTextToSend();
		
		MessageEventForSending messageEvent = new MessageEventForSending(texto);
		messageEvent.addEvent("84295799", 3453); 
		senderManager.sendMessage("506",messageEvent);
		
		return null;
	}
	
}
