package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.EditPoolActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Pool.TransmitPool;

/**
 * Created by adduxa on 22.02.2016.
 */
public class NewPoolActivity extends CommandActivity {
	public NewPoolActivity(IUserActivity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		getCommands().registerCommand(new Cancel("Cancel new pool creation", "New pool creation canceled"));

	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Enter new pool name:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		TransmitPool pool = user.newPool(update.getMessage().getText());
		if(pool == null) {
			controller.getTelegramModule().replyTo("Pool exist", update.getMessage());
			replyKeyboard(controller, update);
		} else {
			controller.getTelegramModule().replyTo("Pool created", update.getMessage());

			user.pushActivity(new EditPoolActivity(user.getActivity().getPreviousActivity()), controller, update);
		}
	}
}
