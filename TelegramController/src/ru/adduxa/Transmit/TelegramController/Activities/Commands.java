package ru.adduxa.Transmit.TelegramController.Activities;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;

import java.util.LinkedHashMap;

/**
 * Created by adduxa on 22.02.2016.
 */
public class Commands {
	private final LinkedHashMap<String, ICommand> commands = new LinkedHashMap<>();

	public void registerCommand(ICommand command) {
		commands.put(command.getName().toLowerCase(), command);
	}

	public void registerAlias(String commandName, String aliasName) {
		ICommand command = commands.get(commandName.toLowerCase());
		if(command != null) {
			registerAlias(command, aliasName);
		}
	}

	public void registerAlias(ICommand command, String aliasName) {
		ICommand aliasCommand = new ICommand() {
			@Override
			public void summon(TelegramTransmitController controller, Update update) {
				command.summon(controller, update);
			}

			@Override
			public String getName() {
				return aliasName;
			}

			@Override
			public String getDescription() {
				return command.getDescription();
			}

			@Override
			public boolean isHidden() {
				return true;
			}
		};
		registerCommand(aliasCommand);
	}

	public void summonCommand(String name, TelegramTransmitController controller, Update update) {
		ICommand command = commands.get(name.toLowerCase());
		if(command != null) {
			command.summon(controller, update);
		} else {
			controller.getTelegramModule().replyTo("Unknown command. Try /help", update.getMessage());
		}
	}

	public LinkedHashMap<String, ICommand> getCommandList() {
		return commands;
	}
}