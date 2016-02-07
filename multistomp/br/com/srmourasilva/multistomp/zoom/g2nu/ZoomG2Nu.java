package br.com.srmourasilva.multistomp.zoom.g2nu;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.Patch;
import br.com.srmourasilva.multistomp.zoom.ZoomGenericEffect;

/** For:
 *  - Zoom G2Nu
 */
public class ZoomG2Nu extends Multistomp {

	private static final int TOTAL_PATCHS = 100;
	
	/** Number of parameters that can have an Effect
	 *
	 *  Equalizer have 6
	 *  All other have max 3
	 */
	private static final int SIZE_PARAMS = 6;

	public ZoomG2Nu() {
		List<Patch> patchs = createPatchs(TOTAL_PATCHS);

		for (Patch patch : patchs)
			this.addPatch(patch);
	}

	private List<Patch> createPatchs(int totalPatch) {
		List<Patch> patchs = new ArrayList<>();

		for (int i=0; i<totalPatch; i++) {
			Patch patch = new Patch(i);

			for (Effect effect : createEffects())
				patch.addEffect(effect);

			patchs.add(patch);
		}

		return patchs;
	}

	@Override
	public Messages start() {
		return Messages.Empty();
	}

	/** Very thanks for: 
	 * http://www.loopers-delight.com/LDarchive/201104/msg00195.html
	 */
	private List<Effect> createEffects() {
		List<Effect> effects = new ArrayList<Effect>();
		effects.add(new ZoomGenericEffect(65, "Comp",       SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(66, "Efx",        SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(68, "Drive",      SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(69, "EQ",         SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(70, "ZNR",        SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(71, "Modulation", SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(72, "Delay",      SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(73, "Reverb",     SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(74, "Mute",       SIZE_PARAMS));
		effects.add(new ZoomGenericEffect(75, "Bypass",     SIZE_PARAMS));

		return effects;
	}
}