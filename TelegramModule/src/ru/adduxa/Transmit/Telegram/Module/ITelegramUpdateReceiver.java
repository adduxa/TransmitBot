package ru.adduxa.Transmit.Telegram.Module;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by adduxa on 19.02.2016.
 */
public interface ITelegramUpdateReceiver {
	void updateReceived(Update update);
}
