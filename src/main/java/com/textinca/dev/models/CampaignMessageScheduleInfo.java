package com.textinca.dev.models;

import java.sql.Timestamp;

public class CampaignMessageScheduleInfo {
	

	private int anticipationDays;
	private String intervalBetweenShipments;
	private String revisionStatus;
	private Timestamp shippingDate;
	private String eventType;
	private String scheduleType;
	
	
	public CampaignMessageScheduleInfo()
	{
	}
	
	public int getAnticipationDays() {
		return anticipationDays;
	}
	public void setAnticipationDays(int anticipationDays) {
		this.anticipationDays = anticipationDays;
	}
	
	public String getIntervalBetweenShipments() {
		return intervalBetweenShipments;
	}
	
	public void setIntervalBetweenShipments(String intervalBetweenShipments) {
		this.intervalBetweenShipments = intervalBetweenShipments;
	}

	public String getRevisionStatus() {
		return revisionStatus;
	}
	
	public void setRevisionStatus(String revisionStatus) {
		this.revisionStatus = revisionStatus;
	}
	
	public Timestamp getShippingDate() {
		return shippingDate;
	}
	
	public void setShippingDate(Timestamp shippingDate) {
		this.shippingDate = shippingDate;
	}
	
	public String getEventType() {
		return eventType;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public String getScheduleType() {
		return scheduleType;
	}
	
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
}
