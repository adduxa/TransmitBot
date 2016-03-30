package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.EditPoolActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class PoolEditorActivity extends CommandActivity {
	public PoolEditorActivity(IUserActivity previousActivity) {
		super(previousActivity);
	}

	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());

		getCommands().registerCommand(new Cancel("Exit Pool Editor", "Pool Editor exited", true));
		getActivityCommands().registerCommandActivity("Create new pool", new NewPoolActivity(user.getActivity()));
		getActivityCommands().registerCommandActivity(user.getPools().keySet(), new EditPoolActivity(user.getActivity()));
	}

	public String getInitMessage() {
		return "Welcome to Pool Editor!\nSelect pool:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Pool not found", update.getMessage());
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());

		getActivityCommands().getCommandList().clear();
		getActivityCommands().registerCommandActivity("Create new pool", new NewPoolActivity(user.getActivity()));
		getActivityCommands().registerCommandActivity(user.getPools().keySet(), new EditPoolActivity(user.getActivity()));
	}
}
