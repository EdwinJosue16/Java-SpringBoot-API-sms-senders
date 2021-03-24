package com.textinca.dev.models;

import java.util.ArrayList;
import java.util.List;

public class MessageEventForSending {
	
	private String companysCountryPhonePrefix;
	private String messageToSend;
	private List<SingleMessageEvent> events;
	
	
	public MessageEventForSending(String messageToSend) {
		this.messageToSend = messageToSend;
		this.events = new ArrayList<SingleMessageEvent>();
	}
	
	public MessageEventForSending() {
		this.events = new ArrayList<SingleMessageEvent>();
	}

	public String getMessageToSend() {
		return messageToSend;
	}
	
	public void setMessageToSend(String messageToSend) {
		this.messageToSend = messageToSend;
	}
	
	public void addEvent(String destinationPhoneNumber, long eventCode)
	{
		events.add( new SingleMessageEvent(destinationPhoneNumber, eventCode));
	}
	
	public List<SingleMessageEvent> getEvents()
	{
		return events;
	}
	
	public boolean hasOnlyOneEvent()
	{
		return events.size() == 1;
	}

	public void setEvents(List<SingleMessageEvent> events) {
		this.events = events;
	}

	public String getCompanysCountryPhonePrefix() {
		return companysCountryPhonePrefix;
	}

	public void setCompanysCountryPhonePrefix(String companysCountryPhonePrefix) {
		this.companysCountryPhonePrefix = companysCountryPhonePrefix;
	}
}
