package ru.adduxa.Transmit.TelegramController;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControlledChat;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.Main.MainActivity;
import ru.adduxa.Transmit.TransmitCore.Module.*;
import ru.adduxa.Transmit.Telegram.Module.ITelegramUpdateReceiver;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by adduxa on 19.02.2016.
 */
public class TelegramTransmitController implements ITelegramUpdateReceiver {
	private final TelegramModule telegramModule;
	private final ConcurrentHashMap<Integer, ControllingUser> controllingUsers = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, TransmitModule> transmitModules = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Long, ControlledChat> controlledChats = new ConcurrentHashMap<>();
//TODO: check .put() for null checks !!
	public TelegramTransmitController(TelegramModule telegram) {
		telegramModule = telegram;
		telegramModule.getBot().addUpdateReceiver(this);
	}

	public void addModule(TransmitModule module) {
		transmitModules.put(module.getName(), module);
	}

	public void removeModule(String moduleName) {
		transmitModules.remove(moduleName);
	}

	public void addChat(ControlledChat chat) {
		addChat(chat, true);
	}

	public void addChat(ControlledChat chat, boolean sendMessage) {
		System.out.printf("addChat: chatId: %d, Title: %s, Owner: %s\n", chat.getId(), chat.getTitle(), chat.getOwner().getId());
		if(!controlledChats.containsKey(chat.getId())) {
			chat.getOwner().addChat(chat);
			controlledChats.put(chat.getId(), chat);
			if(sendMessage) {
				telegramModule.sendTo(String.format("Chat added: '%s'", chat.getTitle()), chat.getOwner().getChatId().toString());
			}
		} else {
			if(sendMessage) {
				telegramModule.sendTo(String.format("Error: This chat has already been added '%s'", chat.getTitle()), chat.getOwner().getChatId().toString());
			}
		}
	}

	public void removeChat(ControlledChat chat) {
		System.out.printf("remove: chatId: %d, Title: %s, Owner: %s\n", chat.getId(), chat.getTitle(), chat.getOwner().getId());
		ControllingUser controllingUser = chat.getOwner();
		controllingUser.removeChat(chat);
		controlledChats.remove(chat.getId());
		
		telegramModule.sendTo(String.format("Chat removed: '%s'", chat.getTitle()), chat.getOwner().getChatId().toString());
	}

	public ControllingUser getUser(Message message) {
		return getUser(message.getFrom().getId());
	}

	public ControllingUser getUser(Integer id) {
		//System.out.printf("getUser: id: %d\n", id);
		return controllingUsers.get(id);
	}

	public ControllingUser getOrCreateUser(Message message) {
		return getOrCreateUser(message.getFrom().getId(), message.getChatId());
	}
	public ControllingUser getOrCreateUser(Integer id, Long chatId) {
		System.out.printf("getOrCreateUser: id: %d, chatId: %d", id, chatId);
		ControllingUser user = getUser(id);
		if(user == null) {
			System.out.printf(", doesNotExist ");
			user = new ControllingUser(id, chatId, new MainActivity());
			controllingUsers.put(id, user);
			if(user.getChatId() != null) {
				addChat(new ControlledChat(getTelegramModule().getName(), chatId, "Bot's chat", false, user), false);
			}
		}
		if(user.getChatId() == null && chatId != null) {
			System.out.printf(", newChatId: %d ", chatId);
			user.setChatId(chatId);
			addChat(new ControlledChat(getTelegramModule().getName(), chatId, "Bot's chat", false, user), false);
		}
		System.out.println();
		return user;
	}

	@Override
	public void updateReceived(Update update) {
		if(update.hasMessage()) {
			Message message = update.getMessage();
			if(message.getChat().isUserChat() && message.hasText()) {
				ControllingUser user = getOrCreateUser(message);
				user.getActivity().onUpdate(this, update);
			} else {
				if(message.getNewChatParticipant() != null) {
					User newUser = message.getNewChatParticipant();
					if(newUser.getUserName().equals(telegramModule.getBot().getBotUsername())) {
						User fromUser = message.getFrom();
						ControllingUser controllingUser = getOrCreateUser(fromUser.getId(), null);
						addChat(new ControlledChat(telegramModule.getName(), message.getChatId(), message.getChat().getTitle(), message.isChannelMessage(), controllingUser));
						System.out.printf("Added bot to chat: chatId: %d, chatTitle: '%s' by: %s\n", message.getChatId(), message.getChat().getTitle(), message.getFrom().getUserName());
					}
				} else if(message.getLeftChatParticipant() != null) {
					User leftUser = message.getLeftChatParticipant();
					if(leftUser.getUserName().equals(telegramModule.getBot().getBotUsername())) {
						ControlledChat chat = controlledChats.get(message.getChatId());
						if(chat != null) {
							removeChat(chat);
						} else {
							System.out.printf("removeChat chatId: %d, title: '%s'", message.getChatId(), message.getChat().getTitle());
						}
					}
				}
			}
		}
	}

	public TelegramModule getTelegramModule() {
		return telegramModule;
	}

	public ConcurrentHashMap<Integer, ControllingUser> getControllingUsers() {
		return controllingUsers;
	}

	public ConcurrentHashMap<String, TransmitModule> getTransmitModules() {
		return transmitModules;
	}

	public ConcurrentHashMap<Long, ControlledChat> getControlledChats() {
		return controlledChats;
	}
}
