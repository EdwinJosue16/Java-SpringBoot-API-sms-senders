package com.textinca.dev;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.managers.MessageSenderManager;

@SpringBootTest
class SendingMessagesModuleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void sendOneCR()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Hola, es de prueba sendOneCR ");
		messageEvent.addEvent("84295799", 196500); 
		new MessageSenderManager().sendMessage("506",messageEvent);
	}
	
	@Test
	public void sendMassiveCR()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Hola, soy Edwin, estoy probando algo confirme recibido por whatsapp ");
		messageEvent.addEvent("84062535", 10);
		messageEvent.addEvent("84295799", 12); 
		messageEvent.addEvent("84295799", 12); 
		messageEvent.addEvent("85863197", 13); 
		messageEvent.addEvent("84403105", 14); 
		messageEvent.addEvent("89916089", 15); 
		messageEvent.addEvent("85398252", 16); 
		new MessageSenderManager().sendMessage("506",messageEvent);
	}
	
	@Test
	public void sendMultipleSameSmsToSameNumberCR()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Mismo mensaje varias veces");
		for(int times=10;times<20;++times)
		{
			messageEvent.addEvent("84295799", times);
		}
		new MessageSenderManager().sendMessage("506",messageEvent);
	}
	
	@Test
	public void sendMultipleSmsToSameNumberCR()
	{
		for(int times=0;times<10;++times)
		{
			MessageEventForSending messageEvent = new MessageEventForSending("Casi el mismo mensaje"+times);
			messageEvent.addEvent("84295799", times);
			new MessageSenderManager().sendMessage("506",messageEvent);
		}

	}
	
	@Test
	public void sendOneHN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending(".");
		messageEvent.addEvent("96687930", 17709); 
		new MessageSenderManager().sendMessage("504",messageEvent);
	}
	
	@Test
	public void sendMassiveHN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Hola es Edwin, estoy en prueba de sendMassiveHN");
		messageEvent.addEvent("96687930", 17709); 
		messageEvent.addEvent("94807480", 17708);
		messageEvent.addEvent("96103154", 17708);
		messageEvent.addEvent("99395716", 17708);
		messageEvent.addEvent("99904033", 17708);
		messageEvent.addEvent("32816572", 17708);
		new MessageSenderManager().sendMessage("504",messageEvent);
	}
	
	@Test
	public void sendMultipleSameSmsToSameNumberHN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Mismo mensaje varias veces");
		for(int times=10;times<20;++times)
		{
			messageEvent.addEvent("96687930", times);
			messageEvent.addEvent("96103154", 17708);
		}
		new MessageSenderManager().sendMessage("504",messageEvent);
	}
	
	@Test
	public void sendMultipleSmsToSameNumberHN()
	{
		for(int times=10;times<20;++times)		{
			MessageEventForSending messageEvent = new MessageEventForSending("Casi el mismo mensaje"+times);
			messageEvent.addEvent("96687930", times);
			messageEvent.addEvent("96103154", 17708);
			new MessageSenderManager().sendMessage("504",messageEvent);
		}
	}
	
	@Test
	public void sendOneCN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Hola, es de prueba");
		messageEvent.addEvent(/*"5147567716"*/"23gggFF", 13409); 
		new MessageSenderManager().sendMessage("1",messageEvent);
	}
	
	@Test
	public void sendMassiveCN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Hola, es de prueba");
		messageEvent.addEvent("5147567716", 13409);
		messageEvent.addEvent("4189050874", 13409);
		new MessageSenderManager().sendMessage("1",messageEvent);
	}
	
	@Test
	public void sendMultipleSameSmsToSameNumberCN()
	{
		MessageEventForSending messageEvent = new MessageEventForSending("Mismo mensaje varias veces");
		for(int times=10;times<20;++times)
		{
			messageEvent.addEvent("5147567716", 13409);
			messageEvent.addEvent("4189050874", 13409);
		}
		new MessageSenderManager().sendMessage("1",messageEvent);
	}
	
	@Test
	public void sendMultipleSmsToSameNumberCN()
	{
		for(int times=10;times<20;++times)
		{
			MessageEventForSending messageEvent = new MessageEventForSending("Casi el mismo mensaje"+times);
			messageEvent.addEvent("5147567716", 13409);
			messageEvent.addEvent("4189050874", 13409);
			new MessageSenderManager().sendMessage("1",messageEvent);
		}
	}
	
}
