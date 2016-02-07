package br.com.srmourasilva.multistomp.controller;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.domain.PedalType;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.connection.MidiConnection;
import br.com.srmourasilva.multistomp.connection.transport.MidiTransmition;
import br.com.srmourasilva.multistomp.nulo.NullMultistomp;
import br.com.srmourasilva.multistomp.zoom.ZoomMultistompFactory;

public class PedalControllerFactory {
	
	/** 
	 * Search the pedal connected on PC
	 */
	public static PedalController searchPedal() throws DeviceNotFoundException {
		for (PedalType multistomp : PedalType.values())
			if (isConnected(multistomp))
				return getPedal(multistomp);

		throw new DeviceNotFoundException("Has a device connected in this PC?");
	}
	
	private static boolean isConnected(PedalType multistomp) {
		return MidiTransmition.findInfoDevicesBy(multistomp.getUSBName()).size() != 0;
	}

	public static PedalController getPedal(PedalType pedalType) throws DeviceNotFoundException {
		Multistomp pedal;
		PedalCompany company = pedalType.getCompany();

		if (company == PedalCompany.ZoomCorp)
			pedal = new ZoomMultistompFactory().generate(pedalType);

		else if (company == PedalCompany.Roland)
			pedal = new NullMultistomp();

		else if (company == PedalCompany.Line6)
			pedal = new NullMultistomp();

		else
			pedal = new NullMultistomp();

		MidiConnection midiConnection = new MidiConnection(pedal, pedalType);

		return new PedalController(pedal, midiConnection);
	}
}
