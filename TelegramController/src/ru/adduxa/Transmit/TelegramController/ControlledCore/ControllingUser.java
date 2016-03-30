package ru.adduxa.Transmit.TelegramController.ControlledCore;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Pool.TransmitPool;
import ru.adduxa.Transmit.TransmitCore.Receiver.TransmitReceiver;
import ru.adduxa.Transmit.TransmitCore.Sender.TransmitSender;

import java.util.HashMap;

/**
 * Created by adduxa on 19.02.2016.
 */
public class ControllingUser {
	private final Integer id;
	private Long chatId;
	private final HashMap<String, TransmitPool> pools = new HashMap<>();
	private final HashMap<Long, ControlledChat> chats = new HashMap<>();
	private final HashMap<String, TransmitReceiver> receivers = new HashMap<>();
	private final HashMap<String, TransmitSender> senders = new HashMap<>();
	private IUserActivity activity;

	public ControllingUser(Integer id, Long chatId, IUserActivity activity) {
		this.id = id;
		this.chatId = chatId;
		this.activity = activity;
	}

	public Integer getId() {
		return id;
	}

	public HashMap<String, TransmitPool> getPools() {
		return pools;
	}

	public TransmitPool addPool(String poolName, TransmitPool pool) {
		if(pools.containsKey(poolName)) {
			return null;
		} else {
			pools.put(poolName, pool);
			return pool;
		}
	}

	public TransmitPool newPool(String poolName) {
		TransmitPool pool = new TransmitPool();
		return addPool(poolName, pool);
	}

	public void removePool(String poolName) {
		TransmitPool pool = pools.get(poolName);
		if(pool != null) {
			pool.removeReceivers();
			pool.removeSenders();
			//TODO: needed??
			pools.remove(poolName);
		}
	}
	public void addChat(ControlledChat chat) {
		chats.put(chat.getId(), chat);
	}

	public void removeChat(ControlledChat chat) {
		chats.remove(chat.getId());
		for(TransmitPool pool :
				getPools().values()) {
			for(TransmitReceiver receiver :
					pool.getReceivers().values()) {
				if(receiver.getModuleName().equals(chat.getModuleName()) && receiver.getChatId().equals(chat.getId().toString())) {
					pool.removeReceiver(receiver.getName());
					receivers.remove(receiver.getName());
				}
			}
			for(TransmitSender sender :
					pool.getSenders().values()) {
				if(sender.getModuleName().equals(chat.getModuleName()) && sender.getChatId().equals(chat.getId().toString())) {
					pool.removeSender(sender.getName());
					senders.remove(sender.getName());
				}
			}
		}
	}

	public HashMap<Long, ControlledChat> getChats() {
		return chats;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public IUserActivity getActivity() {
		return activity;
	}

	public void pushActivity(IUserActivity activity, TelegramTransmitController controller, Update update) {
		setActivity(activity);
		activity.pushUpdate(controller, update);
	}
	public void setActivity(IUserActivity activity) {
		this.activity = activity;
	}

	public void popActivity(TelegramTransmitController controller, Update update) {
		if(getActivity().getPreviousActivity() != null) {
			setActivity(getActivity().getPreviousActivity());
			getActivity().popUpdate(controller, update);
		}
	}

	public void removeReceiver(String receiverName) {
		for(TransmitPool pool :
				getPools().values()) {
			for(TransmitReceiver receiver :
					pool.getReceivers().values()) {
				if(receiver.getName().equals(receiverName)) {
					pool.removeReceiver(receiverName);
					receivers.remove(receiverName);
				}
			}
		}
	}

	public void removeSender(String senderName) {
		for(TransmitPool pool :
				getPools().values()) {
			for(TransmitSender sender :
					pool.getSenders().values()) {
				if(sender.getName().equals(senderName)) {
					pool.removeSender(senderName);
					senders.remove(senderName);
				}
			}
		}
	}

	public void connectReceiver(String poolName, String receiverName) {
		TransmitReceiver receiver = getReceivers().get(receiverName);
		TransmitPool pool = getPools().get(poolName);
		connectReceiver(pool, receiver);
	}

	public void connectSender(String poolName, String senderName) {
		TransmitSender sender = getSenders().get(senderName);
		TransmitPool pool = getPools().get(poolName);
		connectSender(pool, sender);
	}

	public void connectReceiver(TransmitPool pool, TransmitReceiver receiver) {
		pool.addReceiver(receiver);
	}
	public void connectSender(TransmitPool pool, TransmitSender sender) {
		pool.addSender(sender);
	}

	public HashMap<String, TransmitReceiver> getReceivers() {
		return receivers;
	}

	public HashMap<String, TransmitSender> getSenders() {
		return senders;
	}
}
