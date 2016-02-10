package br.com.srmourasilva.multistomp.zoom.gseries;

import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;

public class ZoomGSeriesMessages {

	public static Message REQUEST_CURRENT_PATCH_NUMBER() {
		return new MultistompMessage(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_NUMBER);
	}

	public static Message REQUEST_CURRENT_PATCH_DETAILS() {
		return new MultistompMessage(ZoomGSeriesCause.REQUEST_CURRENT_PATCH_DETAILS);
	}
	
	public static Message REQUEST_SPECIFIC_PATCH_DETAILS(int idPatch) {
		MultistompDetails details = new MultistompDetails();
		details.patch = idPatch;

		return new MultistompMessage(ZoomGSeriesCause.REQUEST_SPECIFIC_PATCH_DETAILS, details);
	}
	
	public static Message LISSEN_ME() {
		return new MultistompMessage(ZoomGSeriesCause.LISSEN_ME);
	}
	
	public static Message YOU_CAN_TALK() {
		return new MultistompMessage(ZoomGSeriesCause.YOU_CAN_TALK);
	}
	
	public static Message TUNER_MUTE_ON() {
		return new MultistompMessage(ZoomGSeriesCause.TUNER_MUTE_ON);
	}
	
	public static Message TUNER_MUTE_OFF() {
		return new MultistompMessage(ZoomGSeriesCause.TUNER_MUTE_OFF);
	}
	
	public static Message TUNER_BYPASS_ON() {
		return new MultistompMessage(ZoomGSeriesCause.TUNER_BYPASS_ON);
	}
	
	public static Message TUNER_BYPASS_OFF() {
		return new MultistompMessage(ZoomGSeriesCause.TUNER_BYPASS_OFF);
	}
	
	public static Message PATCH_TAP_TEMPO(int bpm) {
		if (bpm < 40) bpm = 40;
		else if (bpm > 250) bpm = 250;

		MultistompDetails details = new MultistompDetails();
		details.value = bpm;

		return new MultistompMessage(ZoomGSeriesCause.CURRENT_PATCH_TAP_TEMPO, details);
	}
}
