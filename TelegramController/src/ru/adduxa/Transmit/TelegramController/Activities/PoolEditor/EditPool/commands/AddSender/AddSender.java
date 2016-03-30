package ru.adduxa.Transmit.TelegramController.Activities.PoolEditor.EditPool.commands.AddSender;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Sender.TransmitSender;

/**
 * Created by adduxa on 22.02.2016.
 */
public class AddSender extends CommandActivity {
	private final String poolName;
	public AddSender(IUserActivity previousActivity, String poolName) {
		super(previousActivity);
		this.poolName = poolName;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		String senderName = update.getMessage().getText();
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		TransmitSender sender = user.getSenders().get(senderName);
		if(user.getPools().get(poolName).addSender(sender)) {
			controller.getTelegramModule().replyTo("Sender added", update.getMessage());
		} else {
			controller.getTelegramModule().replyTo("Sender already added", update.getMessage());
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
