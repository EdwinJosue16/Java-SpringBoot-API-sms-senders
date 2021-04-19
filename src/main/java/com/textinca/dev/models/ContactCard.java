package com.textinca.dev.models;

import org.jooq.Record;

/**
 * Contains ContactCard model methods 
 */
public class ContactCard {
	private String status;
    private String name;
	private String phoneNumberPK;
    private String email;
	private String lastModificationDate;
    private String companyEmailFK;
    
    public static String ENABLED = "Enabled";
    public static String DISABLED = "Disabled";
    
	/**
	 * Default constructor
	 */
    public ContactCard() {
    	
    }
    
	/**
	 * Constructor 
	 * @param recordValues - Values to set in a contactCard 
	 */
	public ContactCard(Record recordValues)
	{
		this.setStatus( (String) recordValues.getValue("status") );
		this.setName( (String) recordValues.getValue("name") );
		this.setPhoneNumberPK( (String) recordValues.getValue("phoneNumberPK") );
		this.setEmail( (String) recordValues.getValue("email") );
		this.setLastModificationDate( (String) recordValues.getValue("lastModificationDate").toString() );
		this.setCompanyEmailFK( (String) recordValues.getValue("companyEmailFK") );
	}
	
	
	/**
	 * Constructor
	 * @param status
	 * @param name
	 * @param phoneNumberPK
	 * @param associatedDate
	 * @param email
	 * @param lastModificationDate
	 * @param companyEmailFK
	 */
	public ContactCard(String status, String name, String phoneNumberPK, String email, String lastModificationDate, String companyEmailFK) {
		
		this.status = status;
		this.name = name;
		this.phoneNumberPK = phoneNumberPK;
		this.email = email;
		this.lastModificationDate = lastModificationDate;
		this.companyEmailFK = companyEmailFK;
	}

    
	/**
	 * Get status value
	 * @return status
	 */
    public String getStatus() {
		return status;
	}
    
	/**
	 * Set status value
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get name value
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name value
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get phoneNumberPK value
	 * @return phoneNumberPK
	 */
	public String getPhoneNumberPK() {
		return phoneNumberPK;
	}

	/**
	 * Set phoneNumberPK value
	 * @param phoneNumberPK
	 */
	public void setPhoneNumberPK(String phoneNumberPK) {
		this.phoneNumberPK = phoneNumberPK;
	}
	
	/**
	 * Get  email value
	 * @return  email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email value
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Get lastModificationDate value
	 * @return lastModificationDate
	 */
	public String getLastModificationDate() {
		return lastModificationDate;
	}
	
	/**
	 * Set lastModificationDate value
	 * @param lastModificationDate
	 */
	public void setLastModificationDate(String lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
	
	/**
	 * Get companyEmailFK value
	 * @return companyEmailFK
	 */
	public String getCompanyEmailFK() {
		return companyEmailFK;
	}
	
	/**
	 * Set companyEmailFK value
	 * @param companyEmailFK
	 */
	public void setCompanyEmailFK(String companyEmailFK) {
		this.companyEmailFK = companyEmailFK;
	}
}
