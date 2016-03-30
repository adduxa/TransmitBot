package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.RemoveSender;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public class RemoveSenderSelector extends CommandActivity {
	private final String poolName;
	public RemoveSenderSelector(IUserActivity previousActivity, String poolName) {
		super(previousActivity);
		this.poolName = poolName;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		getActivityCommands().registerCommandActivity(user.getPools().get(poolName).getSenders().keySet(), new RemoveSender(user.getActivity().getPreviousActivity(), poolName));
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Select sender:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Sender not found", update.getMessage());
	}
}
