package ru.adduxa.Transmit.TelegramController.Activities.Basic;

import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Help;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Ping;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.UserActivity;

/**
 * Created by adduxa on 22.02.2016.
 */
abstract public class BasicActivity extends UserActivity {
	protected BasicActivity(IUserActivity previousActivity) {
		super(previousActivity);
		getCommands().registerCommand(new Help());
		getCommands().registerCommand(new Ping());
		getCommands().registerCommand(new Cancel());
	}
}
