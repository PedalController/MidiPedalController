package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.domain.multistomp.Multistomp;

public class ZoomGSeriesDisableEffectDecoder extends AbstractZoomGSeriesEffectParamDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		return super.isForThis(message) && 
			   message.getMessage()[PARAM] == 0 &&
			   message.getMessage()[EFFECT] <= 5;
	}

	@Override
	protected Messages decodeThe(MultistompDetails details, Multistomp multistomp) {
		details.param = MultistompDetails.NULL;
		details.value = null;

		return Messages.For(new MultistompMessage(CommonCause.EFFECT_DISABLE, details));
	}
}