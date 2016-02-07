package br.com.srmourasilva.domain.multistomp;

import br.com.srmourasilva.domain.PedalType;

public interface MultistompFactory {
	Multistomp generate(PedalType type);
}