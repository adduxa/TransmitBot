package ru.adduxa.Transmit.TransmitCore.Receiver;

import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.ArrayList;

/**
 * Created by adduxa on 15.02.2016.
 */
public interface ITransmitReceiver {
	String getModuleName();
	String getChatId();
	String getLobbyId();

	TransmitTags getTags();
	void setTags(TransmitTags tags);

	ArrayList<String> getUsers();
	void setUsers(ArrayList<String> users);

	void messageReceived(TransmitMessage message);
}
