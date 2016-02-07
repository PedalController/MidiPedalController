package br.com.srmourasilva.multistomp.zoom.gseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sound.midi.MidiMessage;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.ZoomGSeriesActiveEffectDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.ZoomGSeriesDisableEffectDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.ZoomGSeriesPatchNameByMultistompChangeDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.ZoomGSeriesSelectPatchDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.ZoomGSeriesSetValueParamDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.decoder.patch.ZoomGSeriesPatchDecoder;

public class ZoomGSeriesMessageDecoder implements MessageDecoder {

	private List<MessageDecoder> decoders;

	public ZoomGSeriesMessageDecoder() {
		decoders = new ArrayList<>();

		decoders.add(new ZoomGSeriesPatchDecoder());
		
		decoders.add(new ZoomGSeriesActiveEffectDecoder());
		decoders.add(new ZoomGSeriesDisableEffectDecoder());
		decoders.add(new ZoomGSeriesPatchNameByMultistompChangeDecoder());
		
		decoders.add(new ZoomGSeriesSelectPatchDecoder());
		decoders.add(new ZoomGSeriesSetValueParamDecoder());
	}

	@Override
	public boolean isForThis(MidiMessage message) {
		return decodeFor(message).isPresent();
	}

	@Override
	public Messages decode(MidiMessage message, Multistomp multistomp) {
		Optional<MessageDecoder> decoder = decodeFor(message);
		
		if (decoder.isPresent())
			return decoder.get().decode(message, multistomp);

		throw new RuntimeException("The message isn't for this implementation");
	}

	private Optional<MessageDecoder> decodeFor(MidiMessage message) {
		for (MessageDecoder decoder : decoders)
			if (decoder.isForThis(message))
				return Optional.of(decoder);

		return Optional.empty();
	}
}
