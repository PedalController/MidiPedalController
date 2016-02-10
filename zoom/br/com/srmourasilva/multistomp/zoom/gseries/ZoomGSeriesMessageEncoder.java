package br.com.srmourasilva.multistomp.zoom.gseries;

import java.util.ArrayList;

import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.MidiMessages;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.encoder.ZoomGSeriesCommonCauseMessageEncoder;
import br.com.srmourasilva.multistomp.zoom.gseries.encoder.ZoomGSeriesSpecificCauseMessageEncoder;

public class ZoomGSeriesMessageEncoder implements MessageEncoder {

	private ArrayList<MessageEncoder> encoders;

	public ZoomGSeriesMessageEncoder() {
		this.encoders = new ArrayList<MessageEncoder>();
		
		this.encoders.add(new ZoomGSeriesCommonCauseMessageEncoder());
		this.encoders.add(new ZoomGSeriesSpecificCauseMessageEncoder());
	}
	
	@Override
	public MidiMessages encode(Messages messages) {
		MidiMessages midiMessages = new MidiMessages();
		
		for (MessageEncoder encoder : encoders)
			midiMessages.concatWith(encoder.encode(messages));
		
		return midiMessages;
	} 
}
