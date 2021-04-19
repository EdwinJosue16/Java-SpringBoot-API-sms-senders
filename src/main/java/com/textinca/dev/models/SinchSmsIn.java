package com.textinca.dev.models;

import java.sql.Timestamp;

public class SinchSmsIn extends SmsInBase{
	
	private String id;
	private String from;
	private String to;
	private String body;
	private String type;
	private Timestamp received_at;
	private String operator_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	
	@Override
	public String getPhoneNumber() {
		return from;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getReceived_at() {
		return received_at;
	}
	public void setReceived_at(Timestamp received_at) {
		this.received_at = received_at;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	@Override
	public String getMessage() {
		return body;
	}
	

}
