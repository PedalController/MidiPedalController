package br.com.srmourasilva.multistomp.zoom.gseries.decoder.patch;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.sysex.SysexVerbal;
import br.com.srmourasilva.util.MidiMessageTester;

public class ZoomGSeriesPatchNameDecoder implements MessageDecoder {

	private static final int FIRST_LETTER = 102;
	private static final int LAST_LETTER  = 102 + 11;

	@Override
	public boolean isForThis(MidiMessage message) {
		MidiMessageTester tester = new MidiMessageTester(message);

        boolean patchInfo = tester.init().sizeIs(120).test();
        boolean changeName = false;//tester.init().sizeIs(120).test();

		return patchInfo || changeName;
	}

	@Override
	public Messages decode(MidiMessage midiMessage, Multistomp multistomp) {
		MultistompDetails details = new MultistompDetails();
        details.value = this.getNameBy(midiMessage);

        Message message = new MultistompMessage(CommonCause.PATCH_NAME, details);
		return Messages.For(message);
	}
	
	private String getNameBy(MidiMessage message) {
		int firstChar = ZoomGSeriesPatchNameDecoder.FIRST_LETTER;
        int lastChar  = ZoomGSeriesPatchNameDecoder.LAST_LETTER;

        return SysexVerbal.For(message).interval(firstChar, lastChar).asAsciiString();
	}
}
