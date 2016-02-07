package br.com.srmourasilva.main;

import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.simulator.Log;
import br.com.srmourasilva.multistomp.simulator.MultistompSimulator;

public class Simulator {

	public static void main(String[] args) {
		Log log = new Log("Test");
		
		Multistomp pedal = new MultistompSimulator(5);
		pedal.addListener(log);

		pedal.toPatch(3);
		pedal.currentPatch().effects().get(0).active();
		pedal.currentPatch().effects().get(0).params().get(0).addValue();

		System.out.println(pedal);
	}
}
