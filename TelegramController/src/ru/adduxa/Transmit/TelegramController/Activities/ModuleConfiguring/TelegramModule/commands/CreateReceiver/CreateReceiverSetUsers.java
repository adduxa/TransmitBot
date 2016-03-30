package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateReceiver;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Message.TransmitTags;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adduxa on 06.03.2016.
 */
public class CreateReceiverSetUsers extends CommandActivity {
	private final TelegramModule module;
	private final Long chatId;
	private final TransmitTags transmitTags;

	public CreateReceiverSetUsers(IUserActivity previousActivity, TelegramModule module, Long chatId, TransmitTags transmitTags) {
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
		return "Enter users those can trigger receiver, separates by commas. Or just dot to add no one (you will be added by default)";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		String[] usersArray = update.getMessage().getText().split(", ?", -1);
		ArrayList<String> users = new ArrayList<>(Arrays.asList(usersArray));
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		users.add(update.getMessage().getFrom().getUserName());
		users.remove(".");
		user.pushActivity(new CreateReceiver(user.getActivity().getPreviousActivity(), module, chatId, transmitTags, users), controller, update);
	}
}
