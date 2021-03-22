package com.textinca.dev.models;

import java.sql.Timestamp;

public class CampaignMessageGeneralInfo {
	
	private String campaignName;
	private Timestamp registerDate;
	private String mainText;
	private String sendingMessagesPath;
	private String description;
	private String status;
	private int members;
	
	public CampaignMessageGeneralInfo()
	{
	}
	
	public String getCampaignName() {
		return campaignName;
	}
	
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	public Timestamp getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}
	
	public String getMainText() {
		return mainText;
	}
	
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}
	
	public String getSendingMessagesPath() {
		return sendingMessagesPath;
	}
	
	public void setSendingMessagesPath(String sendingMessagesPath) {
		this.sendingMessagesPath = sendingMessagesPath;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) {
		this.members = members;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		
	}
}
