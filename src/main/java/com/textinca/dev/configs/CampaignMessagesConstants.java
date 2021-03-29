package com.textinca.dev.configs;

public class CampaignMessagesConstants {
	
	//Actions to do when new campaign is created
	public static final int SAVE_AND_SEND = 0;
	public static final int SAVE_AND_SCHEDULE = 1;
	
	//Schedule information
	public static final String EVENT_TYPE_RECURRENT = "recurrent";
	public static final String EVENT_TYPE_NON_RECURRING = "non-recurring";
	public static final String SCHEDULE_TYPE_LATER = "later";
	public static final String SCHEDULE_TYPE_IMMEDIATE = "immediate";
	public static final String REVISION_STATUS_OK = "reviewed";
	public static final String REVISION_STATUS_PENDING = "pending";
	public static final String INTERVAL_BETWEEN_SHIPMENTS_DAILY = "daily";
	public static final String INTERVAL_BETWEEN_SHIPMENTS_MONTHLY = "monthly";
	public static final String INTERVAL_BETWEEN_SHIPMENTS_QUARTERLY = "quarterly";
	public static final String INTERVAL_BETWEEN_SHIPMENTS_ANNUAL = "annual";
	public static final String INTERVAL_BETWEEN_SHIPMENTS_NONE = "none";
	
	//Types of campaigns
	public static final String MASSIVE = "Massive";
	public static final String REMINDER = "Reminder";
	public static final String ANIVERSARY = "Aniversary";
	public static final String POLL = "Poll";
	
	//Campaign and her events registering status
	public static final int SAVE_CAMPAIGN_ERROR = 99; //Conflict with campaign name or null fields
	public static final int SAVE_LESS_THAN_EXPECTED = 100; //Contacts with some problems in their fields
	public static final int SAVE_ALL_EXPECTED = 101; // Perfectly saved
	public static final int SAVE_NON_EVENT_REGISTERED = 102; //Campaign was created, but not has members
	
	//Database configurations
	public static final String JSON_PROPERTY_FOR_LISTNAMES_SP = "list_names";
	
}
