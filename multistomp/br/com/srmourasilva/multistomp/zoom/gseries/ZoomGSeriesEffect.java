package br.com.srmourasilva.multistomp.zoom.gseries;

import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Param;
import br.com.srmourasilva.multistomp.zoom.ZoomEffect;

/**
 * @deprecated Use ZoomG3V2Pedals
 */
@Deprecated
public enum ZoomGSeriesEffect implements ZoomEffect {
	COMP {
		@Override
		public Effect generate() {
			Effect effect = new Effect(0, this.name());

			effect.addParam(new Param("Sense", 0,  10,   6, 1));
			effect.addParam(new Param("Tone",  0,  10,   6, 1));
			effect.addParam(new Param("Level", 0, 150, 100, 1));
			effect.addParam(new Param("ATTCK", 0,   1,   0, 1));

			return effect;
		}
	}
}