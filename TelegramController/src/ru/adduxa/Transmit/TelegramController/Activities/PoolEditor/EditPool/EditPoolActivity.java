package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.AddReceiver.AddReceiverSelector;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.AddSender.AddSenderSelector;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.DeletePool.DeletePool;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.RemoveReceiver.RemoveReceiverSelector;
import ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.RemoveSender.RemoveSenderSelector;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class EditPoolActivity extends CommandActivity {
	public EditPoolActivity(IUserActivity previousActivity) {
		super(previousActivity);
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		String poolName = update.getMessage().getText();
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		getActivityCommands().registerCommandActivity("Add receiver", new AddReceiverSelector(user.getActivity(), poolName));
		getActivityCommands().registerCommandActivity("Add sender", new AddSenderSelector(user.getActivity(), poolName));
		getActivityCommands().registerCommandActivity("Remove receiver", new RemoveReceiverSelector(user.getActivity(), poolName));
		getActivityCommands().registerCommandActivity("Remove sender", new RemoveSenderSelector(user.getActivity(), poolName));
		getActivityCommands().registerCommandActivity("Delete this pool", new DeletePool(user.getActivity(), poolName));

		getCommands().registerCommand(new Cancel("Exit Pool Editor", "Pool Editor exited"));
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Available operations:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Operation not found", update.getMessage());
	}
}
