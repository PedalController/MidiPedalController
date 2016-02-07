package br.com.srmourasilva.multistomp.zoom.gseries;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.util.MidiMessagesGenerator;
import br.com.srmourasilva.util.MidiMessagesGenerator.MidiMessageGenerator;

public class ZoomGSeriesMessageEncoder implements MessageEncoder {

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
		
		generator.forEachOfType(ZoomGSeriesCause.SET_EFFECT)
				 .generate(setEffect());

		generator.forEachOfType(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_NUMBER)
		 		 .generate(requestCurrentPatchNumber());
		generator.forEachOfType(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_DETAILS)
		 		 .generate(requestCurrentPatchDetails());
		generator.forEachOfType(ZoomGSeriesCause.REQUEST_SPECIFIC_PATCH_DETAILS)
		 		 .generate(requestSpecificPatchDetails());

		generator.forEachOfType(ZoomGSeriesCause.LISSEN_ME)
		 		 .generate(lissenMe());
		generator.forEachOfType(ZoomGSeriesCause.YOU_CAN_TALK)
		 		 .generate(youCanTalk());

		return generator.generateMidiMessages();
	}

	private MidiMessageGenerator toPatch() {
		return message -> {
			final int SET_PATH = ShortMessage.PROGRAM_CHANGE;

			int patch = message.details().patch;

			MidiMessage messageGenerated;

			try {
				messageGenerated = new ShortMessage(SET_PATH, 0, patch, 0);
			} catch (InvalidMidiDataException e) {
				throw new RuntimeException(e);
			}

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator statusEffect(CommonCause cause) {
		return message -> {
			int effect = message.details().effect;
	
			boolean actived = cause == CommonCause.EFFECT_ACTIVE;
			int byteActived = actived ? 0x01 : 0x00;
	
			MidiMessage messageGenerated = manupuleEffect(effect, SET_STATUS, byteActived);

			return new MidiMessages().concatWith(lissenMeMessage())
									 .concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator setParam() {
		return message -> {
			int effect = message.details().effect;
			int param  = message.details().param;
			int value  = (int) message.details().value;
	
			MidiMessage messageGenerated = manupuleEffect(effect, param + PARAM_EFFECT, value);

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator setEffect() {
		return message -> {
			int effect = message.details().effect;
			int value  = (int) message.details().value;
			
			MidiMessage messageGenerated = manupuleEffect(effect, CHANGE_EFFECT, value);
	
			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private final int SET_STATUS = 0;
	private final int CHANGE_EFFECT = 1;
	private final int PARAM_EFFECT = 2; // Base

	private MidiMessage manupuleEffect(int effect, int type, int value) {
		int value2 = value / 128;
		value = value % 128;

		return customMessageFor(new byte[] {
			(byte) 0xF0, (byte)  0x52, (byte)   0x00,
			(byte) 0x5A, (byte)  0x31, (byte) effect,
			(byte) type, (byte) value, (byte) value2,
			(byte) 0xF7
		});
	}


	///////////////////////////////////////
	// SPECIFIC ZOOM
	///////////////////////////////////////

	private MidiMessageGenerator requestCurrentPatchNumber() {
		MidiMessage messageGenerated = customMessageFor(new byte[] {
			(byte) 0xF0, (byte) 0x52, (byte) 0x00,
			(byte) 0x5A, (byte) 0x33, (byte) 0xF7
		});
		
		return message -> new MidiMessages().concatWith(messageGenerated);
	}

	private MidiMessageGenerator requestCurrentPatchDetails() {
		MidiMessage messageGenerated = customMessageFor(new byte[] {
			(byte) 0xF0, (byte) 0x52, (byte) 0x00,
			(byte) 0x5A, (byte) 0x29, (byte) 0xF7
		});

		return message -> new MidiMessages().concatWith(messageGenerated);
	}

	private MidiMessageGenerator requestSpecificPatchDetails() {
		return message -> {
			int patch = message.details().patch;
	
			byte[] CURRENT_PATCH = {
				(byte) 0xF0, (byte)  0x52, (byte) 0x00,
				(byte) 0x5A, (byte)  0x09, (byte) 0x00,
				(byte) 0x00, (byte) patch, (byte) 0xF7
			};

			MidiMessage messageGenerated = customMessageFor(CURRENT_PATCH);

			return new MidiMessages().concatWith(messageGenerated);
		};
	}

	private MidiMessageGenerator lissenMe() {
		return message -> new MidiMessages().concatWith(lissenMeMessage());	
	}
	
	private MidiMessage lissenMeMessage() {
		return customMessageFor(new byte[] {
			(byte) 0xF0, (byte) 0x52, (byte) 0x00,
			(byte) 0x5A, (byte) 0x50, (byte) 0xF7
		});
	}

	private MidiMessageGenerator youCanTalk() {
		MidiMessage messageGenerated = customMessageFor(new byte[] {
			(byte) 0xF0, (byte) 0x52, (byte) 0x00,
			(byte) 0x5A, (byte) 0x16, (byte) 0xF7
		});
				
		return message -> new MidiMessages().concatWith(messageGenerated);
	}

	private SysexMessage customMessageFor(byte[] message) {
		try {
			return new SysexMessage(message, message.length);
		} catch (InvalidMidiDataException e) {
			throw new RuntimeException(e);
		}
	}
}
