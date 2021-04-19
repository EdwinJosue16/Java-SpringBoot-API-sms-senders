package com.textinca.dev.repositories;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;


import org.jooq.Query;
import org.jooq.Record;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.ContactCard;
import com.textinca.dev.models.ContactModel;
import com.textinca.dev.models.ContactsList;
import com.textinca.dev.settings.StatusCodesSettings;
/**
 * Contains queries to manage contacts and lists of contacts in data base
 */
@Component
public class ContactsRepository  {

	@Autowired
	private DatabaseConnector dbConnector;

	/**
	 * Insert contact in data base
	 * 
	 * @param contact      - @ContactModel instance
	 * @return result - 1 the contact was inserted successfully -1 the contact was
	 *         not inserted
	 */
	public int insert(ContactModel contact) {

		int result = StatusCodesSettings.INCORRECT;
		if (!this.existContact(contact.getContactCard().getCompanyEmailFK(), contact.getContactCard().getPhoneNumberPK())) {
			this.insertNewContact(contact.getContactCard());
			result = StatusCodesSettings.CORRECT;
		}
		if (!this.existContactList(contact.getContactsList().getCompanyEmailPK(), contact.getContactsList().getName(), contact.getContactsList().getPhoneNumberPK())) {
			this.insertNewContactList(contact.getContactsList());
			result = StatusCodesSettings.CORRECT;
		}
		return result;
	}

	/**
	 * Method to determine if contact exists
	 * 
	 * @param companyEmailFK - email of current user
	 * @param phoneNumberPK - phone number to ask for
	 * @return result - true the contact exists false the contact doesn't exist
	 */
	public boolean existContact(String companyEmailFK, String phoneNumberPK) {
		Query builtQuery = dbConnector.getFactory().select(field(name("phoneNumberPK")), field(name("companyEmailFK")))
				.from(table(name("ContactCard"))).where(field("phoneNumberPK").eq(phoneNumberPK))
				.and(field("companyEmailFK").eq(companyEmailFK));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
	}

	/**
	 * Method to determine if name of contactsList exists
	 * 
	 * @param companyEmailPK - email of current user
	 * @param name - name of list
	 * @return result - true the list name exists false the list name doesn't exist
	 */
	public boolean existContactListName(String companyEmailPK,String name) {
		Query builtQuery = dbConnector.getFactory()
				.selectDistinct(
						field(name("name")))
				.from(
						table(name("ContactsList")))
				.where(
						field("name").eq(name))
				.and(
						field("companyEmailPK").eq(companyEmailPK));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
	}

	/**
	 * Method to determine if ContactsList exists
	 * 
	 * @param companyEmailPK - email of current user
	 * @param name - name of list
	 * @param phoneNumberPK - phone number to ask for
	 * @return result - true the ContactsList exists false the ContactsList doesn't
	 *         exist
	 */
	public boolean existContactList(String companyEmailPK, String name, String phoneNumberPK) {

		Query builtQuery = dbConnector.getFactory()
				.select(field(name("status")), field(name("name")), field(name("phoneNumberPK")),
						field(name("companyEmailPK")))
				.from(table(name("ContactsList"))).where(field("phoneNumberPK").eq(phoneNumberPK))
				.and(field("companyEmailPK").eq(companyEmailPK)).and(field("name").eq(name));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
	}

	/**
	 * Query to insert the contact to data base
	 * 
	 * @param contact - Contact @ContactCard instance
	 * @return result - 1 the Contact was inserted false the Contact was not
	 *         inserted
	 */
	public int insertNewContact(ContactCard contact) {
		int result = -1;
		Query insertContactCard = dbConnector.getFactory()
				.insertInto(table(name("ContactCard")), field(name("status")), field(name("name")),
						field(name("phoneNumberPK")), field(name("email")), field(name("lastModificationDate")),
						field(name("companyEmailFK")))
				.values(contact.getStatus(), contact.getName(), contact.getPhoneNumberPK(), contact.getEmail(),
						contact.getLastModificationDate(), contact.getCompanyEmailFK());
		try {
			int affectedRows = dbConnector.getFactory().execute(insertContactCard);
			result = affectedRows != 0 ? StatusCodesSettings.CORRECT : StatusCodesSettings.INCORRECT;
		} catch (Exception error) {
			System.out.println("An error has occured while you try insert new application user: " + error.toString());
		}
		return result;
	}

	/**
	 * Query to insert the @ContactsList to data base
	 * 
	 * @param contactsList - @ContactsList instance
	 * @return result - 1 the @ContactsList was inserted false the ContactList was not
	 *         inserted
	 */
	public int insertNewContactList(ContactsList contactsList) {
		int result = StatusCodesSettings.INCORRECT;
		Query insertContactList = dbConnector.getFactory()
				.insertInto(table(name("ContactsList")), field(name("status")), field(name("name")),
						field(name("companyEmailPK")), field(name("phoneNumberPK")), field(name("aniversaryDate")),
						field(name("reminderDate")))
				.values(contactsList.getStatus(), contactsList.getName(), contactsList.getCompanyEmailPK(),
						contactsList.getPhoneNumberPK(), contactsList.getAniversaryDate(),
						contactsList.getReminderDate());

		try {
			int affectedRows = dbConnector.getFactory().execute(insertContactList);
			result = affectedRows > 0 ? StatusCodesSettings.CORRECT : StatusCodesSettings.INCORRECT;
		} catch (Exception error) {
			System.out.println("An error has occured while you try insert new contact: " + error.toString());
		}
		return result;
	}
	
	/**
	 * Method to execute query and get a record
	 * 
	 * @param builtQuery - Query to execute
	 * @return @Record - result after executing the query if null there was not
	 *         results
	 */
	private Record buildResutlFromQuery(Query builtQuery) {
		String query = builtQuery.getSQL();
		return dbConnector.getFactory().fetchOne(query);
	}
}
