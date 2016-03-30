package ru.adduxa.Transmit.TransmitApp;

import ru.adduxa.Transmit.TelegramController.TelegramTransmitController;
import ru.adduxa.Transmit.Telegram.Module.TelegramModule;

/**
 * Created by adduxa on 15.02.2016.
 */
class Main {
	public static void main(String args[]) {
		TelegramModule telegram = new TelegramModule();

		TelegramTransmitController controller = new TelegramTransmitController(telegram);
		controller.addModule(telegram);

		/*
		TransmitPool transmitPool = new TransmitPool();
		transmitPool.addReceiver(telegram.newReceiver(-119944506L, new TransmitTags(new String[] {"#notransmit"})));
		transmitPool.addSender(telegram.newSender("53137039", new TransmitTags(true, new String[] {"#toprivate", "#toall"}, new String[] {"#noprivate", "#notelegram"})));
		transmitPool.addReceiver(telegram.newReceiver(53137039L, new TransmitTags(true, new String[] {"#transmit"}, new String[] {"#notransmit"})));
		transmitPool.addSender(telegram.newSender("-119944506", new TransmitTags(true, new String[] {"#togroup", "#toall"}, new String[] {"#nogroup", "#notelegram"})));
		transmitPool.addSender(telegram.newSender("@qweqweqewqwe", new TransmitTags(true, new String[] {"#tochannel", "#toall"}, new String[] {"#nochannel", "#notelegram"})));
		*/
	}
}
