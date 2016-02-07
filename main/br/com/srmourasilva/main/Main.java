package br.com.srmourasilva.main;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.multistomp.controller.PedalController;
import br.com.srmourasilva.multistomp.controller.PedalControllerFactory;
import br.com.srmourasilva.multistomp.zoom.ZoomG3Type;

public class Main {
	public static void main(String[] args) throws DeviceNotFoundException, DeviceUnavailableException {
		// Throws DeviceNotFoundException if not found
		PedalController multistomp = PedalControllerFactory.generateControllerFor(ZoomG3Type.class);

		// Init the system (not your pedal)
		// Throws MidiUnavailableException
		multistomp.on();

		// TO ZOOM G2Nu
		// 0 to 7: Comp to Reverb
		// 8 Mute
		// 9 Bypass

		multistomp.activeEffect(0);
		multistomp.disableEffect(1);

		// TO ZOOM G3
		// 0 to 5 (6 Pedals)
		// ...

		// Set Patch
		multistomp.beforePatch();
		multistomp.nextPatch();
		multistomp.toPatch(99);

		System.out.println(multistomp);

		// Turn down the system, not your pedal :P
		multistomp.off();
	}
}
