package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.AddReceiver;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Receiver.TransmitReceiver;

/**
 * Created by adduxa on 23.02.2016.
 */
public class AddReceiver extends CommandActivity {
	private final String poolName;
	public AddReceiver(IUserActivity previousActivity, String poolName) {
		super(previousActivity);
		this.poolName = poolName;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		String receiverName = update.getMessage().getText();
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		TransmitReceiver receiver = user.getReceivers().get(receiverName);
		if(user.getPools().get(poolName).addReceiver(receiver)) {
			controller.getTelegramModule().replyTo("Receiver added", update.getMessage());
		} else {
			controller.getTelegramModule().replyTo("Receiver already added", update.getMessage());
		}
		user.popActivity(controller, update);
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
