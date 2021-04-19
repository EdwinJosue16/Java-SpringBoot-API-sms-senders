package com.textinca.dev.models;

import org.jooq.Record;

/**
 * Contains ContactsList model methods
 */
public class ContactsList {
	private String status;
	private String name;
	private String companyEmailPK;
	private String phoneNumberPK;
	private String aniversaryDate;
	private String reminderDate;

	public static String ENABLED = "Enabled";
	public static String DISABLED = "Disabled";

	/**
	 * Constructor
	 * 
	 * @param status
	 * @param name
	 * @param companyEmailPK
	 * @param phoneNumberPK
	 */
	public ContactsList(String status, String name, String companyEmailPK, String phoneNumberPK, String aniversaryDate, String reminderDate) {
		this.status = status;
		this.name = name;
		this.companyEmailPK = companyEmailPK;
		this.phoneNumberPK = phoneNumberPK;
		this.aniversaryDate = aniversaryDate;
		this.reminderDate = reminderDate;
	}
	
	/**
	 * Constructor 
	 * @param recordValues - Values to set in a ContactsList
	 */
	public ContactsList(Record recordValues)
	{
		this.setStatus( (String) recordValues.getValue("status") );
		this.setName( (String) recordValues.getValue("name") );
		this.setPhoneNumberPK( (String) recordValues.getValue("phoneNumberPK") );
		this.setCompanyEmailPK( (String) recordValues.getValue("companyEmailPK") );
		if (recordValues.getValue("aniversaryDate") != null)
			this.setAniversaryDate((String) recordValues.getValue("aniversaryDate").toString());
		if (recordValues.getValue("reminderDate") != null)
			this.setReminderDate((String) recordValues.getValue("reminderDate").toString());
	}
	

	/**
	 * Get status value
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status value
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get companyEmailPK value
	 * 
	 * @return companyEmailPK
	 */
	public String getCompanyEmailPK() {
		return companyEmailPK;
	}

	/**
	 * Set companyEmailPK value
	 * 
	 * @param companyEmailPK
	 */
	public void setCompanyEmailPK(String companyEmailPK) {
		this.companyEmailPK = companyEmailPK;
	}

	/**
	 * Get phoneNumberPK value
	 * 
	 * @return phoneNumberPK
	 */
	public String getPhoneNumberPK() {
		return phoneNumberPK;
	}

	/**
	 * Set phoneNumberPK value
	 * 
	 * @param phoneNumberPK
	 */
	public void setPhoneNumberPK(String phoneNumberPK) {
		this.phoneNumberPK = phoneNumberPK;
	}

	/**
	 * Get name value
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name value
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get name aniversaryDate
	 * 
	 * @return aniversaryDate
	 */
	public String getAniversaryDate() {
		return aniversaryDate;
	}

	/**
	 * Set aniversaryDate value
	 * 
	 * @param aniversaryDate
	 */
	public void setAniversaryDate(String aniversaryDate) {
		this.aniversaryDate = aniversaryDate;
	}

	/**
	 * Get name reminderDate
	 * 
	 * @return reminderDate
	 */
	public String getReminderDate() {
		return reminderDate;
	}

	/**
	 * Set reminderDate value
	 * 
	 * @param reminderDate
	 */
	public void setReminderDate(String reminderDate) {
		this.reminderDate = reminderDate;
	}

}
