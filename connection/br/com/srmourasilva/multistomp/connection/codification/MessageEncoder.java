package br.com.srmourasilva.multistomp.connection.codification;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;

/** 
 * Generate the MidiMessage based in the changes
 * described in ChangeMessage<Multistomp>
 */
public interface MessageEncoder {
	MidiMessages encode(Messages messages);
}
