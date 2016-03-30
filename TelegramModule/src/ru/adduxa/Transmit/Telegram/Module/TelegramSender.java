package ru.adduxa.Transmit.Telegram.Module;

import ru.adduxa.Transmit.TransmitCore.Message.TransmitMessage;
import ru.adduxa.Transmit.TransmitCore.Sender.TransmitSender;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

/**
 * Created by adduxa on 15.02.2016.
 */
class TelegramSender extends TransmitSender {
	private final TelegramModule telegramModule;

	public TelegramSender(String name, TelegramModule module, String chatId, TransmitTags tags) {
		super(name, module.getName(), chatId, tags);
		this.telegramModule = module;
	}

	@Override
	public void sendMessage(TransmitMessage msg) {
		telegramModule.sendTo(msg.getText(), getChatId());
	}
}
