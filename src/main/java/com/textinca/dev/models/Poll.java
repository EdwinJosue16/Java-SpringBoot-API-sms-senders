package com.textinca.dev.models;

import java.sql.Timestamp;
import java.util.List;

public class Poll {
	
	private String companyEmailFK;
	private String campaignNameFK;
	private String keyWord;
	private String confirmationText;
	private String goodByeText;
	private Timestamp closeDate;


	public Timestamp getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
		this.closeDate = closeDate;
	}

	public String getCompanyEmailFK() {
		return companyEmailFK;
	}

	public void setCompanyEmailFK(String companyEmailFK) {
		this.companyEmailFK = companyEmailFK;
	}

	public String getCampaignNameFK() {
		return campaignNameFK;
	}

	public void setCampaignNameFK(String campaignNameFK) {
		this.campaignNameFK = campaignNameFK;
	}

	/**
	 * get keyword value
	 * @return keyWord
	 */
	public String getKeyWord() {
		return keyWord;
	}
	
	/**
	 * Set keyWord value
	 * @param keyWord
	 */
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	/**
	 * get confirmationText value
	 * @return confirmationText
	 */
	public String getConfirmationText() {
		return confirmationText;
	}
	
	/**
	 * Set confirmationText value
	 * @param confirmationText
	 */
	public void setConfirmationText(String confirmationText) {
		this.confirmationText = confirmationText;
	}

	/**
	 * get goodByeText value
	 * @return goodByeText
	 */
	public String getGoodByeText() {
		return goodByeText;
	}

	/**
	 * Set goodByeText value
	 * @param goodByeText
	 */
	public void setGoodByeText(String goodByeText) {
		this.goodByeText = goodByeText;
	}
}
