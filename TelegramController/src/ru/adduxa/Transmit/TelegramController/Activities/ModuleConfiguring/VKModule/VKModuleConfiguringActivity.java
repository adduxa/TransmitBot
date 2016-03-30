package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.VKModule;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.ConfiguringActivity;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.VK.Module.VKModule;

/**
 * Created by adduxa on 22.02.2016.
 */
public class VKModuleConfiguringActivity extends ConfiguringActivity {

	private final VKModule module;

	public VKModuleConfiguringActivity(IUserActivity previousActivity, VKModule module) {
		super(previousActivity, module);
		this.module = module;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return null;
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {

	}
}
