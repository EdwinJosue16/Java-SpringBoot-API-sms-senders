package com.textinca.dev.managers;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.textinca.dev.loaders.ContactLoader;
import com.textinca.dev.models.CampaignMessageToSend;
import com.textinca.dev.models.MessageInformation;
import com.textinca.dev.models.Poll;
import com.textinca.dev.models.Raffle;
import com.textinca.dev.models.SmsInBase;
import com.textinca.dev.repositories.ReceiveMessageRepository;
import com.textinca.dev.utilities.MessagesTransmitter;

@Component
public class MessageReceiverManager {
	
	private static int INITIAL_ANSWER = 1;
	private static int CLOSE_POLL_ANSWER = 2;
	private static int OPEN_POLL_ANSWER = 3;
	private static int FIRST_QUESTION = 0;
	private static String CLOSE_POLL = "close";
	
	@Autowired ReceiveMessageRepository receiveRepo;
	@Autowired MessagesTransmitter transmitter;
	@Autowired ContactLoader contactLoader;
	
	/**
	 * Get phoneNumber and message 
	 * @param smsIn - base @SmsInBase to get phone and message from different objects 
	 */
	public void answerMessage(SmsInBase smsIn) {
		MessageInformation messageInfo = new MessageInformation(smsIn.getPhoneNumber());
		this.processText(smsIn.getMessage(), messageInfo.getPhoneNumber());
	}
	
	/**
	 * Define what to do if receives initial message (One Word)
	 * ClosePollMessage (Two Words)
	 * OpenPollMessage (Three or More Words)
	 * @param text
	 * @param phoneNumber
	 */
	public void processText(String text, String phoneNumber) {
		
		String[] processText = text.split(" ");
		String keyWord = processText[0];
		
		if(processText.length == INITIAL_ANSWER) { //Apenas se considera participar a un raffle o a un poll mensaje inicial
			this.answerInitialMessage(keyWord, phoneNumber);
		}// si son 2 en la posicion 1 viene la respuesta, si son 3 en la posicion 2 viene la respuesta
		else if(processText.length == CLOSE_POLL_ANSWER) { // responde a un poll cerrado
			String message = processText[1];
			this.answerClosePollMessage(keyWord, phoneNumber, message);
		}
		else if(processText.length >= OPEN_POLL_ANSWER) { // responde a un poll
			String message = processText[2];
			int actualQuestion = Integer.parseInt(processText[1]);
			if (processText.length > OPEN_POLL_ANSWER) { //Si el mensaje es mas grande que solamente 3 palabras considera despues de 3 el mensaje de respuesta
				for(int index = 3; index < processText.length; ++index) {
					String word = " " + processText[index];
					message +=  word;
				}
			}
			System.out.println("MESSAGE: " + message);
		    this.answerOpenPollMessage(keyWord, phoneNumber, message, actualQuestion);
		}
	}
	
	/**
	 * Register Answer
	 * If keyword if in Poll send welcome message and firs question if poll is not close and add a member
	 * if Poll is close send closeMessage
	 * If keyword is in raffle add a participant and answer with a welcome message 
	 * if Raffle is close send closeMessage
	 * @param keyWord
	 * @param phoneNumber
	 */
	private void answerInitialMessage(String keyWord, String phoneNumber) {
		int questionToSend = FIRST_QUESTION; 
		if(this.existPoll(keyWord)) { //Considerar que no haya cerrado ya no porque solo van a existir nombre unicos
			Poll poll = this.receiveRepo.getPoll(keyWord);
			this.registerAnswer(keyWord, keyWord, phoneNumber, questionToSend);
			String question = this.receiveRepo.questionTosend(keyWord, questionToSend);

			if(this.isClose(poll.getCloseDate())) {
				this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), "La encuesta Cerro", phoneNumber); //Close "Message"
			}
			else if(poll.getType().equals(CLOSE_POLL)) {

				String message = poll.getConfirmationText();
				message += this.getQuestions(this.receiveRepo.getQuestions(keyWord));
				this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), message, phoneNumber); //envia primera pregunta de un poll cerrado y sus opciones
				this.receiveRepo.addPollMember(poll);
			}
			else {
					this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), poll.getConfirmationText(), phoneNumber);
					this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), question, phoneNumber); //envia primera pregunta de un poll
					this.receiveRepo.addPollMember(poll);
			}
		}else if(this.existRaffle(keyWord)) { 
			Raffle raffle = this.receiveRepo.getRaffle(keyWord);
			this.registerAnswer(keyWord, keyWord, phoneNumber, questionToSend);
			if(existParticipant(keyWord, phoneNumber)) {
				this.sendMessage(raffle.getCompanyEmailFK(), raffle.getCampaignNameFK(), raffle.getDuplicateParticipantText(), phoneNumber);
			}else if(this.isClose(raffle.getCloseDate())) {
				this.sendMessage(raffle.getCompanyEmailFK(), raffle.getCampaignNameFK(), raffle.getCloseReplyText(), phoneNumber);
			}else {
				this.receiveRepo.insertSuscriptor(raffle, phoneNumber); //agregar miembro al raffle
				this.sendMessage(raffle.getCompanyEmailFK(), raffle.getCampaignNameFK(), raffle.getConfirmationReplyText(), phoneNumber);
			}
		}
	}
	
	private String getQuestions(List<String> questions) {
		String message = "";
		for(int i = 0; i < questions.size(); ++i) {
			message += " " + questions.get(i);
		}
		return message;
	}

	/**
	 * Register Answer
	 * Send goodBye text because close Poll just have one answer
	 * if poll is close send CloseMessage
	 * @param keyWord
	 * @param phoneNumber
	 * @param message
	 */
	private void answerClosePollMessage(String keyWord, String phoneNumber, String message) {
		if(this.existPoll(keyWord)) { //Considerar que no haya cerrado
			Poll poll = this.receiveRepo.getPoll(keyWord);
			int actualQuestion = FIRST_QUESTION;
			this.registerAnswer(keyWord, message, phoneNumber, actualQuestion); // si la pregunta es cerrada solo va a registrar la respuesta
			if (this.isClose(poll.getCloseDate())) {
				this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(),  "La encuesta Cerro",
						phoneNumber);
			} else {
				this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), poll.getGoodByeText(),
						phoneNumber);
			}
		}
	}
	
	/**
	 * find the next question and Send the question or send the goodBye message id there is not more questions 
	 * if poll is close send close message
	 * @param keyWord
	 * @param phoneNumber
	 * @param message
	 * @param actualQuestion
	 */
	private void answerOpenPollMessage(String keyWord, String phoneNumber, String message, int actualQuestion) {
		if(this.existPoll(keyWord)) { //Considerar que no haya cerrado
			Poll poll = this.receiveRepo.getPoll(keyWord);
			this.registerAnswer(keyWord, message, phoneNumber, actualQuestion); // si la pregunta es cerrada solo va a registrar la respuesta
			if (this.isClose(poll.getCloseDate())) {
				this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(),  "La encuesta Cerro",
						phoneNumber);
			} else {
				String question = this.receiveRepo.questionTosend(keyWord, actualQuestion);
				if (question != null)
					this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), question, phoneNumber);
				else
					this.sendMessage(poll.getCompanyEmailFK(), poll.getCampaignNameFK(), poll.getGoodByeText(),
							phoneNumber);
			}
		
		}
	}
	
	
	/**
	 * true if closeDate is not later than actual date 
	 * false if closeDate is later than actual date 
	 * @param closeDate
	 * @return
	 */
	private boolean isClose(Timestamp closeDate) {
		Timestamp actualDate = new Timestamp(System.currentTimeMillis());
		return !closeDate.after(actualDate);
	}

	/**
	 * Find if participant if already participating in raffle
	 * true exist false not exist
	 * @param keyWord
	 * @param phoneNumber
	 * @return
	 */
	private boolean existParticipant(String keyWord, String phoneNumber) {
		return this.receiveRepo.existParticipant(keyWord , phoneNumber);
	}

	/**
	 * Find if raffle exist
	 * true exist false not exist
	 * @param keyWord
	 * @return
	 */
	private boolean existRaffle(String keyWord) {
		return this.receiveRepo.existRaffle(keyWord);
	}

	/**
	 * Load Contact if doesnt exist
	 * register messageEventLog in order to register send message
	 * Create campaignMessage to send and send immediate message 
	 * @param companyEmail
	 * @param campaignName
	 * @param message
	 * @param phoneNumber
	 */
	private void sendMessage(String companyEmail, String campaignName, String message, String phoneNumber) {
		this.contactLoader.LoadOneContact(phoneNumber, companyEmail);
		this.receiveRepo.registerEventLog(companyEmail, campaignName, phoneNumber);
		CampaignMessageToSend campaignToSend = new CampaignMessageToSend(companyEmail, campaignName, message);
		this.transmitter.doImmediateSend(campaignToSend);
	}

	/**
	 * Find if Poll exist 
	 * true exist false not exist
	 * @param keyWord
	 * @return
	 */
	private boolean existPoll(String keyWord) {
		return this.receiveRepo.existPoll(keyWord);
	}

	/**
	 * Register Answer 
	 * @param keyword
	 * @param content
	 * @param phoneNumber
	 * @param nextQuestion
	 */
	public void registerAnswer(String keyword, String content, String phoneNumber, int nextQuestion) {
		this.receiveRepo.registerAnswer(keyword, content, phoneNumber, nextQuestion);
	}
	
}
