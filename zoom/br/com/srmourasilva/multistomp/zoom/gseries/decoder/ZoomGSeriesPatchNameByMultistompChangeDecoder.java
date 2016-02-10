package br.com.srmourasilva.multistomp.zoom.gseries.decoder;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;
import br.com.srmourasilva.domain.multistomp.Multistomp;

public class ZoomGSeriesPatchNameByMultistompChangeDecoder extends AbstractZoomGSeriesEffectParamDecoder {

	@Override
	public boolean isForThis(MidiMessage message) {
		return super.isForThis(message) && 
			   message.getMessage()[EFFECT] == 7;
	}

	@Override
	protected Messages decodeThe(MultistompDetails details, Multistomp multistomp) {
		int letterChanged = details.param+2;
		char letterValue = (char) (int) details.value;
		
		StringBuilder newName = new StringBuilder(multistomp.currentPatch().getName());
		newName.setCharAt(letterChanged, letterValue);

		details.patch = multistomp.getIdCurrentPatch();
		details.effect = MultistompDetails.NULL;
		details.param = MultistompDetails.NULL;
		details.value = newName.toString();

		return Messages.For(new MultistompMessage(CommonCause.PATCH_NAME, details));
	}
}