package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateSender;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

/**
 * Created by adduxa on 06.03.2016.
 */
public class CreateSender extends CommandActivity {
	private final TransmitTags transmitTags;
	private final TelegramModule module;
	private final Long chatId;


	public CreateSender(IUserActivity previousActivity, TelegramModule module, Long chatId, TransmitTags transmitTags) {
		super(previousActivity);
		this.module = module;
		this.chatId = chatId;
		this.transmitTags = transmitTags;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Enter name for sender:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		String senderName = update.getMessage().getText();
		if(!user.getSenders().containsKey(senderName)) {
			user.getSenders().put(senderName, module.newSender(senderName, chatId.toString(), transmitTags));
			controller.getTelegramModule().replyTo("Sender created", update.getMessage());
			user.popActivity(controller, update);
		} else {
			controller.getTelegramModule().replyTo("Sender exist", update.getMessage());
			replyKeyboard(controller, update);
		}
	}
}
