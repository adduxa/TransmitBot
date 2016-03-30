package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControlledChat;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.Activities.ICommand;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;

/**
 * Created by adduxa on 22.02.2016.
 */
public class AddChannel implements ICommand {
	private final TelegramModule module;

	public AddChannel(TelegramModule module) {
		this.module = module;
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		Message message = update.getMessage();
		String[] splittedMessage = message.getText().split(" ");
		ControllingUser user = controller.getOrCreateUser(message);

		if(splittedMessage.length > 1 && !splittedMessage[1].isEmpty()) {
			try {
				Message sentMessage = controller.getTelegramModule().sendToUnsafe("Transmit Bot test message", splittedMessage[1]);
				controller.addChat(new ControlledChat(module.getName(), sentMessage.getChatId(), sentMessage.getChat().getTitle(), true, user));
			} catch(TelegramApiException e) {
				controller.getTelegramModule().replyTo(String.format("Error while sending test message to channel: '%s'", e.getApiResponse()), message);
			}
		} else {
			controller.getTelegramModule().replyTo("Usage: /addChannel @PublicChannelName\nAttention: bot will send test message to channel!\nTo add bot to private channel make the channel public, add bot to the channel and then make the channel private back", update.getMessage());
		}
	}

	@Override
	public String getName() {
		return "/addChannel";
	}

	@Override
	public String getDescription() {
		return "Add bot to channel";
	}

	@Override
	public boolean isHidden() {
		return false;
	}
}
