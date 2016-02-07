package br.com.srmourasilva.multistomp.zoom;

import br.com.srmourasilva.domain.PedalType;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.MultistompFactory;
import br.com.srmourasilva.multistomp.zoom.g2nu.ZoomG2Nu;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeries;

public class ZoomMultistompFactory implements MultistompFactory {

	@Override
	public Multistomp generate(PedalType type) {
		if (type == PedalType.G2Nu)
			return new ZoomG2Nu();
		else if (type == PedalType.G3)
			return new ZoomGSeries(100, 6);
		else
			return new ZoomGSeries(0, 5);
	}
}