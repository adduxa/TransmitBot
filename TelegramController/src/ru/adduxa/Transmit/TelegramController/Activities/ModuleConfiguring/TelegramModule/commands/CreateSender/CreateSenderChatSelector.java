package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateSender;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControlledChat;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 06.03.2016.
 */
public class CreateSenderChatSelector extends CommandActivity {
	private final TelegramModule module;
	public CreateSenderChatSelector(IUserActivity previousActivity, TelegramModule module) {
		super(previousActivity);
		this.module = module;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		for(ControlledChat chat : user.getChats().values()) {
			getActivityCommands().registerCommandActivity(chat.getTitle(), new CreateSenderSetTags(user.getActivity().getPreviousActivity(), module, chat.getId()));
		}
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Select chat:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {

	}
}
