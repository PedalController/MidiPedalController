package br.com.srmourasilva.multistomp.zoom;

import br.com.srmourasilva.domain.multistomp.Effect;
import br.com.srmourasilva.domain.multistomp.Param;

public class ZoomGenericEffect extends Effect {

	public ZoomGenericEffect(int midiId, String name, int sizeParams) {
		super(midiId, name);

		for (int i=0; i<sizeParams; i++)
			this.addParam(new Param("Param " + i, 0, 10, 0, 1));
	}
}