package com.textinca.dev.models;

import java.util.ArrayList;
import java.util.List;

public class MessageEventForSending {
	
	public class SingleMessageEvent
	{
		private String destinationPhoneNumber;
		private long code;
		
		public SingleMessageEvent(String destinationPhoneNumber, long code) 
		{
			this.destinationPhoneNumber = destinationPhoneNumber;
			this.code = code;
		}

		public String getDestinationPhoneNumber() {
			return destinationPhoneNumber;
		}

		public void setDestinationPhoneNumber(String destinationPhoneNumber) {
			this.destinationPhoneNumber = destinationPhoneNumber;
		}

		public long getCode() {
			return code;
		}

		public void setCode(long code) {
			this.code = code;
		}
		
	}
	
	private static final int UNIQUE_EVENT = 0;
	
	private String messageToSend;
	private List<SingleMessageEvent> events;
	
	
	public MessageEventForSending(String messageToSend) {
		this.messageToSend = messageToSend;
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
	
	public String getOnlyOneEventDestinationNumber()
	{
		return this.events.get(UNIQUE_EVENT).destinationPhoneNumber;
	}
	
	public long getOnlyOneEventCode()
	{
		return this.events.get(UNIQUE_EVENT).code;
	}
	
	public boolean hasOnlyOneEvent()
	{
		return events.size() == 1;
	}
}
