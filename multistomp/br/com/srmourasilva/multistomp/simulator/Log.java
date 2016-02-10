package br.com.srmourasilva.multistomp.simulator;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.multistomp.OnMultistompListener;

public class Log implements OnMultistompListener {
	
	private String type;

	public Log(String type) {
		this.type = type;
	}

	@Override
	public synchronized void onChange(Messages messages) {
		for (Message message : messages)
			System.out.println("LOG:: " + type + " " + message);
	}
}
