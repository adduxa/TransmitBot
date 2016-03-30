package ru.adduxa.Transmit.TelegramController.Activities.Basic.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Ping implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("pong", update.getMessage());
	}

	@Override
	public String getName() {
		return "/ping";
	}

	@Override
	public String getDescription() {
		return "Returns 'pong'";
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
