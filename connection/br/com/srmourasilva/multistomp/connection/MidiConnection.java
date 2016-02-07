package br.com.srmourasilva.multistomp.connection;

import java.util.Optional;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.PedalType;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoderFactory;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoderFactory;
import br.com.srmourasilva.multistomp.connection.transport.MidiReader;
import br.com.srmourasilva.multistomp.connection.transport.MidiReader.MidiReaderListener;
import br.com.srmourasilva.multistomp.connection.transport.MidiSender;
import br.com.srmourasilva.multistomp.connection.transport.MidiTransmition;
import br.com.srmourasilva.util.BinarioUtil;

public class MidiConnection implements MidiReaderListener {

	public interface OnUpdateListener {
		void update(Messages messages);
	}
	
	private Multistomp multistomp;

	private MidiSender sender;
	private MidiReader reader;

	private MessageEncoder encoder;
	private MessageDecoder decoder;	

	private Optional<OnUpdateListener> listener = Optional.empty();

	public MidiConnection(Multistomp multistomp, PedalType pedalType) throws DeviceNotFoundException {
		this.multistomp = multistomp;

		this.sender = MidiTransmition.getSenderOf(pedalType.getUSBName());
		this.reader = MidiTransmition.getReaderOf(pedalType.getUSBName());
		reader.setListener(this);

		this.encoder = MessageEncoderFactory.For(pedalType);
		this.decoder = MessageDecoderFactory.For(pedalType);
	}

	/*************************************************/
	
	public void start() throws MidiUnavailableException {
		sender.start();
		reader.start();
	}

	public void stop() {
		sender.stop();
		reader.stop();
	}

	/*************************************************/

	public void send(Messages messages) {
		for (MidiMessage midiMessage : generateMidiMessages(messages))
			this.send(midiMessage);
	}

	private MidiMessages generateMidiMessages(Messages messages) {
		return encoder.encode(messages);
	}

	public void send(MidiMessage message) {
		System.out.println("MIDI sended: ");
		System.out.println(" " + BinarioUtil.byteArrayToHex(message.getMessage()));

		this.sender.send(message);
	}

	/*************************************************/

	public void setListener(OnUpdateListener listener) {
		this.listener = Optional.of(listener);
	}

	@Override
	public void onDataReceived(MidiMessage message) {
		System.out.println("MIDI received: ");
    	System.out.println(" " + BinarioUtil.byteArrayToHex(message.getMessage()));

		if (!decoder.isForThis(message)) {
			System.out.println(" command unknown for " + decoder.getClass().getSimpleName());
			return;
		}

		Messages messagesDecoded = decoder.decode(message, multistomp);

    	if (listener.isPresent())
			this.listener.get().update(messagesDecoded);
	}
}
