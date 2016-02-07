package br.com.srmourasilva.multistomp.zoom.gseries.decoder.patch;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.effect.ZoomGSeriesPatchEffectsDecoder;
import br.com.srmourasilva.util.MidiMessageTester;

public class ZoomGSeriesPatchDecoder implements MessageDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		final int MESSAGE_SIZE = 120;

		MidiMessageTester tester = new MidiMessageTester(message);

		return tester.init().sizeIs(MESSAGE_SIZE).test();
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		Messages messages = Messages.Empty();

		MessageDecoder patchName   = new ZoomGSeriesPatchNameDecoder();
		MessageDecoder patchNumber = new ZoomGSeriesPatchNumberDecoder();
		MessageDecoder effects     = new ZoomGSeriesPatchEffectsDecoder();

		messages.concatWith(patchName.decode(message, multistomp))
				.concatWith(patchNumber.decode(message, multistomp))
				.concatWith(effects.decode(message, multistomp));

		return messages;
	}	
}