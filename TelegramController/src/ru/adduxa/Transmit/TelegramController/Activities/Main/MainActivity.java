package ru.adduxa.Transmit.TelegramController.Activities.Main;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.BasicActivity;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Help;
import ru.adduxa.Transmit.TelegramController.Activities.Main.commands.*;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class MainActivity extends BasicActivity {
	public MainActivity() {
		super(null);
		getCommands().registerCommand(new ModuleConfigurer());
		getCommands().registerCommand(new PoolEditor());
		getCommands().registerCommand(new Modules());
		getCommands().registerCommand(new Pools());
		getCommands().registerCommand(new Start());
		getCommands().registerCommand(new Help());
		getCommands().registerCommand(new Support());
	}

	@Override
	public void onTextUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public void pushUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public void popUpdate(TelegramTransmitController controller, Update update) {

	}
}
