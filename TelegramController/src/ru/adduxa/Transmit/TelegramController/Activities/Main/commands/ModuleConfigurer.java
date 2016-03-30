package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.ModuleConfiguringSelector;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class ModuleConfigurer implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		user.pushActivity(new ModuleConfiguringSelector(user.getActivity()), controller, update);
	}

	@Override
	public String getName() {
		return "/moduleConfigurer";
	}

	@Override
	public String getDescription() {
		return "Launch Module Configurer: create, remove and manage your receivers and senders";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
