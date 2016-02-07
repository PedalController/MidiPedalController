package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Details;
import br.com.srmourasilva.domain.message.Messages.Message;
import br.com.srmourasilva.domain.multistomp.Multistomp;

/**
 * f0 52 00 5a 31   05    02   02   00 f7
 * f0 52 00 5a 31 Pedal Param Value 00 f7
 */
public class ZoomGSeriesSetValueParamDecoder extends AbstractZoomGSeriesEffectParamDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		return super.isForThis(message) &&
			   message.getMessage()[PARAM] >= 2;
	}

	@Override
	protected Messages decodeThe(Details details, Multistomp multistomp) {
		return Messages.For(new Message(CommonCause.PARAM_VALUE, details));
	}
}