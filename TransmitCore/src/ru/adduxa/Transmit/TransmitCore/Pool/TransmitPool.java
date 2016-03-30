package ru.adduxa.Transmit.TransmitCore.Pool;

import ru.adduxa.Transmit.TransmitCore.Receiver.TransmitReceiver;
import ru.adduxa.Transmit.TransmitCore.Sender.TransmitSender;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by adduxa on 15.02.2016.
 */

public class TransmitPool extends Observable implements Observer {
	private final HashMap<String, TransmitReceiver> receivers = new HashMap<>();
	private final HashMap<String, TransmitSender> senders = new HashMap<>();

	public boolean addReceiver(TransmitReceiver receiver) {
		if(!receivers.containsKey(receiver.getName())) {
			receivers.put(receiver.getName(), receiver);
			receiver.addObserver(this);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeReceiver(String receiverName) {
		if(receivers.containsKey(receiverName)) {
			TransmitReceiver receiver = receivers.get(receiverName);
			receiver.deleteObserver(this);
			receivers.remove(receiverName);
			return true;
		} else {
			return false;
		}
	}

	public void removeReceivers() {
		receivers.forEach((s, receiver) -> removeReceiver(s));
	}

	public boolean addSender(TransmitSender sender) {
		if(!senders.containsKey(sender.getName())) {
			senders.put(sender.getName(), sender);
			addObserver(sender);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeSender(String senderName) {
		if(senders.containsKey(senderName)) {
			TransmitSender sender = senders.get(senderName);
			deleteObserver(sender);
			senders.remove(senderName);
			return true;
		} else {
			return false;
		}
	}

	public void removeSenders() {
		senders.forEach((s, transmitSender) -> removeReceiver(s));
		deleteObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	public HashMap<String, TransmitReceiver> getReceivers() {
		return receivers;
	}

	public HashMap<String, TransmitSender> getSenders() {
		return senders;
	}
}
