package com.textinca.dev.models;

public class CampaignMessageToSend {
	
	private String associatedCompanyEmail;
	private String campaignName;
	private String textToSend;
	
	public CampaignMessageToSend()
	{
	}
	
	
	public CampaignMessageToSend(String associatedCompanyEmail, String campaignName, String textToSend) {
		this.associatedCompanyEmail = associatedCompanyEmail;
		this.campaignName = campaignName;
		this.textToSend = textToSend;
	}



	public String getAssociatedCompanyEmail() {
		return associatedCompanyEmail;
	}

	public void setAssociatedCompanyEmail(String associatedCompanyEmail) {
		this.associatedCompanyEmail = associatedCompanyEmail;
	}

	
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getTextToSend() {
		return textToSend;
	}

	public void setTextToSend(String textToSend) {
		this.textToSend = textToSend;
	}
}
