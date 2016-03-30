package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by adduxa on 22.02.2016.
 */
public class ActivityCommands {
	private final LinkedHashMap<String, IUserActivity> commands = new LinkedHashMap<>();

	public void registerCommandActivity(Collection<String> summonLines, CommandActivity commandActivity) {
		for(String summonLine : summonLines) {
			registerCommandActivity(summonLine, commandActivity);
		}
	}

	public void registerCommandActivity(String summonLine, IUserActivity commandActivity) {
		commands.put(summonLine, commandActivity);
	}

	public boolean summonActivity(String summonLine, TelegramTransmitController controller, Update update) {
		IUserActivity activity = commands.get(summonLine);
		if(activity == null) {
			activity = commands.get("*");
		}
		if(activity != null) {
			ControllingUser user = controller.getOrCreateUser(update.getMessage());
			user.pushActivity(activity, controller, update);
			return true;
		} else {
			return false;
		}
	}

	public LinkedHashMap<String, IUserActivity> getCommandList() {
		return commands;
	}
	public ArrayList<String> getCommandSummonLines() {
		ArrayList<String> qw = new ArrayList<>(commands.keySet());
		qw.remove("*");
		return qw;
	}
}