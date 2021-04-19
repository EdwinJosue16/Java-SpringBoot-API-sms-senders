package com.textinca.dev.loaders;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.models.ContactCard;
import com.textinca.dev.models.ContactModel;
import com.textinca.dev.models.ContactsList;
import com.textinca.dev.repositories.ContactsRepository;
import com.textinca.dev.utilities.DateTimeUtil;

/**
 * loader to insert a contact in data base
 */
@Component
public class ContactLoader{

	@Autowired ContactsRepository contactsRepository;
	/**
	 * Load a contact
	 * @param phoneNumber
	 * @param email - current user email 
	 */
	public void LoadOneContact(String phoneNumber, String email) {
		try {
			DateTimeUtil dateTimeUtil = new DateTimeUtil();
					String date = dateTimeUtil.getDateNow();
					ContactCard contactCard = new ContactCard("enabled", "", phoneNumber, null, date, email);
					ContactsList contactList = new ContactsList("enabled",  "Individuals", email, phoneNumber, null,
							 null);
					ContactModel contact = new ContactModel(contactCard, contactList);
					contactsRepository.insert(contact);
		} catch (Exception err) {
			System.out.println(err.toString());
		}
	}

}
