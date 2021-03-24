package com.textinca.dev.models;

public class CampaignMessageToSend {
	
	private String associatedCompanyEmail;
	private String campaignName;
	
	public CampaignMessageToSend()
	{
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
}
