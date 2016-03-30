package ru.adduxa.Transmit.TransmitCore.Receiver;

import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 * Created by adduxa on 15.02.2016.
 */
public abstract class TransmitReceiver extends Observable implements ITransmitReceiver {
	private final String moduleName;
	private final String chatId;
	private TransmitTags tags;
	private ArrayList<String> users = new ArrayList<>();
	private final String name;

	protected TransmitReceiver(String name, String moduleName, String chatId, TransmitTags tags, Collection<String> users) {
		this.name = name;
		this.moduleName = moduleName;
		this.chatId = chatId;
		setTags(tags);
		this.users.addAll(users);
	}

	public void messageReceived(TransmitMessage message) {
		if(message.getLobbyId().equals(getLobbyId())
				&& getTags().isMatching(message.getTags())
				&& getUsers().contains(message.getSender())
				) {
			setChanged();
			notifyObservers(message);
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

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}
}
