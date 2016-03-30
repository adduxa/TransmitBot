package ru.adduxa.Transmit.Telegram.Module;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;
import ru.adduxa.Transmit.TransmitCore.Module.TransmitModule;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by adduxa on 15.02.2016.
 */
public class TelegramModule extends TransmitModule {
	private TelegramTransmitBot transmitBot;

	public TelegramModule() {
		super("Telegram");
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			transmitBot = new TelegramTransmitBot();
			telegramBotsApi.registerBot(transmitBot);
		} catch(TelegramApiException e) {
			System.out.println(e.getMessage());
		}
	}

	public TelegramTransmitBot getBot() {
		return transmitBot;
	}

	public TelegramReceiver newReceiver(String name, Long chatId, TransmitTags tags, Collection<String> users) {
		return new TelegramReceiver(name, this, chatId, tags, users);
	}

	public TelegramSender newSender(String name, String chatId, TransmitTags tags) {
		return new TelegramSender(name, this, chatId, tags);
	}

	@Override
	public String getName() {
		return "Telegram";
	}

	public Message sendToUnsafe(String messageText, String chatId, ReplyKeyboard replayMarkup) throws TelegramApiException {
		if(chatId != null) {
			SendMessage sendMessageRequest = new SendMessage();
			sendMessageRequest.setChatId(chatId);
			sendMessageRequest.setText(messageText);
			if(replayMarkup != null) {
				sendMessageRequest.setReplayMarkup(replayMarkup);
			}

			Message sendMessageResponse = getBot().sendMessage(sendMessageRequest);
			System.out.printf("OUT: chatId: %d, to: %s, text: '%s'\n", sendMessageResponse.getChatId(), sendMessageResponse.getChat().getUserName(), sendMessageResponse.getText());
			return sendMessageResponse;
		}
		return null;
	}

	public Message sendToUnsafe(String messageText, String chatId) throws TelegramApiException {
		return sendToUnsafe(messageText, chatId, null);
	}

	public Message sendTo(String messageText, String chatId, ReplyKeyboard replayMarkup) {
		try {
			return sendToUnsafe(messageText, chatId, replayMarkup);
		} catch(TelegramApiException e) {
			System.out.println(e.getApiResponse());
		}
		return null;
	}

	public Message sendTo(String messageText, String chatId) {
		return sendTo(messageText, chatId, null);
	}

	public Message replyTo(String messageText, Message incomingMessage) {
		return replyTo(messageText, incomingMessage, null);
	}

	public Message replyTo(String messageText, Message incomingMessage, ReplyKeyboard replayMarkup) {
		return sendTo(messageText, incomingMessage.getChatId().toString(), replayMarkup);
	}

	public ReplyKeyboardMarkup makeSelectingKeyboard(Collection<String> selections) {
		return makeSelectingKeyboard(selections, true);
	}

	public ReplyKeyboardMarkup makeSelectingKeyboard(Collection<String> selections, boolean oneTime) {
		if(selections.isEmpty()) {
			return null;
		}
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<List<String>> pools = new ArrayList<>();
		for(String selection :
				selections) {
			List<String> row = new ArrayList<>();
			row.add(selection);
			pools.add(row);
		}

		keyboardMarkup.setKeyboard(pools);
		keyboardMarkup.setResizeKeyboard(true);
		keyboardMarkup.setOneTimeKeyboad(oneTime);
		return keyboardMarkup;
	}
}
