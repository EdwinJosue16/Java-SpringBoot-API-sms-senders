package com.textinca.dev.managers;

import org.springframework.stereotype.Component;

import com.textinca.dev.models.MessageEventForSending;
import com.textinca.dev.smssneders.ClaroCRSender;
import com.textinca.dev.smssneders.SinchCanadaSender;
import com.textinca.dev.smssneders.SmsSender;
import com.textinca.dev.smssneders.TigoHondurasSender;


@Component
public class MessageSenderManager {
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
				provider = new ClaroCRSender();
				break;
			case SmsSender.HONDURAS_PREFIX:
				provider = new TigoHondurasSender();
				break;
			case SmsSender.CANADA_PREFIX:
				provider = new SinchCanadaSender();
				break;
		}
		return provider;
	}
}
