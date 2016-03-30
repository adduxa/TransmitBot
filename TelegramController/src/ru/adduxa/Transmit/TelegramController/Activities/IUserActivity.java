package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public interface IUserActivity {
	void onUpdate(TelegramTransmitController controller, Update update);
	void pushUpdate(TelegramTransmitController controller, Update update);
	void popUpdate(TelegramTransmitController controller, Update update);

	IUserActivity getPreviousActivity();

	Commands getCommands();
}
