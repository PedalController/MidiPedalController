package br.com.srmourasilva.multistomp.zoom.gseries.decoder.effect;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;

/**
 * Message 110
 *
 * 0000 0000
 *      ^^^              0 a 5 -> Menos significativo
 * ^^^^    + (0 a 9) * 5       -> Mais significativo
 *           -----------------
 *                     0 a 49
 *
 * Message 120
 *
 * 0000 0000 | ... | 0000 000_
 *                   ^^^^ ^^^  0 a 128 (2 em 2) => real 0 a 63
 *   ^
 */
// FIXME - Implement!
public class ZoomGSeriesPatchEffectsParamsDecoder implements MessageDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		return true;
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		// First Pedal - First Param
        byte maisSignificativo = message.getMessage()[10];
        byte menosSignificativo = message.getMessage()[12];

        System.out.println(maisSignificativo + "" + menosSignificativo);

        int value = (maisSignificativo >> 5) * 64 + (menosSignificativo >> 1);

        System.out.println("VALUE OF FIRST PEDAL: " + value);

		return Messages.Empty();
	}

}
