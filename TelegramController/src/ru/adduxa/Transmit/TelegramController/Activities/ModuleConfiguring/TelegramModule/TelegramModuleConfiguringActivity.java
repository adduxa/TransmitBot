package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.ConfiguringActivity;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.*;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateReceiver.CreateReceiverChatSelector;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateSender.CreateSenderChatSelector;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;

/**
 * Created by adduxa on 22.02.2016.
 */
public class TelegramModuleConfiguringActivity extends ConfiguringActivity {
	private final TelegramModule module;
	public TelegramModuleConfiguringActivity(IUserActivity previousActivity, TelegramModule module) {
		super(previousActivity, module);
		this.module = module;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		getCommands().registerCommand(new Chats());
		getCommands().registerCommand(new AddChannel(module));
		getCommands().registerCommand(new Cancel("Exit Telegram module configurer", "Telegram module configurer exited"));
		ControllingUser user = controller.getOrCreateUser(update.getMessage());
		getActivityCommands().registerCommandActivity("Create receiver", new CreateReceiverChatSelector(user.getActivity(), module));
		getActivityCommands().registerCommandActivity("Create sender", new CreateSenderChatSelector(user.getActivity(), module));
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Try /help";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {

	}
}
