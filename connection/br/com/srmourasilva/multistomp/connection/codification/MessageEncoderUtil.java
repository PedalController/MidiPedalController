package br.com.srmourasilva.multistomp.connection.codification;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import br.com.srmourasilva.architecture.exception.ImplemetationException;

public class MessageEncoderUtil {
	private MessageEncoderUtil() {}
	
	public static SysexMessage customMessageFor(byte[] message) {
		try {
			return new SysexMessage(message, message.length);
		} catch (InvalidMidiDataException e) {
			throw new ImplemetationException(e);
		}
	}
}
