package com.textinca.dev.models;

public class ContactModel {
	
	ContactCard contactCard;
	ContactsList contactsList;
	
	public ContactModel(ContactCard contactCard, ContactsList contactsList){
		this.contactCard = contactCard;
		this.contactsList = contactsList;
	}
	
	public ContactModel() {
		// TODO Auto-generated constructor stub
	}

	public ContactCard getContactCard() {
		return contactCard;
	}
	public void setContactCard(ContactCard contactCard) {
		this.contactCard = contactCard;
	}
	public ContactsList getContactsList() {
		return contactsList;
	}
	public void setContactsList(ContactsList contactsList) {
		this.contactsList = contactsList;
	}

}
