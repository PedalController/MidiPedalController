package br.com.srmourasilva.util;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiMessage;

public class MidiMessageTester {
	
	private List<Boolean> tests;
	private MidiMessage message;
	
	public MidiMessageTester(MidiMessage message) {
		this.message = message;
	}
	
	public MidiMessageTester init() {
		this.tests = new ArrayList<>();
		
		return this;
	}

	public boolean test() {
		return !tests.contains(false);
	}

	public MidiMessageTester isLessThen(byte[] bytes) {
		tests.add(message.getLength() < bytes.length);

		return this;
	}

	public MidiMessageTester startingWith(byte[] bytes) {
		if (!(bytes.length <= message.getLength())) {
			tests.add(false);

			return this;
		}

		byte[] midiMessage = message.getMessage();

		for (int i=0; i < bytes.length; i++)
			if (bytes[i] != midiMessage[i])
				tests.add(false);

		tests.add(true);

		return this;
	}

	public MidiMessageTester sizeIs(int size) {
		tests.add(message.getLength() == size);

		return this;
	}

	public MidiMessageTester endingWith(byte[] bytes) {
		if (!(bytes.length <= message.getLength())) {
			tests.add(false);

			return this;
		}

		byte[] midiMessage = message.getMessage();

		for (int i=1; i<=bytes.length; i++)
			if (bytes[bytes.length-i] != midiMessage[midiMessage.length-i])
				tests.add(false);

		tests.add(true);

		return this;
	}
}
