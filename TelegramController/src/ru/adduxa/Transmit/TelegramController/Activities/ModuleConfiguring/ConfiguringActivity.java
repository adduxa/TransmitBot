package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring;

import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TransmitCore.Module.TransmitModule;

/**
 * Created by adduxa on 23.02.2016.
 */
public abstract class ConfiguringActivity extends CommandActivity {

	protected ConfiguringActivity(IUserActivity previousActivity, TransmitModule module) {
		super(previousActivity);
	}
}
