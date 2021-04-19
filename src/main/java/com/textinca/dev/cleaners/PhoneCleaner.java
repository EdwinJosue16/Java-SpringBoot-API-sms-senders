package com.textinca.dev.cleaners;

/** Abstract class to CleanContacts
 */
public abstract class PhoneCleaner {
 
	/**
	 * Give the phone a standard format
	 * @param phone - contact phone number
	 */
	 public abstract String clean(String phone);
}
