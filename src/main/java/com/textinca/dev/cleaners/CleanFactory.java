package com.textinca.dev.cleaners;
import org.springframework.stereotype.Component;

import com.textinca.dev.settings.ContactsSettings;

/**
 * Factory to manage cleaners from different countries prefixPhone number
 */
@Component
public class CleanFactory {
	
	/**
	 * Gives format to a number from a specific country cleaner
	 * @param phonePrefix
	 * @param phoneNumber
	 * @return phoneNumber - phoneNumber Formatted 
	 */
	public String clean(String phonePrefix, String phoneNumber) {
		
		PhoneCleaner phoneCleaner = null;
		switch(phonePrefix) {
		
		case ContactsSettings.COSTA_RICA:
			phoneCleaner = new CostaRicaCleaner();
			break;
		case ContactsSettings.HONDURAS:
			phoneCleaner = new HondurasCleaner();
			break;
			
		case ContactsSettings.CANADA:
			phoneCleaner = new CanadaCleaner();
			break;
			
		default:
			return "-1";
		}
		
		return phoneCleaner.clean(phoneNumber);
	}

}
