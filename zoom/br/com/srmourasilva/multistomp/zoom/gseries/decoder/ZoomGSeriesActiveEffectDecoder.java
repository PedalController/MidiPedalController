package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import java.util.List;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.Cause;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.sysex.SysexVerbal;
import br.com.srmourasilva.util.MidiMessageTester;

/**
 * Exemplos:
 * 
 * f0 52 00 5a 28 50 0d 00 00 02 00 00 00 02 64 00 00 00 00 56 00 00
 * 00 00 00 00 00 00 00 08 00 00 00 56 00 00 00 00 00 00 00 00 00 00 
 * 00 20 00 56 00 00 00 00 00 00 00 00 00 00 00 00 2e 00 00 00 00 00 
 * 00 00 00 00 00 00 00 00 2f 00 00 20 00 30 04 00 00 00 00 00 00 00 
 * 64 00 1c 00 00 00 00 00 22 20 20 20 20 00 20 20 20 20 20 20 00 f7 
 * 
 *  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 |
 * --------------------------------------------------------------------
 * f0 52 00 5a 28 50 0d 00 00 02 00 00 00 02 64 00 00 00 00 56 00 00 | a
 * 00 00 00 00 00 00 00 08 00 00 00 56 00 00 00 00 00 00 00 00 00 00 | b
 * 00 20 00 56 00 00 00 00 00 00 00 00 00 00 00 00 2e 00 00 00 00 00 | c
 * 00 00 00 00 00 00 00 00 2f 00 00 20 00 30 04 00 00 00 00 00 00 00 | d
 * 64 00 1c 00 00 00 00 00 22 4d 65 6e 69 00 6e 75 20 20 20 20 00 f7 | e
 * 
 * Header (size 4) + ?????????????
 * ????????????????
 * ???????????? + ID_PEDAL + ????
 * ????????????????
 * ???????????????? + Name + END
 * 
 * Header
 *  Def: Header
 *  Position: [(a 1), (a 4)]
 *  Example: f0 52 00 5a
 * 
 * ID_PEDAL
 *  Def: Pedal number 
 *  Position: (c 17)
 *  Example: 2e (COMP)
 * 
 * Name (ascii) 
 *  Def: Patch Name
 *  Position: [(e 10), (e 20)]
 *  Example:    4 chars   + 00 +     6 chars
 *            4d 65 6e 69 | 00 | 6e 75 20 20 20 20
 * 
 * END 
 * 00 f7
 *
 *
   F0       52       00        05      5A
11110000 01010010 00000000 01011010 00101000


PEDAL 1
01000000 00111001 00000000 00000000 00000000 00000000 00000000 00000000 00000010 00000000 00000000 00000000 00000000 
PEDAL 2
00000000 01010110 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00001000 00000000 00000000 
PEDAL 3
00000000 01010110 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00100000
PEDAL 4
00000000 01010110 00000000 00000000 00000000 00000000 00000000 00000001 00000000 00000000 00000000 00000000 00000000 
PEDAL 5
00000000 01010110 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00001100 00000100 00001100 01100100 01100100 
PEDAL 6
00000000 01010110 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 01100100

???
00000000 00011100 00000000 00000000 00100000 00000000 01000000 01011000 

NAME
01100010 01100001 01100001 01100001 00000000 01100001 01100001 01100001 01100001 01100001 01100001 

END
00000000 11110111


Estrutura do pedal (PEDAL 1):
         |Estado (ligado, desligado)
01000000 00111001 00000000 00000000 00000000 00000000 00000000 00000000 00000010 00000000 00000000 00000000 00000000
          |------ Número do Efeito 
 */
public class ZoomGSeriesActiveEffectDecoder implements MessageDecoder {

	private static final int[] PATCHES = new int[] {6, 19, 33, 47, 60, 74};

	@Override
	public final boolean isForThis(MidiMessage message) {
		MidiMessageTester tester = new MidiMessageTester(message);

		return tester.init().sizeIs(110).test();
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		List<Effect> effects = multistomp.currentPatch().effects();

		Messages messages = Messages.Empty(); 
		for (int idPedal = 0; idPedal < PATCHES.length; idPedal++) {
			int patch = PATCHES[idPedal];

			boolean actived = hasActived(message, patch);
			if (actived && !effects.get(idPedal).hasActived())
				messages.add(generateMessageFor(actived, idPedal));
		}

		return messages;
	}

	private boolean hasActived(MidiMessage message, int position) {
		return SysexVerbal.For(message).asBoolean(position, 0);
	}

	private Message generateMessageFor(boolean actived, int effect) {
		Cause cause = actived ? CommonCause.EFFECT_ACTIVE : CommonCause.EFFECT_DISABLE;

		MultistompDetails details = new MultistompDetails();
		details.effect = effect;

		return new MultistompMessage(cause, details);
	}
}