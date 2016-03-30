package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Start implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Hi there! Try /help", update.getMessage());
	}

	@Override
	public String getName() {
		return "/start";
	}

	@Override
	public String getDescription() {
		return "Initial command";
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
