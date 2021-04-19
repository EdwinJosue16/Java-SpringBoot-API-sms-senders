package com.textinca.dev.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.textinca.dev.managers.MessageReceiverManager;
import com.textinca.dev.models.ClaroSmsIn;
import com.textinca.dev.models.SinchSmsIn;
import com.textinca.dev.models.SmsInBase;
import com.textinca.dev.models.TigoSmsIn;

@RestController
@RequestMapping("/receiving-side")
public class ReceiveMessagesController {

	@Autowired
	MessageReceiverManager receiverManager;

	@PostMapping("/claro-cr")
	public void receiveClaro(@RequestBody ClaroSmsIn answer) {
		SmsInBase claroSms = answer;
		this.receiverManager.answerMessage(claroSms);
		// TODO defininir el objeto que captura los mensajes entrantes enviadas al
		// numero de claro y procesarlas
	}

	@PostMapping("/sinch-ca")
	public void receiveSinch(@RequestBody SinchSmsIn answer) {
		SmsInBase SinchSms = answer;
		this.receiverManager.answerMessage(SinchSms);
		// TODO defininir el objeto que captura los mensajes entrantes enviadas al
		// numero de SINCH y procesarlas
	}

	@PostMapping("/tigo-hn")
	public void receiveTigo(@RequestBody String answer) {
		JSONObject json = new JSONObject(answer);
		
		if (json.has("Mobile") && json.has("Message")) {
			String phoneNumber = json.getString("Mobile");
			String message = json.getString("Message");
			SmsInBase TigoSms = new TigoSmsIn(phoneNumber, message);
			this.receiverManager.answerMessage(TigoSms);
		}
		// TODO defininir el objeto que captura los mensajes entrantes enviadas al
		// numero de TIGO y procesarlas
	}
	// TODO para tigo hay que definir 4 end points mas, uno por cada numero
	// aprovisionado
}
