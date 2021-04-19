package com.textinca.dev.models;

import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.cleaners.CleanFactory;
import com.textinca.dev.settings.ContactsSettings;

@Component
public class MessageInformation {
	
	private String provider;
	private String prefix;
	private String phoneNumber;
	
	public MessageInformation()
	{
	}

	public MessageInformation(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		this.setCommunicationChanelInfo();
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}


	public String getPrefix() {
		return prefix;
	}


	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}


	public void setCommunicationChanelInfo() {
		CleanFactory clean = new CleanFactory();
		//Canada matcher
		if (Pattern.matches("^(1)?[2-9]\\d{9}$", this.phoneNumber)) {
			this.setProvider(ContactsSettings.SINCH_PROVIDER);
			this.setPrefix(ContactsSettings.CANADA);
		}//Costa Rica matcher
		else if (Pattern.matches("^(506)?[5-8]\\d{7}$", this.phoneNumber)) {
			this.setProvider(ContactsSettings.CLARO_PROVIDER);
			this.setPrefix(ContactsSettings.COSTA_RICA);
		}// Honduras matcher
		else if (Pattern.matches("^(504)?[3789]\\d{7}$", this.phoneNumber)) {
			this.setProvider(ContactsSettings.TIGO_PROVIDER);
			this.setPrefix(ContactsSettings.HONDURAS);
		}
		
		this.setPhoneNumber(clean.clean(this.prefix, this.phoneNumber));
		
	}

}
