package com.textinca.dev.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.configs.DatabaseConnector;
import com.textinca.dev.models.MessageInformation;
import com.textinca.dev.models.Poll;
import com.textinca.dev.models.SmsInBase;
import com.textinca.dev.settings.StatusCodesSettings;
import com.textinca.dev.models.Raffle;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;
import org.jooq.Record;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jooq.Query;

@Component
public class ReceiveMessageRepository {
	
	@Autowired
	private DatabaseConnector dbConnector;
	
	/**
	 * Exist Poll
	 * true exist false not exist
	 * @param keyword
	 * @return
	 */
	public boolean existPoll(String keyword) {
		
		Query builtQuery = dbConnector.getFactory()
				.select(field("Poll.keyWordPK"))
				.from(table(name("Poll"))).where(field("Poll.keyWordPK").eq(keyword));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
		
	}
	
	/**
	 * Exist Raffle
	 * true exist false not exist
	 * @param keyword
	 * @return
	 */
	public boolean existRaffle(String keyword) {
		
		Query builtQuery = dbConnector.getFactory()
				.select(field("Raffle.entryKeyWordPK"))
				.from(table(name("Raffle"))).where(field("Raffle.entryKeyWordPK").eq(keyword));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
		
	}
	
	/**
	 * Insert Answer in data base
	 * @param keyword
	 * @param content
	 * @param phoneNumber
	 * @param nexReplyKey
	 * @return
	 */
	public int registerAnswer(String keyword, String content, String phoneNumber, int nexReplyKey) {
		MessageInformation provider = new MessageInformation(phoneNumber);
		int affectedRows = 0;
		Date date = new Date();
			Query builtQuery = dbConnector.getFactory()
					.insertInto(table(name("Answers")), 
							field(name("content")), 
							field(name("registerDate")),
							field(name("transmitterPhoneNumber")), 
							field(name("associatedReplyKey")), 
							field(name("nextReplyKey")),
							field(name("associatedKeyword")),
							field(name("sendingMessagesPathFK")),
							field(name("providerIdFK")),
							field(name("phonePrefixFK"))
							)
					.values(
							content,
							new Timestamp(date.getTime()),
							provider.getPhoneNumber(),
							nexReplyKey,
							nexReplyKey + 1,
							keyword,
							this.getSendingMessagePath(this.getProviderID(provider.getProvider()), provider.getPrefix()),
							this.getProviderID(provider.getProvider()),
							provider.getPrefix()
							);
			try {
				affectedRows = dbConnector.getFactory().execute(builtQuery);
			} catch (Exception error) {
				affectedRows = 0;
				System.out.println(error);
			}
		return affectedRows;
	}

	/**
	 * Get providerId by name
	 * @param providerName
	 * @return
	 */
	public int getProviderID(String providerName) {
		Query builtQuery = dbConnector.getFactory()
				.select(field(name("providerIdPK")))
				.from(table(name("CommunicationsProvidersCoreSMS")))
				.where(field("providerName").eq(providerName));

		Record result = null;
		try{
			result = this.buildResutlFromQuery(builtQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result != null)
			return (int) result.get("providerIdPK");
		return 0;
	}
	
	/**
	 * Get sendMessagePath by providerId and prefix
	 * @param providerID
	 * @param prefix
	 * @return
	 */
	public String getSendingMessagePath(int providerID, String prefix) {
		Query builtQuery = dbConnector.getFactory()
				.select(field(name("sendingMessagesPathPK")))
				.from(table(name("CommunicationChannelSMS")))
				.where(field("providerIdFK").eq(providerID))
				.and(field("phonePrefixFK").eq(prefix));

		Record result = null;
		try{
			result = this.buildResutlFromQuery(builtQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result != null)
			return (String) result.get("sendingMessagesPathPK");
		return "0";
	}
	
	/**
	 * Find the next question to send
	 * @param keyWord
	 * @param question
	 * @return
	 */
	public String questionTosend(String keyWord, int question) {
		Query builtQuery = dbConnector.getFactory()
				.select(field(name("question")))
				.from(table(name("Questions")))
				.where(field("keyWordFK").eq(keyWord))
				.and(field("idPK").eq(Integer.toString(question + 1)));

		Record result = null;
		try{
			result = this.buildResutlFromQuery(builtQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result != null) {
			String questionToSend = result.get("question") != null ? ((String) result.get("question") ) : null;
			if(questionToSend == null)
				return "";
			return Integer.toString(question + 1) + ". " + questionToSend;
		}
		return null;
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
		return (Record) dbConnector.getFactory().fetchOne(query);
	}
	
	/**
	 * Get Poll by Keyword
	 * @param keyWord
	 * @return
	 */
	public Poll getPoll(String keyWord) {

		Query builtQuery = dbConnector.getFactory()
				.select(
						field("Poll.confirmationText"),
						field("Poll.goodByeText"), 
						field("Poll.keyWordPK").as("keyWord"),
						field("Poll.campaignNameFK"),
						field("Poll.companyEmailFK"),
						field("Poll.closeDate"),
						field("Poll.type")
						)
				.from(table(name("Poll")))
				.where(field("Poll.keyWordPK").eq(keyWord));
		try {
			Poll poll = dbConnector.getFactory().fetchOne(builtQuery.getSQL()).into(Poll.class);
			return poll;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	/**
	 * Get Raffle by keyword
	 * @param keyWord
	 * @return
	 */
	public Raffle getRaffle(String keyWord) {

		Query builtQuery = dbConnector.getFactory()
				.select(
						field("Raffle.entryKeyWordPK"),
						field("Raffle.confirmationReplyText"), 
						field("Raffle.duplicateParticipantText"),
						field("Raffle.closeReplyText"),
						field("Raffle.campaignNameFK"),
						field("Raffle.companyEmailFK"),
						field("Raffle.closeDate")
						)
				.from(table(name("Raffle")))
				.where(field("Raffle.entryKeyWordPK").eq(keyWord));
		try {
			Raffle raffle = dbConnector.getFactory().fetchOne(builtQuery.getSQL()).into(Raffle.class);
			return raffle;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	/**
	 * Exist participant in Raffle
	 * true exist false not exist
	 * @param keyWord
	 * @param phoneNumber
	 * @return
	 */
	public boolean existParticipant(String keyWord, String phoneNumber) {
		Query builtQuery = dbConnector.getFactory()
				.select(field(name("phoneNumber")))
				.from(table(name("Suscriptor")))
				.where(field("entryKeyWordFK").eq(keyWord))
				.and(field("phoneNumber").eq(phoneNumber));

		Record result = this.buildResutlFromQuery(builtQuery);

		return result != null ? true : false;
	}
	
	/**
	 * Insert suscriptor or participant in Raffle
	 * @param raffle
	 * @param phoneNumber
	 * @return
	 */
	public int insertSuscriptor(Raffle raffle, String phoneNumber) {
		int affectedRows = 0;
		Date date = new Date();
		
			Query builtQuery = dbConnector.getFactory()
					.insertInto(table(name("Suscriptor")), 
							field(name("campaignNameFK")), 
							field(name("companyEmailFK")),
							field(name("entryKeyWordFK")), 
							field(name("phoneNumber")), 
							field(name("entryDate"))
							)
					.values(
							raffle.getCampaignNameFK(),
							raffle.getCompanyEmailFK(),
							raffle.getEntryKeyWordPK(),
							phoneNumber,
							new Timestamp(date.getTime())
							);
			try {
				affectedRows = dbConnector.getFactory().execute(builtQuery);
			} catch (Exception error) {
				affectedRows = 0;
				System.out.println(error);
			}
		return affectedRows;
	}
	
	/**
	 * Insert MessageEventLog
	 * @param companyEmail
	 * @param campaignName
	 * @param phoneNumber
	 * @return
	 */
	public int registerEventLog(String companyEmail, String campaignName, String phoneNumber) {
		
		int affectedRows = 0;
		Timestamp date = new Timestamp(System.currentTimeMillis());
		Query builtQuery = dbConnector.getFactory()
				.insertInto(table(name("MessageEventLog")), 
						field(name("companyEmailFK")),
						field(name("campaignNameFK")), 
						field(name("phoneNumberFK")), 
						field(name("registerDate")),
						field(name("shippingDate")))
				.values(
						companyEmail, 
						campaignName,
						phoneNumber, 
						date, 
						date);
		try {
			affectedRows = dbConnector.getFactory().execute(builtQuery);
		} catch (Exception error) {
			System.out.println("An error has occured while you try to add messageEventLog: " + error.toString());
		}
		return affectedRows;
	}
	
	/**
	 * Increment 1 the value of members in a poll
	 * @param poll
	 * @return
	 */
	public int addPollMember(Poll poll) {
		int affectedRows = 0;
		Query builtQuery = dbConnector.getFactory()
				.select(field(name("members")))
				.from(table(name("Poll")))
				.where(field("keyWordPK").eq(poll.getKeyWord()))
				.and(field("campaignNameFK").eq(poll.getCampaignNameFK()))
				.and(field("companyEmailFK").eq(poll.getCompanyEmailFK()));

		Record result = null;
		try{
			result = this.buildResutlFromQuery(builtQuery);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(result != null) {
			int members = result.get("members") == null ? 0 : ((int) result.get("members"));
			Query updateQuery = dbConnector.getFactory()
					.update(table(name("Poll")))
					.set(field(name("members")), members + 1)
					.where(field("keyWordPK").eq(poll.getKeyWord()))
					.and(field("campaignNameFK").eq(poll.getCampaignNameFK()))
					.and(field("companyEmailFK").eq(poll.getCompanyEmailFK()));

			try {
				affectedRows = dbConnector.getFactory().execute(updateQuery);
			} catch (Exception error) {
				System.out.println("An error has occured while you try to update contact list: " + error.toString());
			}
		}
		return affectedRows;
	}
	
	/**
	 * Get questions by keyword
	 * 
	 * @param keyword
	 * @return list of questions
	 */
	public List<String> getQuestions(String keyWord) {
		Query builtQuery = dbConnector.getFactory().select(field(name("question"))).from(table(name("Questions")))
				.where(field("keyWordFK").eq(keyWord));

		Result<Record> result = dbConnector.getFactory().fetch(builtQuery.getSQL());

		List<String> questions = new ArrayList<String>();
		for (Record values : result) {
			String question = (String) values.get("question");
			if (question != null) {
				questions.add(question);
			}
		}
		return questions;
	}

	
	
}
