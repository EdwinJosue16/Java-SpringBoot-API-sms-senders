package com.textinca.dev.cleaners;

import java.util.regex.Pattern;

import com.textinca.dev.settings.ContactsSettings;

/**
 * Specific class to CleanContacts from Canada
 */
public class CanadaCleaner extends PhoneCleaner {

	/**
	 * Give the Canada's phone a standard format
	 * 
	 * @param phone - contact phone number
	 */
	public String clean(String phone) {
		// Just keep numbers
		String numberRefined = phone.replaceAll("[^0-9]", "");
		// regex starts with  (2 to 9) followed by 9 numbers
		if (numberRefined.length() == ContactsSettings.CANADA_PHONE_NUMBER_LENGTH
				&& Pattern.matches("^[2-9]\\d{9}$", numberRefined)) {
			return numberRefined;
		}
		// ^(q)?[2-9]\d{7}$ regex starts with 1 followed by a number (2 to 9) and 9
		// numbers
		else if (Pattern.matches("^(1)?[2-9]\\d{9}$", numberRefined)) {
			return numberRefined.substring(1);
		}
		return "-1";
	}

}
