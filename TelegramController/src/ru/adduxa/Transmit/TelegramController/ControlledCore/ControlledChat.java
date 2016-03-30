package ru.adduxa.Transmit.TelegramController.ControlledCore;

/**
 * Created by adduxa on 19.02.2016.
 */
public class ControlledChat {
	private final String moduleName;
	private final Long id;
	private String title;
	private final boolean isChannel;
	private final ControllingUser owner;
	private boolean enabled = true;

	public ControlledChat(String moduleName, Long id, String title, boolean isChannel, ControllingUser owner) {
		this.moduleName = moduleName;
		this.id = id;
		this.title = title;
		this.isChannel = isChannel;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ControllingUser getOwner() {
		return owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChannel() {
		return isChannel;
	}
}
