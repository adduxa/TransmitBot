package ru.adduxa.Transmit.TelegramController.Activities.Basic.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Help implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		String helpMessage = "Available commands:";
		for(ICommand command :
				user.getActivity().getCommands().getCommandList().values()) {
			if(!command.isHidden()) {
				helpMessage += "\n" + command.getName() + " - " + command.getDescription();
			}
		}
		controller.getTelegramModule().replyTo(helpMessage, update.getMessage());
	}

	@Override
	public String getName() {
		return "/help";
	}

	@Override
	public String getDescription() {
		return "Print this help page";
	}

	@Override
	public boolean isHidden() {
		return true;
	}
}
