package com.textinca.dev.models;

import java.sql.Timestamp;

public class Raffle {
	
	 private String	entryKeyWordPK;
	 private String	duplicateParticipantText;
	 private String	confirmationReplyText;
	 private String	closeReplyText;
	 private String campaignNameFK;
	 private String companyEmailFK;
	 private Timestamp closeDate;


	public String getCampaignNameFK() {
		return campaignNameFK;
	}

	public void setCampaignNameFK(String campaignNameFK) {
		this.campaignNameFK = campaignNameFK;
	}

	public String getCompanyEmailFK() {
		return companyEmailFK;
	}

	public void setCompanyEmailFK(String companyEmailFK) {
		this.companyEmailFK = companyEmailFK;
	}

	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public String getEntryKeyWordPK() {
		return entryKeyWordPK;
	}

	public void setEntryKeyWordPK(String entryKeyWordPK) {
		this.entryKeyWordPK = entryKeyWordPK;
	}

	public String getDuplicateParticipantText() {
		return duplicateParticipantText;
	}

	public void setDuplicateParticipantText(String duplicateParticipantText) {
		this.duplicateParticipantText = duplicateParticipantText;
	}

	public String getConfirmationReplyText() {
		return confirmationReplyText;
	}

	public void setConfirmationReplyText(String confirmationReplyText) {
		this.confirmationReplyText = confirmationReplyText;
	}

	public String getCloseReplyText() {
		return closeReplyText;
	}

	public void setCloseReplyText(String closeReplyText) {
		this.closeReplyText = closeReplyText;
	}
	 
}
