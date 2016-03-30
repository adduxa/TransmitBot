package ru.adduxa.Transmit.TelegramController.Activities.Main.commands;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Pool.TransmitPool;

import java.util.Map;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Pools implements ICommand {
	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());

		String temp = "Added pools:";
		for(Map.Entry<String, TransmitPool> pool :
				user.getPools().entrySet()) {
			temp += "\n*" + pool.getKey();
			temp += "\n Receivers:";
			for(String receiver :
					pool.getValue().getReceivers().keySet()) {
				temp += "\n +" + receiver;
			}
			temp += "\n Senders:";
			for(String sender :
					pool.getValue().getSenders().keySet()) {
				temp += "\n -" + sender;
			}
		}
		controller.getTelegramModule().replyTo(temp, update.getMessage());
	}

	@Override
	public String getName() {
		return "/pools";
	}

	@Override
	public String getDescription() {
		return "Get added pools";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
