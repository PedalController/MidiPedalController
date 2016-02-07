package br.com.srmourasilva.main;


/**
 * Thanks for:
 * http://www.onicos.com/staff/iz/formats/midi-event.html
 *
 */
public class Demonstration {
	public static void main(String[] args) {
		System.err.println("This demonstration can change any patch effect!");
		return;

		/*
		System.out.println("Pedals Supported: ");
		for (PedalType type : PedalType.values()) {
			System.out.println(" - " + type);
		}
		sleep(1);
		System.out.println();

		System.out.println("Searching a pedal");
		//PedalController pedaleira = PedalControllerFactory.getPedal(PedalType.G2Nu);
		PedalController pedaleira = PedalControllerFactory.searchPedal();
		System.out.println(pedaleira);

		System.out.println("Starting pedal");
		pedaleira.on();
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Set Patch to 5");
		pedaleira.setPatch(5);
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Set Patch to Zero");
		pedaleira.setPatch(0);
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Active effect: ");
		for (int i = 0; i < pedaleira.getAmountEffects(); i++) {
			System.out.println(" - Effect number " + i);
			pedaleira.activeEffect(i);
			sleep(0.5);
		}
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Disable effect: ");
		for (int i = pedaleira.getAmountEffects()-1; i > -1; i--) {
			System.out.println(" - Effect number " + i);
			pedaleira.disableEffect(i);
			sleep(0.5);
		}
		sleep(1);
		System.out.println(pedaleira);

		// TODO - Implementar Mute e Bypass para todos!
		// Jogar na pedaleira e talvez nos pedais
		// Na ZOOM G2 funciona assim:
		// Mute
		//pedaleira.activeEffect(8);
		// Bypass
		//pedaleira.activeEffect(9);

		System.out.println("Next Patch");
		pedaleira.nextPatch();
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Before Patch");
		pedaleira.beforePatch();
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Seth path to 99");
		pedaleira.setPatch(99);
		sleep(1);
		System.out.println(pedaleira);

		System.out.println("Turn down - Bye!");
		pedaleira.off();
		sleep(1);
		System.out.println(pedaleira);
		*/
	}

	public static void sleep(double seconds) {
		try {
		    Thread.sleep((int) (seconds * 1000));
		} catch (InterruptedException e) {
		    Thread.currentThread().interrupt();
		    return;
		}
	}
}