package com.textinca.dev.models;

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