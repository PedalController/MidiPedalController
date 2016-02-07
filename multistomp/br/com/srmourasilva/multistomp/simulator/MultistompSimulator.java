package br.com.srmourasilva.multistomp.simulator;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.Param;
import br.com.srmourasilva.domain.multistomp.Patch;

public class MultistompSimulator extends Multistomp {

	public MultistompSimulator(int totalPatch) {
		for (int i = 0; i < totalPatch; i++) {
			Patch patch = new Patch(i);
			for (Effect effect : genEffects())
				patch.addEffect(effect);

			this.addPatch(patch);
		}
	}

	private List<Effect> genEffects() {
		List<Effect> effects = new ArrayList<>();

		effects.add(genEffect(0, "Overdrive"));
		effects.add(genEffect(1, "Reverb"));
		effects.add(genEffect(2, "Chorus"));

		return effects;
	}

	private Effect genEffect(int id, String name) {
		Effect effect = new Effect(id, name);
		effect.addParam(new Param("Gain", 0, 50, 0, 1));
		effect.addParam(new Param("Volume", 0, 25, 0, 1));
		
		return effect;
	}

	@Override
	public Messages start() {
		return Messages.Empty();
	}
}