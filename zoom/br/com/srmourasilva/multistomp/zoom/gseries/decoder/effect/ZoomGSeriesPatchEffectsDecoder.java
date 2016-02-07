package br.com.srmourasilva.multistomp.zoom.gseries.decoder.effect;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;

public class ZoomGSeriesPatchEffectsDecoder implements MessageDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
        return true;
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		Messages messages = Messages.Empty();

		MessageDecoder typeEffects   = new ZoomGSeriesPatchEffectsTypeDecoder();
		MessageDecoder effectsStatus = new ZoomGSeriesPatchEffectsStatusDecoder();
		MessageDecoder paramEffects  = new ZoomGSeriesPatchEffectsParamsDecoder();

		// Caution, 'type' can reset the pedal param in the Multistomp.class object
		messages.concatWith(typeEffects.decode(message, multistomp))
				.concatWith(effectsStatus.decode(message, multistomp))
				.concatWith(paramEffects.decode(message, multistomp));

		return messages;
	}
}