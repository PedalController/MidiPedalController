package br.com.srmourasilva.multistomp.zoom.gseries.decoder.patch;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.util.MidiMessageTester;

public class ZoomGSeriesPatchNumberDecoder implements MessageDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		MidiMessageTester tester = new MidiMessageTester(message);

		return tester.init().sizeIs(120).test();
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		final int patchNumber = this.getPatchNumber(message);

        return Messages.For(this.generateMessageFor(patchNumber));
	}

	private int getPatchNumber(MidiMessage message) {
		final int PATCH_INFO = 7;

		return message.getMessage()[PATCH_INFO];
	}

    private Message generateMessageFor(int patchNumber) {
    	MultistompDetails details = new MultistompDetails();
        details.patch = patchNumber;

		return new MultistompMessage(CommonCause.PATCH_NUMBER, details);
	}
}
