package br.com.srmourasilva.multistomp.zoom;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.Pedal;
import br.com.srmourasilva.multistomp.PedalType;
import br.com.srmourasilva.multistomp.connection.Connection;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.connection.midi.MidiConnection;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeries;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageDecoder;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessageEncoder;

@Pedal(company="Zoom Corporation", name="Zoom G3", detection="Series")
public class ZoomG3Type implements PedalType {

	@Override
	public Multistomp generate() {
		return new ZoomGSeries(100, 6);
	}
	
	@Override
	public Connection generateConnectionFor(Multistomp multistomp, String stringDetection) throws DeviceNotFoundException {
		return new MidiConnection(multistomp, stringDetection);
	}
	
	@Override
	public MessageEncoder generateEncoder() {
		return new ZoomGSeriesMessageEncoder();
	}

	@Override
	public MessageDecoder generateDecoder() {
		return new ZoomGSeriesMessageDecoder();
	}
}
