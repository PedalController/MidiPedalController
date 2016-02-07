package br.com.srmourasilva.multistomp.connection.midi;

import java.util.Optional;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.Connection;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.connection.midi.MidiReader.MidiReaderListener;
import br.com.srmourasilva.util.BinaryUtil;

public class MidiConnection implements Connection, MidiReaderListener {

	public interface OnUpdateListener {
		void update(Messages messages);
	}
	
	private Multistomp multistomp;

	private MidiSender sender;
	private MidiReader reader;

	private MessageEncoder encoder;
	private MessageDecoder decoder;	

	private Optional<OnUpdateListener> listener = Optional.empty();

	public MidiConnection(Multistomp multistomp, String stringDetection) throws DeviceNotFoundException {
		this.multistomp = multistomp;

		this.sender = MidiTransmition.getSenderOf(stringDetection);
		this.reader = MidiTransmition.getReaderOf(stringDetection);
		reader.setListener(this);
	}

	/*************************************************/
	
	@Override
	public void start() throws DeviceUnavailableException {
		try {
			sender.start();
			reader.start();
		} catch (MidiUnavailableException e) {
			throw new DeviceUnavailableException(e);
		}
	}

	@Override
	public void stop() {
		sender.stop();
		reader.stop();
	}

	/*************************************************/
	
	public void setEncoder(MessageEncoder encoder) {
		this.encoder = encoder;
	}
	
	public void setDecoder(MessageDecoder decoder) {
		this.decoder = decoder;
	}
	
	/*************************************************/

	@Override
	public void send(Messages messages) {
		for (MidiMessage midiMessage : generateMidiMessages(messages))
			this.send(midiMessage);
	}

	private MidiMessages generateMidiMessages(Messages messages) {
		return encoder.encode(messages);
	}

	private void send(MidiMessage message) {
		System.out.println("MIDI sended: ");
		System.out.println(" " + BinaryUtil.byteArrayToHex(message.getMessage()));

		this.sender.send(message);
	}

	/*************************************************/

	public void setListener(OnUpdateListener listener) {
		this.listener = Optional.of(listener);
	}

	@Override
	public void onDataReceived(MidiMessage message) {
		System.out.println("MIDI received: ");
    	System.out.println(" " + BinaryUtil.byteArrayToHex(message.getMessage()));

		if (!decoder.isForThis(message)) {
			System.out.println(" command unknown for " + decoder.getClass().getSimpleName());
			return;
		}

		Messages messagesDecoded = decoder.decode(message, multistomp);

    	if (listener.isPresent())
			this.listener.get().update(messagesDecoded);
	}
}
