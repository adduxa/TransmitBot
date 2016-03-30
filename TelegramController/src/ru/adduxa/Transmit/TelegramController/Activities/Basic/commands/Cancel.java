package ru.adduxa.Transmit.TelegramController.Activities.Basic.commands;

import org.telegram.telegrambots.api.objects.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Cancel implements ICommand {
	private final String description;
	private final String exitMessageText;
	private final boolean hideKeyboard;

	public Cancel() {
		this("Operation canceled");
	}

	public Cancel(String exitMessageText) {
		this("Cancel the current operation", exitMessageText);
	}

	public Cancel(String description, String exitMessageText) {
		this(description, exitMessageText, true);
	}

	public Cancel(String description, String exitMessageText, boolean hideKeyboard) {
		this.description = description;
		this.exitMessageText = exitMessageText;
		this.hideKeyboard = hideKeyboard;
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		if(exitMessageText != null) {
			if(hideKeyboard) {
				ReplyKeyboardHide keyboardHide = new ReplyKeyboardHide();
				keyboardHide.setHideKeyboard(true);
				controller.getTelegramModule().replyTo(exitMessageText, update.getMessage(), keyboardHide);
			} else {
				controller.getTelegramModule().replyTo(exitMessageText, update.getMessage());
			}
		}
		user.popActivity(controller, update);
	}

	@Override
	public String getName() {
		return "/cancel";
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
