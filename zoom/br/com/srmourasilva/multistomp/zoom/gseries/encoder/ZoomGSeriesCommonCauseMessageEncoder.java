package br.com.srmourasilva.multistomp.zoom.gseries.encoder;

import static br.com.srmourasilva.multistomp.connection.codification.MessageEncoderUtil.customMessageFor;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import br.com.srmourasilva.architecture.exception.ImplemetationException;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.util.MidiMessagesGenerator;
import br.com.srmourasilva.util.MidiMessagesGenerator.MidiMessageGenerator;

/**
 * Encode CommonCause messages
 */
public class ZoomGSeriesCommonCauseMessageEncoder implements MessageEncoder {

	@Override
	public MidiMessages encode(Messages messages) {
		MidiMessagesGenerator generator = new MidiMessagesGenerator(messages);

		generator.forEachOfType(CommonCause.TO_PATCH)
				 .generate(toPatch());
		
		generator.forEachOfType(CommonCause.EFFECT_ACTIVE)
		 		 .generate(statusEffect(CommonCause.EFFECT_ACTIVE));
		generator.forEachOfType(CommonCause.EFFECT_DISABLE)
		 		 .generate(statusEffect(CommonCause.EFFECT_DISABLE));

		generator.forEachOfType(CommonCause.PARAM_VALUE)
		 		 .generate(setParam());
		
		generator.forEachOfType(CommonCause.EFFECT_TYPE)
				 .generate(setEffect());

		return generator.generateMidiMessages();
	}

	private MidiMessageGenerator toPatch() {
		return message -> {
			final int SET_PATH = ShortMessage.PROGRAM_CHANGE;

			MultistompDetails details = (MultistompDetails) message.details();
			int patch = details.patch;

			MidiMessage messageGenerated;

			try {
				messageGenerated = new ShortMessage(SET_PATH, 0, patch, 0);
			} catch (InvalidMidiDataException e) {
				throw new ImplemetationException(e);
			}

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator statusEffect(CommonCause cause) {
		return message -> {
			MultistompDetails details = (MultistompDetails) message.details();
			int effect = details.effect;
	
			boolean actived = cause == CommonCause.EFFECT_ACTIVE;
			int byteActived = actived ? 0x01 : 0x00;
	
			MidiMessage messageGenerated = manipuleEffect(effect, Manipulation.SET_STATUS, byteActived);

			return new MidiMessages().concatWith(ZoomGSeriesSpecificCauseMessageEncoder.lissenMeMessage())
									 .concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator setParam() {
		return message -> {
			MultistompDetails details = (MultistompDetails) message.details();
			int effect = details.effect;
			int param  = details.param;
			int value  = (int) details.value;
	
			MidiMessage messageGenerated = manipuleEffectParam(effect, param, value);

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator setEffect() {
		return message -> {
			MultistompDetails details = (MultistompDetails) message.details();
			int effect = details.effect;
			int value  = (int) details.value;
			
			MidiMessage messageGenerated = manipuleEffect(effect, Manipulation.CHANGE_EFFECT, value);
	
			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private enum Manipulation {
		SET_STATUS(0),
		CHANGE_EFFECT(1),
		PARAM_EFFECT(2);
		
		private byte identifier;

		Manipulation(int identifier) {
			this.identifier = (byte) identifier;
		}
		
		public byte getIdentifier() {
			return identifier;
		}
	}

	private MidiMessage manipuleEffectParam(int effect, int posParam, int value) {
		return manipuleEffectImpl(effect, posParam + Manipulation.PARAM_EFFECT.getIdentifier(), value);
	}

	private MidiMessage manipuleEffect(int effect, Manipulation type, int value) {
		return manipuleEffectImpl(effect, type.getIdentifier(), value);
	}

	private MidiMessage manipuleEffectImpl(int effect, int type, int value) {
		int value2 = value / 128;
		value = value % 128;

		return customMessageFor(new byte[] {
			(byte) 0xF0, (byte)  0x52, (byte)   0x00,
			(byte) 0x5A, (byte)  0x31, (byte) effect,
			(byte) type, (byte) value, (byte) value2,
			(byte) 0xF7
		});
	} 
}
