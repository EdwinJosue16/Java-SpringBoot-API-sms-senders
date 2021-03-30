package com.textinca.dev.controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/receiving-side")
public class ReceiveMessagesController {


	@PostMapping("claroCR")
	public void receiveClaro(@RequestBody Object answer)
	{
		//TODO defininir el objeto que captura los mensajes entrantes enviadas al numero de claro y procesarlas
	}
	
	@PostMapping("sinchCA")
	public void receiveSinch(@RequestBody Object answer)
	{
		//TODO defininir el objeto que captura los mensajes entrantes enviadas al numero de SINCH y procesarlas
	}
	
	@PostMapping("tigoHN")
	public void receiveTigo(@RequestBody Object answer)
	{
		//TODO defininir el objeto que captura los mensajes entrantes enviadas al numero de TIGO y procesarlas
	}
	//TODO para tigo hay que definir 4 end points mas, uno por cada numero aprovisionado
}

