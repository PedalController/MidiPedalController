package br.com.srmourasilva.multistomp.zoom.gseries.decoder.patch;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Message;
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
		Message message = new Message(CommonCause.PATCH_NAME);
        message.details().value = this.getNameBy(midiMessage);

		return Messages.For(message);
	}
	
	private String getNameBy(MidiMessage message) {
		int firstChar = ZoomGSeriesPatchNameDecoder.FIRST_LETTER;
        int lastChar  = ZoomGSeriesPatchNameDecoder.LAST_LETTER;

        return SysexVerbal.For(message).interval(firstChar, lastChar).asAsciiString();
	}
}
