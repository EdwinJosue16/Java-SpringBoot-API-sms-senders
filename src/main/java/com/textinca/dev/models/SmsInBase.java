package com.textinca.dev.models;

import org.springframework.stereotype.Component;

@Component
public abstract class SmsInBase {
	
	public abstract String getPhoneNumber();
	public abstract String getMessage();

}
