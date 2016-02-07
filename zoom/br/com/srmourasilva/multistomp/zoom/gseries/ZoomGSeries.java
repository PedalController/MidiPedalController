package br.com.srmourasilva.multistomp.zoom.gseries;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.Patch;

/** For:
 *  - Zoom G3v2
 *  - Zoom G5
 *  - Zoom Ms-50G
 *  - Zoom Ms-70cd
 *  - Zoom MS-200bt
 *  - Zoom MS-50B
 */
public class ZoomGSeries extends Multistomp {

	private final int TOTAL_PATCHS;
	private final int TOTAL_EFFECTS;

	/**
	 * @param sizePatchs   Max Patches that Pedal may have  
	 * @param totalEffects Max Effects that Patches may have
	 */
	public ZoomGSeries(int totalPatchs, int totalEffects) {
		TOTAL_PATCHS = totalPatchs;
		TOTAL_EFFECTS = totalEffects;

		List<Patch> patchs = createPatchs(TOTAL_PATCHS);

		for (Patch patch : patchs)
			this.addPatch(patch);
	}

	private List<Patch> createPatchs(int totalPatch) {
		List<Patch> patchs = new ArrayList<>();

		for (int i=0; i<totalPatch; i++) {
			Patch patch = new Patch(i);
			for (Effect effect : this.createEffects(TOTAL_EFFECTS))
				patch.addEffect(effect);

			patchs.add(patch);
		}

		return patchs;
	}

	private List<Effect> createEffects(int totalEffects) {
		List<Effect> effects = new ArrayList<Effect>();

		for (int i=0; i < totalEffects; i++)
			effects.add(ZoomGSeriesEffect.COMP.generate());

		return effects;
	}

	@Override
	public Messages start() {
		Messages messages = Messages.Empty();
		messages.concatWith(ZoomGSeriesMessages.LISSEN_ME());
		messages.concatWith(ZoomGSeriesMessages.YOU_CAN_TALK());

		return messages;
	}
}