package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
abstract public class UserActivity implements IUserActivity {
	//TODO: make activities and commands 'static synchronized'
	private final IUserActivity previousActivity;
	private final Commands commands = new Commands();

	public UserActivity(IUserActivity previousActivity) {
		this.previousActivity = previousActivity;
	}

	public Commands getCommands() {
		return commands;
	}

	@Override
	public void onUpdate(TelegramTransmitController controller, Update update) {
		String messageText = update.getMessage().getText();
		if(messageText.startsWith("/")) {
			String command = messageText;
			int qw = messageText.indexOf(" ");
			if(qw > 1) {
				command = command.substring(0, qw);
			}
			commands.summonCommand(command, controller, update);
		} else {
			onTextUpdate(controller, update);
		}
	}

	public abstract void onTextUpdate(TelegramTransmitController controller, Update update);

	@Override
	public IUserActivity getPreviousActivity() {
		return previousActivity;
	}
}
