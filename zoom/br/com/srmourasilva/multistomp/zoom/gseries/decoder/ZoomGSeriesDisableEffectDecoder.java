package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Details;
import br.com.srmourasilva.domain.multistomp.Multistomp;

public class ZoomGSeriesDisableEffectDecoder extends AbstractZoomGSeriesEffectParamDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		return super.isForThis(message) && 
			   message.getMessage()[PARAM] == 0 &&
			   message.getMessage()[EFFECT] <= 5;
	}

	@Override
	protected Messages decodeThe(Details details, Multistomp multistomp) {
		details.param = Details.NULL;
		details.value = null;

		return Messages.Empty().add(CommonCause.EFFECT_DISABLE, details);
	}
}