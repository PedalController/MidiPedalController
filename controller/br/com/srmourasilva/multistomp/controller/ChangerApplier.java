package br.com.srmourasilva.multistomp.controller;

import br.com.srmourasilva.domain.message.Messages.Message;

/**
 * The changes dettected by MessageDecoder and encapsuled
 * in Messages are applied in Multistomp by a ChangerApplier 
 *
 */
public interface ChangerApplier {
	void attempt(Message message, PedalController controller);
}
