package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public interface ICommand {
	void summon(TelegramTransmitController controller, Update update);
	
	String getName();
	String getDescription();
	boolean isHidden();
}
