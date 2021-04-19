package com.textinca.dev.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Class to manage date methods
 */
@Component
public class DateTimeUtil {
	/** Method to obtain the current date
	 * @return currentTime 
	 */
	public String getDateNow() {
		
		Date dt = new java.util.Date();

		SimpleDateFormat sdf = 
		     new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		
		 return currentTime;
		
	}
	
	/**
	 * Covert the given Date to String 
	 * @param dt - date
	 * @return date - String date
	 */
	public String getDate(Date dt) {
		SimpleDateFormat sdf = 
			     new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dtTime = sdf.format(dt);
		
		 return dtTime;
	}
}
