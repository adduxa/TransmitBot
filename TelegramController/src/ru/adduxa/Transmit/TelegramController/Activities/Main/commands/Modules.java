package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Modules implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		String temp = "Available modules:";
		for(String module :
				controller.getTransmitModules().keySet()) {
			temp += "\n" + module;
		}
		controller.getTelegramModule().replyTo(temp, update.getMessage());
	}

	@Override
	public String getName() {
		return "/modules";
	}

	@Override
	public String getDescription() {
		return "Get available modules";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
