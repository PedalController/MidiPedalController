package br.com.srmourasilva.multistomp.nulo;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.Patch;

public class NullMultistomp extends Multistomp {

	private String MSG_ERROR = "Pedal Unknown is unimplemented";

	public NullMultistomp() {
		this.addPatch(new Patch(0));
		System.out.println(MSG_ERROR);
	}

	@Override
	public Messages start() {
		return Messages.Empty();
	}
}