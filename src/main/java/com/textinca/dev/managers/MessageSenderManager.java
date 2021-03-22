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
		SmsSender provider = chooseSMSProviderByCountry(companysCountryPhonePrefix);
		provider.sendSms(messageEvent);
	}
	
	private SmsSender chooseSMSProviderByCountry(String companysCountryPhonePrefix)
	{
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
