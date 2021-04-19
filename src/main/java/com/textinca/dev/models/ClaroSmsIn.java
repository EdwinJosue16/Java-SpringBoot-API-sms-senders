package com.textinca.dev.models;

public class ClaroSmsIn extends SmsInBase{
	
	private String type;
	private String msisdn; 
	private String message;
	private String datetime;
	private String status; //unread
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsisdn() {
		return msisdn;
	}
	@Override
	public String getPhoneNumber() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
