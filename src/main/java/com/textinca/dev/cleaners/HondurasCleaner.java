package com.textinca.dev.cleaners;

import java.util.regex.Pattern;

import com.textinca.dev.settings.ContactsSettings;

/** Specific class to CleanContacts from Honduras
 */
public class HondurasCleaner extends PhoneCleaner {

	/**
	 * Give the Honduras phone a standard format
	 * @param phone - contact phone number
	 */
	public String clean(String phone) {		
		// Just keep numbers
		String numberRefined = phone.replaceAll("[^0-9]", "");
		// regex starts with 3,7,8,9 followed by 7 numbers
		if (numberRefined.length() == ContactsSettings.HONDURAS_PHONE_NUMBER_LENGTH
				&& Pattern.matches("^[3789]\\d{7}$", numberRefined)) {
			return numberRefined;
		}
		// ^(504)?[5-8]\d{7}$ regex starts with 506 followed by a number (3,7,8,9) and 7
		// numbers
		else if (Pattern.matches("^(504)?[3789]\\d{7}$", numberRefined)) {
			return numberRefined.substring(3);
		}
		return "-1";
	}
}
