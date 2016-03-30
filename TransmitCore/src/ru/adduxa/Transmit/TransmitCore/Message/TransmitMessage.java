package ru.adduxa.Transmit.TransmitCore.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adduxa on 15.02.2016.
 */
public class TransmitMessage {
	private final String lobbyId;
	private final String sender;
	private final ArrayList<String> tags;
	private final String text;

	public TransmitMessage(String lobbyId, String sender, String messageText) {
		this.lobbyId = lobbyId;
		this.sender = sender;
		this.text = messageText;
		this.tags = new ArrayList<>();
		Matcher m = Pattern.compile("#\\w\\w+").matcher(messageText);
		while(m.find()) {
			this.tags.add(m.group());
		}
	}

	public String getLobbyId() {
		return lobbyId;
	}

	public String getSender() {
		return sender;
	}
	public ArrayList<String> getTags() {
		return tags;
	}

	public String getText() {
		return text;
	}

}
