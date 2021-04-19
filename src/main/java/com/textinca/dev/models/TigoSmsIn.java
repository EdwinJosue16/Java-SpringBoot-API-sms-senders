package com.textinca.dev.models;

public class TigoSmsIn extends SmsInBase{

	private String Mobile; 
	private String Message;
	
	public TigoSmsIn(String phoneNumber, String message) {
		this.Mobile = phoneNumber;
		this.Message = message;
	}
	public String getMobile() {
		return this.Mobile;
	}
	@Override
	public String getPhoneNumber() {
		return this.Mobile;
	}
	
	public void setMobile(String mobile) {
		this.Mobile = mobile;
	}
	@Override
	public String getMessage() {
		return this.Message;
	}
	public void setMessage(String message) {
		this.Message = message;
	}
	
}
