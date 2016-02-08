package br.com.srmourasilva.multistomp.zoom.gseries;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Details;
import br.com.srmourasilva.domain.message.Messages.Message;

public class ZoomGSeriesMessages {

	public static Messages REQUEST_CURRENT_PATCH_NUMBER() {
		return Messages.For(new Message(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_NUMBER));
	}

	public static Messages REQUEST_CURRENT_PATCH_DETAILS() {
		return Messages.For(new Message(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_DETAILS));
	}
	
	public static Messages REQUEST_SPECIFIC_PATCH_DETAILS(int idPatch) {
		Details details = new Details();
		details.patch = idPatch;

		return Messages.For(new Message(ZoomGSeriesCause.REQUEST_SPECIFIC_PATCH_DETAILS, details));
	}
	
	public static Messages LISSEN_ME() {
		return Messages.For(new Message(ZoomGSeriesCause.LISSEN_ME));
	}
	
	public static Messages YOU_CAN_TALK() {
		return Messages.For(new Message(ZoomGSeriesCause.YOU_CAN_TALK));
	}
	
	/**
	 * @deprecated Use CommonCause.EFFECT_TYPE
	 */
	@Deprecated
	public static Messages SET_EFFECT(int effectPos, int newEffect) {
		Details details = new Details();
		details.effect = effectPos;
		details.value  = newEffect;

		return Messages.For(new Message(ZoomGSeriesCause.SET_EFFECT, details));
	}
}
