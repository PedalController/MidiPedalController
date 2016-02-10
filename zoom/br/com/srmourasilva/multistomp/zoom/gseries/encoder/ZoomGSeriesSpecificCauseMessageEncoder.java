package br.com.srmourasilva.multistomp.zoom.gseries.encoder;

import static br.com.srmourasilva.multistomp.connection.codification.MessageEncoderUtil.customMessageFor;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

import br.com.srmourasilva.architecture.exception.ImplemetationException;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesCause;
import br.com.srmourasilva.util.MidiMessagesGenerator;
import br.com.srmourasilva.util.MidiMessagesGenerator.MidiMessageGenerator;

/**
 * Encode ZoomGSeriesCause messages
 */
public class ZoomGSeriesSpecificCauseMessageEncoder implements MessageEncoder {

	@Override
	public MidiMessages encode(Messages messages) {
		MidiMessagesGenerator generator = new MidiMessagesGenerator(messages);

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

		generator.forEachOfType(ZoomGSeriesCause.TUNER_BYPASS_OFF)
				 .generate(tunerBypassOff());
		generator.forEachOfType(ZoomGSeriesCause.TUNER_BYPASS_ON)
				 .generate(tunerBypassOn());

		generator.forEachOfType(ZoomGSeriesCause.TUNER_MUTE_OFF)
				 .generate(tunerMuteOff());
		generator.forEachOfType(ZoomGSeriesCause.TUNER_MUTE_ON)
				 .generate(tunerMuteOn());

		/*
		generator.forEachOfType(ZoomGSeriesCause.CURRENT_PATCH_TAP_TEMPO)
				 .generate(youCanTalk());
		*/
		
		return generator.generateMidiMessages();
	}


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
			MultistompDetails details = (MultistompDetails) message.details();
			int patch = details.patch;
	
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
	
	static MidiMessage lissenMeMessage() {
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
		
	private MidiMessageGenerator tunerBypassOff() {
		return shortMessage(ShortMessage.CONTROL_CHANGE, 75, 0);
	}
	
	private MidiMessageGenerator tunerBypassOn() {
		return shortMessage(ShortMessage.CONTROL_CHANGE, 75, 64);
	}
	
	private MidiMessageGenerator tunerMuteOff() {
		return shortMessage(ShortMessage.CONTROL_CHANGE, 75, 0);
	}
	
	private MidiMessageGenerator tunerMuteOn() {
		return shortMessage(ShortMessage.CONTROL_CHANGE, 75, 64);
	}
	
	private MidiMessageGenerator shortMessage(int channel, int data1, int data2) {
		return message -> {
			MidiMessage messageGenerated;
			
			try {
				messageGenerated = new ShortMessage(channel, 0, data1, data2);
			} catch (InvalidMidiDataException e) {
				throw new ImplemetationException(e);
			}
			
			return new MidiMessages().concatWith(messageGenerated);
		};
	}
}
