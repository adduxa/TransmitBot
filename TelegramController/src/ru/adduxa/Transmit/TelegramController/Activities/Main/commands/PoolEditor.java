package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.PoolEditorActivity;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class PoolEditor implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		user.pushActivity(new PoolEditorActivity(user.getActivity()), controller, update);
	}

	@Override
	public String getName() {
		return "/poolEdit";
	}

	@Override
	public String getDescription() {
		return "Launch Pool Editor: create, remove and manage your pools";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
