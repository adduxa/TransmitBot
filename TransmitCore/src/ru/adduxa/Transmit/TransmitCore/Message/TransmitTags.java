package ru.adduxa.Transmit.TransmitCore.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by adduxa on 19.02.2016.
 */
public class TransmitTags {
	private final ArrayList<String> listeningTags;
	private final ArrayList<String> ignoringTags;
	private boolean filtering = true;

	public TransmitTags(String[] ignoringTags) {
		this(new ArrayList<>(Arrays.asList(ignoringTags)));
	}

	public TransmitTags(boolean filtering, String[] listeningTags, String[] ignoringTags) {
		this(filtering, new ArrayList<>(Arrays.asList(listeningTags)), new ArrayList<>(Arrays.asList(ignoringTags)));
	}

	public TransmitTags(ArrayList<String> ignoringTags) {
		this(false, new ArrayList<>(), ignoringTags);
	}

	public TransmitTags(boolean filtering, ArrayList<String> listeningTags, ArrayList<String> ignoringTags) {
		this.filtering = filtering;
		this.listeningTags = listeningTags;
		this.ignoringTags = ignoringTags;
	}

	public boolean isMatching(ArrayList<String> messageTags) {
		return (isFiltering()
				&& !Collections.disjoint(messageTags, getListeningTags())
				&& Collections.disjoint(messageTags, getIgnoringTags())
		) || (!isFiltering()
				&& Collections.disjoint(messageTags, getIgnoringTags())
		);
	}

	public boolean isFiltering() {
		return filtering;
	}

	public ArrayList<String> getListeningTags() {
		return listeningTags;
	}

	public ArrayList<String> getIgnoringTags() {
		return ignoringTags;
	}
}
