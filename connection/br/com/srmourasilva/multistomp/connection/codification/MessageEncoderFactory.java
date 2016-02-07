package br.com.srmourasilva.multistomp.connection.codification;

import br.com.srmourasilva.domain.PedalType;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageEncoder;

public class MessageEncoderFactory {

	public static MessageEncoder For(PedalType pedalType) {
		if (PedalType.G3.equals(pedalType))
			return new ZoomGSeriesMessageEncoder();
		
		throw new RuntimeException();
	}
}
