package ru.adduxa.Transmit.TransmitCore.Sender;


import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by adduxa on 15.02.2016.
 */
public abstract class TransmitSender implements Observer, ITransmitSender {
	private final String name;
	private final String moduleName;
	private final String chatId;
	private TransmitTags tags;

	protected TransmitSender(String name, String moduleName, String chatId, TransmitTags tags) {
		this.name = name;
		this.moduleName = moduleName;
		this.chatId = chatId;
		setTags(tags);
	}

	public void update(Observable o, Object arg) {
		TransmitMessage message = (TransmitMessage) arg;
		if(!message.getLobbyId().equals(getLobbyId())
				&& getTags().isMatching(message.getTags())
				) {
			sendMessage(message);
		}
	}

	public String getModuleName() {
		return moduleName;
	}
	public String getChatId() {
		return chatId;
	}
	public String getLobbyId() {
		return getModuleName() + getChatId();
	}

	public TransmitTags getTags() {
		return tags;
	}

	public void setTags(TransmitTags tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}
}
