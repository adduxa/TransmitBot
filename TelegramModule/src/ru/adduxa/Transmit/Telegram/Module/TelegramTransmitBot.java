package ru.adduxa.Transmit.Telegram.Module;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;

/**
 * Created by adduxa on 15.02.2016.
 */
public class TelegramTransmitBot extends TelegramLongPollingBot {
	private final ArrayList<ITelegramUpdateReceiver> updateReceivers = new ArrayList<>();

	@Override
	public void onUpdateReceived(Update update) {
		System.out.printf("IN: chatId: %d, from: %s, text: %s\n", update.getMessage().getChatId(), update.getMessage().getFrom().getUserName(), update.getMessage().getText());
		updateReceivers.forEach(r -> r.updateReceived(update));
	}

	@Override
	public String getBotUsername() {
		return "TransmittBot";
	}

	@Override
	public String getBotToken() {
		return TelegramBotToken.Token;
	}

	public void addUpdateReceiver(ITelegramUpdateReceiver receiver) {
		updateReceivers.add(receiver);
	}

	public void removeUpdateReceiver(ITelegramUpdateReceiver receiver) {
		updateReceivers.remove(receiver);
	}

	public void removeUpdateReceivers() {
		updateReceivers.clear();
	}
}
