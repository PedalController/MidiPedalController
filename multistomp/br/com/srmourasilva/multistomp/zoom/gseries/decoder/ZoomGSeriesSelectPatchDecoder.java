package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Details;
import br.com.srmourasilva.domain.message.Messages.Message;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.util.MidiMessageTester;

/**
 * c0 PATCH
 */
public class ZoomGSeriesSelectPatchDecoder implements MessageDecoder {

	private static final int PATCH = 1;

	@Override
	public boolean isForThis(MidiMessage message) {
		final byte[] begin = new byte[] {(byte) 0xc0};

		MidiMessageTester tester = new MidiMessageTester(message);

		return tester.init()
					 .sizeIs(2)
				     .startingWith(begin)
				     .test();
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		Details details = new Details();
		details.patch = message.getMessage()[PATCH];

		return Messages.For(new Message(CommonCause.TO_PATCH, details));
	}
}