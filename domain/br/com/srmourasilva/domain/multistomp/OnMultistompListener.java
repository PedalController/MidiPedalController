package br.com.srmourasilva.domain.multistomp;

import br.com.srmourasilva.domain.message.Messages;

public interface OnMultistompListener {
	void onChange(Messages messages);
}
