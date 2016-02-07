package br.com.srmourasilva.multistomp.zoom.gseries.decoder.effect;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Message;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomG3v2Pedals;
import br.com.srmourasilva.sysex.SysexVerbal;

public class ZoomGSeriesPatchEffectsTypeDecoder implements MessageDecoder {

	private static final int[] PATCHES = new int[] {6+5, 19+5, 33+5, 47+5, 60+5, 74+5};
	//private static final byte[] PATCHES_MASK = new byte[] {0b01000000, 0b00000010, 0b00001000, 0b00100000, 0b0000001, 0b00000100};
	private static final int[] PATCHES_BIT_POS = new int[] {6, 1, 3, 5, 0, 2}; // Based by PATCHES_MASK
	private static final int[] DECAY = new int[] {1, 6, 4, 2, 7, 5};

	@Override
	public boolean isForThis(MidiMessage message) {
		return true;
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		Messages messages = Messages.Empty();
		
		for (int i=0; i<6; i++) {
			Message msg = new Message(CommonCause.EFFECT_TYPE);
		    msg.details().effect = i;
		    msg.details().value = ZoomG3v2Pedals.instance.getEffect(pedal(i, message));

			messages = messages.concatWith(Messages.For(msg));
		}

		return messages;
	}
	
	private int pedal(int index, MidiMessage message) {
		int byte64Position = PATCHES[index] - DECAY[index];
		int bit64Position = PATCHES_BIT_POS[index];
		int byte32Position = PATCHES[index];
		

		return SysexVerbal.For(message).concatOf(byte64Position).bit(bit64Position)
									   .concatOf(byte32Position).bits(1, 7)
									   .asInteger();
	}
}
