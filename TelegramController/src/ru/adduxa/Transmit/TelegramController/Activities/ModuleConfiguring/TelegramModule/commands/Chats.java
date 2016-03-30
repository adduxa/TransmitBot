package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControlledChat;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Chats implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {

		ControllingUser user = controller.getOrCreateUser(update.getMessage());

		String temp = "Added chats:";
		for(ControlledChat chat :
				user.getChats().values()) {
			temp += "\n\"" + chat.getTitle() + "\"" + (chat.isChannel() ? " - channel" : "");
		}
		controller.getTelegramModule().replyTo(temp, update.getMessage());
	}

	@Override
	public String getName() {
		return "/chats";
	}

	@Override
	public String getDescription() {
		return "Get added chats";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
