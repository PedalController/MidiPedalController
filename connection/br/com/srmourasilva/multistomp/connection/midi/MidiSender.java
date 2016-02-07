package br.com.srmourasilva.multistomp.connection.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

/**
 * Send the messages to real Multistomp
 */
public class MidiSender extends MidiTransmition {

	public MidiSender(MidiDevice device) {
		super(device);
	}

	public void send(MidiMessage message) {
		Receiver receiver;
		try {
			receiver = device().getReceiver();
			receiver.send(message, -1);

		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
}