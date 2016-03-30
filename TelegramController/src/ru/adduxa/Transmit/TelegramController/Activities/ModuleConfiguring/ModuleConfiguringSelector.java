package ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring;

import org.telegram.telegrambots.api.objects.Update;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;
import ru.adduxa.Transmit.TelegramController.Activities.Basic.commands.Cancel;
import ru.adduxa.Transmit.TelegramController.Activities.CommandActivity;
import ru.adduxa.Transmit.TelegramController.Activities.IUserActivity;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.TelegramModule.TelegramModuleConfiguringActivity;
import ru.adduxa.Transmit.TelegramController.Activities.ModuleConfiguring.VKModule.VKModuleConfiguringActivity;
import ru.adduxa.Transmit.TelegramController.ControlledCore.ControllingUser;
import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.TransmitCore.Module.TransmitModule;
import ru.adduxa.Transmit.VK.Module.VKModule;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adduxa on 22.02.2016.
 */
public class ModuleConfiguringSelector extends CommandActivity {
	public ModuleConfiguringSelector(IUserActivity previousActivity) {
		super(previousActivity);
		getCommands().registerCommand(new Cancel("Exit module configurer", "Module configurer exited"));
	}

	@Override
	public void pushCommandUpdate(TelegramTransmitController controller, Update update) {
		ModuleActivityGetter getter = new ModuleActivityGetter();
		getter.put(TelegramModule.class, TelegramModuleConfiguringActivity.class);
		getter.put(VKModule.class, VKModuleConfiguringActivity.class);

		ControllingUser user = controller.getOrCreateUser(update.getMessage());

		for(TransmitModule module:
				controller.getTransmitModules().values()) {
			ConfiguringActivity activity = getter.get(user.getActivity().getPreviousActivity(), module);
			if(activity != null) {
				getActivityCommands().registerCommandActivity(module.getName(), activity);
			}
		}
	}

	@Override
	public void popCommandUpdate(TelegramTransmitController controller, Update update) {

	}

	@Override
	public String getInitMessage() {
		return "Select module:";
	}

	@Override
	public void summon(TelegramTransmitController controller, Update update) {
		controller.getTelegramModule().replyTo("Module not found", update.getMessage());
	}

	private class ModuleActivityGetter {
		private final Map<Class<? extends TransmitModule>, Class<? extends ConfiguringActivity>> map = new HashMap<>();

		public void put(Class<? extends TransmitModule> module, Class<? extends ConfiguringActivity> activity) {
			map.put(module, activity);
		}

		public ConfiguringActivity get(IUserActivity previousActivity, TransmitModule module) {
			try {
				return map.get(module.getClass()).getDeclaredConstructor(IUserActivity.class, module.getClass()).newInstance(previousActivity, module);
			} catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
				return null;
			}
		}
	}
}
