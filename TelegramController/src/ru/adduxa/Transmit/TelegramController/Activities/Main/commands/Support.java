package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 06.03.2016.
 */
public class Support implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Feel free to send bugs and feature requests to @adduxa", update.getMessage());
	}

	@Override
	public String getName() {
		return "/support";
	}

	@Override
	public String getDescription() {
		return "Help me!";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
