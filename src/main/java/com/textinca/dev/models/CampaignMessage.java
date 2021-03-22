package com.textinca.dev.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import static com.textinca.dev.configs.CampaignMessagesConstants.JSON_PROPERTY_FOR_LISTNAMES_SP;
public class CampaignMessage {
	
	private String associatedCompanyEmail;
	private String type;
	private List<String> contactListNames;

	
	public CampaignMessage()
	{
		this.contactListNames = new ArrayList<String>();
	}

	public String getType() {
		return type;
	}
	
	public void setType(String subClassType) {
		this.type = subClassType;
	}
	
	public String getAssociatedCompanyEmail() {
		return associatedCompanyEmail;
	}

	public void setAssociatedCompanyEmail(String associatedCompanyEmail) {
		this.associatedCompanyEmail = associatedCompanyEmail;
	}
	
	public List<String> getContactListNames() {
		return contactListNames;
	}

	public void setContactListNames(List<String> contactListNames) {
		this.contactListNames = contactListNames;
	}
	
	public String covertListNamesIntoStoreProcedureParameter()
	{
		JSONObject json = new JSONObject();
		json.put(JSON_PROPERTY_FOR_LISTNAMES_SP, this.getContactListNames());
		List<JSONObject> listNamesForStoreProcedure = new ArrayList<JSONObject>();
		listNamesForStoreProcedure.add(json);
		return listNamesForStoreProcedure.toString();
	}
}
