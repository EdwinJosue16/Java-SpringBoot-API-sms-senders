package com.textinca.dev.models;

import java.sql.Timestamp;

public class SingleMessageEvent
{
	private String destinationPhoneNumber;
	private long code;
	private Timestamp shippingDate;
	
	public SingleMessageEvent(String destinationPhoneNumber, long code) 
	{
		this.destinationPhoneNumber = destinationPhoneNumber;
		this.code = code;
	}
	
	public SingleMessageEvent(String destinationPhoneNumber, long code, Timestamp shippingDate) 
	{
		this.destinationPhoneNumber = destinationPhoneNumber;
		this.code = code;
		this.shippingDate = shippingDate;
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

	public Timestamp getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Timestamp shippingDate) {
		this.shippingDate = shippingDate;
	}
	
}