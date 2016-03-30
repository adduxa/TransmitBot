package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateReceiver;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.Collection;

/**
 * Created by adduxa on 06.03.2016.
 */
public class CreateReceiver extends CommandActivity {
	private final TelegramModule module;
	private final Long chatId;
	private final TransmitTags transmitTags;
	private final Collection<String> users;


	public CreateReceiver(IUserActivity previousActivity, TelegramModule module, Long chatId, TransmitTags transmitTags, Collection<String> users) {
		super(previousActivity);
		this.module = module;
		this.chatId = chatId;
		this.transmitTags = transmitTags;
		this.users = users;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Enter name for receiver:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		String receiverName = update.getMessage().getText();
		if(!user.getReceivers().containsKey(receiverName)) {
			user.getReceivers().put(receiverName, module.newReceiver(receiverName, chatId, transmitTags, users));
			controller.getTelegramModule().replyTo("Receiver created", update.getMessage());
			user.popActivity(controller, update);
		} else {
			controller.getTelegramModule().replyTo("Receiver exist", update.getMessage());
		}
	}
}
