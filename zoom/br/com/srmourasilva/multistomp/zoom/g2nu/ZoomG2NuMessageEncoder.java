package br.com.srmourasilva.multistomp.zoom.g2nu;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import br.com.srmourasilva.domain.message.Cause;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.util.MidiMessagesGenerator;
import br.com.srmourasilva.util.MidiMessagesGenerator.MidiMessageGenerator;

public class ZoomG2NuMessageEncoder implements MessageEncoder {
	
	private static final int STATE_ON = 0x7f;
	private static final int STATE_OFF= 0x00;

	private static final int SET_PATH = ShortMessage.PROGRAM_CHANGE;
	private static final int SET_STATE_EFFECT= ShortMessage.CONTROL_CHANGE;

	@Override
	public MidiMessages encode(Messages messages) {
		MidiMessagesGenerator generator = new MidiMessagesGenerator(messages);

		generator.forEachOfType(CommonCause.TO_PATCH)
				 .generate(encodeMultistompChange());

		generator.forEachOfType(CommonCause.EFFECT_ACTIVE)
		 		 .generate(encodeEffectChange(CommonCause.EFFECT_ACTIVE));
		generator.forEachOfType(CommonCause.EFFECT_DISABLE)
				 .generate(encodeEffectChange(CommonCause.EFFECT_DISABLE));

		return generator.generateMidiMessages();
	}

	private MidiMessageGenerator encodeMultistompChange() {
		return message -> {
			int patch = message.details().patch;

			MidiMessage messageGenerated = shortMessage(SET_PATH, 0, patch, 0);

			return new MidiMessages().concatWith(messageGenerated);
		};
	}
	
	private MidiMessageGenerator encodeEffectChange(Cause cause) {
		return message -> {
			int effect = message.details().effect;

			boolean actived = cause == CommonCause.EFFECT_ACTIVE;

			int byteActived = actived ? STATE_ON : STATE_OFF;

			MidiMessage messageGenerated = shortMessage(SET_STATE_EFFECT, 0, effect, byteActived);

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private ShortMessage shortMessage(int command, int channel, int data1, int data2) {
		try {
			return new ShortMessage(command, channel, data1, data2);
		} catch (InvalidMidiDataException e) {
			throw new RuntimeException(e);
		} 
	}
}
