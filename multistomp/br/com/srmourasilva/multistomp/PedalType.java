package br.com.srmourasilva.multistomp;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.Connection;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.controller.ChangerApplier;

public interface PedalType {
	
	Multistomp generate();

	/**
	 * Generate the connection based in stringDetection
	 * 
	 * The multistomp is necessary because the decoder claims
	 * partial current multistomp state
	 */
	Connection generateConnectionFor(Multistomp multistomp, String stringDetection) throws DeviceNotFoundException;

	MessageEncoder generateEncoder();
	MessageDecoder generateDecoder();
	
	ChangerApplier generateChangerApplier();
}
