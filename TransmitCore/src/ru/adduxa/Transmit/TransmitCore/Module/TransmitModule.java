package ru.adduxa.Transmit.TransmitCore.Module;

/**
 * Created by adduxa on 19.02.2016.
 */
public abstract class TransmitModule implements ITransmitModule {
	private final String name;

	protected TransmitModule(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
