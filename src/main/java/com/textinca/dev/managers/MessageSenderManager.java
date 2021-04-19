package com.textinca.dev.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.smssneders.ClaroCRSender;
import com.textinca.dev.smssneders.SinchCanadaSender;
import com.textinca.dev.smssneders.SmsSender;
import com.textinca.dev.smssneders.TigoHondurasSender;


@Component
public class MessageSenderManager {
	
	@Autowired ClaroCRSender claroSender;
	@Autowired TigoHondurasSender tigoSender;
	@Autowired SinchCanadaSender sinchSender;
	
	public void sendMessage(String companysCountryPhonePrefix, MessageEventForSending messageEvent)
	{
		//TODO el parametro a recibir debe ser el nombre del pais asociado a la empresa que creo los messagaEvents
		SmsSender provider = chooseSMSProviderByCountry(companysCountryPhonePrefix);
		provider.sendSms(messageEvent);
	}
	
	private SmsSender chooseSMSProviderByCountry(String companysCountryPhonePrefix)
	{
		//TODO este switch debe ser con base al nombre del pais, ya que el phone prefix no es unico
		SmsSender provider = null;
		switch(companysCountryPhonePrefix)
		{
			case SmsSender.COSTA_RICA_PREFIX:
				provider = claroSender;
				break;
			case SmsSender.HONDURAS_PREFIX:
				provider = tigoSender;
				break;
			case SmsSender.CANADA_PREFIX:
				provider = sinchSender;
				break;
		}
		return provider;
	}
}
