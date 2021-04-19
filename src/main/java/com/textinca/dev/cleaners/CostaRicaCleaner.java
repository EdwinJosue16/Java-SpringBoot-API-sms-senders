package com.textinca.dev.cleaners;

import java.util.regex.Pattern;

import com.textinca.dev.settings.ContactsSettings;

/** Specific class to CleanContacts from Costa Rica
 */
public class CostaRicaCleaner extends PhoneCleaner {

	/**
	 * Give the Costa Rica's phone a standard format
	 * @param phone - contact phone number
	 */
	public String clean(String phone) {
		// Just keep numbers
		String numberRefined = phone.replaceAll("[^0-9]", "");
		// regex starts with 5,6,7,8 followed by 7 numbers
		if (numberRefined.length() == ContactsSettings.COSTA_RICA_PHONE_NUMBER_LENGTH
				&& Pattern.matches("^[5-8]\\d{7}$", numberRefined)) {
			return numberRefined;
		}
		// ^(506)?[5-8]\d{7}$ regex starts with 506 followed by a number (5,6,7,8) and 7
		// numbers
		else if (Pattern.matches("^(506)?[5-8]\\d{7}$", numberRefined)) {
			return numberRefined.substring(3);
		}
		return "-1";
	}
}
