package ru.adduxa.Transmit.Telegram.Module;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Receiver.TransmitReceiver;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.Collection;

/**
 * Created by adduxa on 15.02.2016.
 */
class TelegramReceiver extends TransmitReceiver implements ITelegramUpdateReceiver {
	private final TelegramTransmitBot bot;

	public TelegramReceiver(String name, TelegramModule module, Long chatId, TransmitTags tags, Collection<String> users) {
		super(name, module.getName(), chatId.toString(), tags, users);
		this.bot = module.getBot();

		this.bot.addUpdateReceiver(this);
	}

	public void updateReceived(Update update) {
		if(update.hasMessage()) {
			Message message = update.getMessage();
			if(message.getChat().getId().toString().equals(getChatId())) {
				TransmitMessage transmitMessage = new TransmitMessage(getLobbyId(), message.getFrom().getUserName(), message.getText());
				messageReceived(transmitMessage);
			}
		}
	}
}
