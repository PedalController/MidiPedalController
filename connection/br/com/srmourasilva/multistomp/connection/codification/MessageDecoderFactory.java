package br.com.srmourasilva.multistomp.connection.codification;

import br.com.srmourasilva.domain.PedalType;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageDecoder;

public class MessageDecoderFactory {

	public static MessageDecoder For(PedalType pedalType) {
		if (PedalType.G3.equals(pedalType))
			return new ZoomGSeriesMessageDecoder();

		throw new RuntimeException();
	}
}
