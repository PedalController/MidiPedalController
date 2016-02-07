package br.com.srmourasilva.domain.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sound.midi.MidiMessage;

public class MidiMessages implements Iterable<MidiMessage> {
	
	private List<MidiMessage> midiMessages;

	public MidiMessages() {
		this.midiMessages = new ArrayList<MidiMessage>();
	}

	public MidiMessages concatWith(MidiMessages messages) {
		this.midiMessages.addAll(messages.getMidiMessages());
		return this;
	}

	public MidiMessages concatWith(MidiMessage message) {
		this.midiMessages.add(message);
		return this;
	}

	public List<MidiMessage> getMidiMessages() {
		return midiMessages;
	}

	@Override
	public Iterator<MidiMessage> iterator() {
		return this.midiMessages.iterator();
	}
}
