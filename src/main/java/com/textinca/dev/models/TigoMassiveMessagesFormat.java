package com.textinca.dev.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.textinca.dev.models.MessageEventForSending.SingleMessageEvent;

public class TigoMassiveMessagesFormat {
	
	private class AddresseeListData
	{
		public String mobile;
		public String url;
		
		public AddresseeListData(String mobile, String url) {
			this.mobile = mobile;
			this.url = url;
		}
		
		public JSONObject toJSON()
		{
			JSONObject json = new JSONObject();
			json.put("mobile", this.mobile);
			json.put("url", this.url);
			return json;
		}
	}
	
	private String country; //Prefix
	private String messageFormat; // must be non-flash
	private String message; // text
	private List <JSONObject> addresseeList; //contacts to send
	
	public TigoMassiveMessagesFormat()
	{
		addresseeList = new ArrayList <JSONObject>();
	}
	
	public void fillAddresseeList( MessageEventForSending messageEvents)
	{
		for(SingleMessageEvent event : messageEvents.getEvents())
		{
			appendNewDestination(event);
		}
	}
	
	public void appendNewDestination(SingleMessageEvent event)
	{
		addresseeList.add(
				new AddresseeListData(
					event.getDestinationPhoneNumber(),
					null // URL is not necessary
				).toJSON()
			);
	}
	
	public String toJSON()
	{
		JSONObject json = new JSONObject();
		json.put("country", this.country);
		json.put("message", this.message);
		json.put("messageFormat", this.messageFormat);
		json.put("addresseeList", this.addresseeList);
		return json.toString();
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getMessageFormat() {
		return messageFormat;
	}
	
	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
