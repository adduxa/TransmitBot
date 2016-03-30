package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.BasicActivity;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

/**
 * Created by adduxa on 22.02.2016.
 */
public abstract class CommandActivity extends BasicActivity {
	private final ActivityCommands activityCommands = new ActivityCommands();

	protected CommandActivity(IUserActivity previousActivity) {
		super(previousActivity);
	}

	protected ActivityCommands getActivityCommands() {
		return activityCommands;
	}

	@Override
	public void onTextUpdate(TelegramTransmitController controller, Update update) {
		String messageText = update.getMessage().getText();
		if(!activityCommands.summonActivity(messageText, controller, update)) {
			summon(controller, update);
		}
	}

	@Override
	public void pushUpdate(TelegramTransmitController controller, Update update) {
		pushCommandUpdate(controller, update);
		replyKeyboard(controller, update);
	}

	protected void replyKeyboard(TelegramTransmitController controller, Update update) {
		if(getInitMessage() != null) {
			ReplyKeyboard keyboardMarkup = controller.getTelegramModule().makeSelectingKeyboard(getActivityCommands().getCommandSummonLines());
			if(keyboardMarkup == null) {
				ReplyKeyboardHide keyboardHide = new ReplyKeyboardHide();
				keyboardHide.setHideKeyboard(true);
				keyboardMarkup = keyboardHide;
			}
			controller.getTelegramModule().replyTo(getInitMessage(), update.getMessage(), keyboardMarkup);
		}
	}

	@Override
	public void popUpdate(TelegramTransmitController controller, Update update) {
		popCommandUpdate(controller, update);
		replyKeyboard(controller, update);
	}

	protected abstract void pushCommandUpdate(TelegramTransmitController controller, Update update);
	protected abstract void popCommandUpdate(TelegramTransmitController controller, Update update);
	protected abstract String getInitMessage();
	protected abstract void summon(TelegramTransmitController controller, Update update);
}
