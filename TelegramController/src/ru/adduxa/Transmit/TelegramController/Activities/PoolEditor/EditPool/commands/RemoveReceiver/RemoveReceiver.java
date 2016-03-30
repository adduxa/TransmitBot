package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.RemoveReceiver;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 23.02.2016.
 */
public class RemoveReceiver extends CommandActivity {
	private final String poolName;
	public RemoveReceiver(IUserActivity previousActivity, String poolName) {
		super(previousActivity);
		this.poolName = poolName;
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
		String receiverName = update.getMessage().getText();
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		if(user.getPools().get(poolName).removeReceiver(receiverName)) {
			controller.getTelegramModule().replyTo("Receiver removed", update.getMessage());
		} else {
			controller.getTelegramModule().replyTo("Receiver does not exist", update.getMessage());
		}
		user.popActivity(controller, update);
	}
}
