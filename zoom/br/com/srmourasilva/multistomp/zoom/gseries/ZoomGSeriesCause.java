package br.com.srmourasilva.multistomp.zoom.gseries;

import br.com.srmourasilva.domain.message.Cause;

public enum ZoomGSeriesCause implements Cause {
	REQUEST_CURRENT_PATCH_NUMBER,
	REQUEST_CURRENT_PATCH_DETAILS,
	REQUEST_SPECIFIC_PATCH_DETAILS, 
	
	LISSEN_ME,
	YOU_CAN_TALK,
	
	@Deprecated
	SET_EFFECT;
}
