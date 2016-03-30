package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.commands.CreateSender;

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
public class CreateSenderSetTags extends CommandActivity {
	private final TelegramModule module;
	private final Long chatId;
	private enum inputState {ListeningTags, IgnoringTags}

	private inputState state = inputState.ListeningTags;

	private final ArrayList<String> listeningTags = new ArrayList<>();
	private final ArrayList<String> ignoringTags = new ArrayList<>();
	private boolean filtering;

	public CreateSenderSetTags(IUserActivity previousActivity, TelegramModule module, Long chatId) {
		super(previousActivity);
		this.module = module;
		this.chatId = chatId;
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Enter tags separated by commas that the sender will listen to, or '*' to send all messages\nIt can be any word contained in the target message. Usually hashtags\nFor ex. #togroup, #tochannel, #tobot", update.getMessage());
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
		switch(state) {
			case ListeningTags: {
				String[] tags = update.getMessage().getText().split(", ?", -1);
				listeningTags.addAll(Arrays.asList(tags));
				filtering = !listeningTags.contains("*");

				controller.getTelegramModule().replyTo("Enter tags separated by commas that the sender will IGNORE\nIt can be any word contained in the target message. Usually hashtags\nFor ex. #nogroup, #nochannel, #nobot", update.getMessage());
				state = inputState.IgnoringTags;
				break;
			}
			case IgnoringTags: {
				String[] tags = update.getMessage().getText().split(", ?", -1);
				ignoringTags.addAll(Arrays.asList(tags));

				TransmitTags transmitTags = new TransmitTags(filtering, listeningTags, ignoringTags);

				ControllingUser user = controller.getOrCreateUser(update.getMessage());
				user.pushActivity(new CreateSender(user.getActivity().getPreviousActivity(), module, chatId, transmitTags), controller, update);
				break;
			}
		}
	}
}
