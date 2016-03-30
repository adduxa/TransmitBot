package ru.adduxa.Transmit.TransmitCore.Sender;

import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

/**
 * Created by adduxa on 15.02.2016.
 */
public interface ITransmitSender {
	String getModuleName();
	String getLobbyId();

	TransmitTags getTags();
	void setTags(TransmitTags tags);

	void sendMessage(TransmitMessage msg);
}
