package br.com.srmourasilva.multistomp.connection.transport;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;

/*
 * http://stackoverflow.com/questions/6937760/java-getting-input-from-midi-keyboard
 */
public class MidiReader extends MidiTransmition implements Receiver {

	public static interface MidiReaderListener {
		void onDataReceived(MidiMessage message);
	}

	private MidiReaderListener listener;

	public MidiReader(MidiDevice device) throws DeviceNotFoundException {
		super(device);

		try {
			vincule(device, this);
		} catch (MidiUnavailableException e) {
			throw new DeviceNotFoundException(e);
		}
	}

	private void vincule(MidiDevice device, Receiver receiver) throws MidiUnavailableException {
        for (Transmitter transmitter : device.getTransmitters())
        	transmitter.setReceiver(receiver);

        device.getTransmitter().setReceiver(receiver);
	}

	/*************************************************/

	public void setListener(MidiReaderListener listener) {
		this.listener = listener;
	}

	@Override public void close() {}

	@Override
	public void send(MidiMessage message, long arg1) {
		listener.onDataReceived(message);
	}
}