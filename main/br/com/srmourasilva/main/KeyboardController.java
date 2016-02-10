package br.com.srmourasilva.main;

import java.util.Scanner;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.multistomp.controller.PedalController;
import br.com.srmourasilva.multistomp.controller.PedalControllerFactory;
import br.com.srmourasilva.multistomp.zoom.ZoomG3Type;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessages;

public class KeyboardController {
	
	private PedalController pedal;
	private Scanner in;

	public static void main(String[] args) {
		new KeyboardController();
	}

	public KeyboardController() {
		this.pedal = init();

		String command = "";
		while (!command.toUpperCase().equals("EXIT")) {
			menu();

			command = getCommand();
			execute(command);
			
			//System.out.println("\n" + pedal);
		}

		this.pedal.off();
	}

	private PedalController init() {
		PedalController pedal = null;
		this.in = new Scanner(System.in);

		try {
			//pedal = PedalControllerFactory.generateControllerFor(ZoomG3Type.G2Nu);
			pedal = PedalControllerFactory.generateControllerFor(ZoomG3Type.class);
			//pedal.addListener(message -> System.out.println(message));
			pedal.on();

		} catch (DeviceNotFoundException e) {
			System.out.println("Pedal not found! You connected any?");
			System.exit(1);
		} catch (DeviceUnavailableException e) {
			System.out.println("This Pedal has been used by other process program");
			System.exit(1);
		}

		return pedal;
	}

	private void menu() {
		System.out.println("Play Command:");
		System.out.println(" - 'GT number': GT = Go to 'number' Patch");
		System.out.println(" - 'AC number': Active 'number' effect");
		System.out.println(" - 'DB number': Disable 'number' effect'");
		System.out.println(" - 'P effect param newValue': Set Param value of any effect");
		System.out.println(" - 'R': LISSEN_ME");
		System.out.println(" - 'T': Tela status? YOU_CAN_TALK");
		System.out.println(" - 'C': REQUEST_CURRENT_PATCH_NUMBER");
		System.out.println(" - 'I': REQUEST_CURRENT_PATCH_DETAILS");
		System.out.println(" - 'IO': REQUEST_SPECIFIC_PATCH_DETAILS('number' patch)");
		System.out.println(" - '?': ???");

		System.out.println(" - 'Exit': To exit");
	}

	private String getCommand() {
	    System.out.print("Command: ");
	    return in.nextLine();
	}

	private void execute(String command) {
		command = command.toUpperCase();
		String commands[] = command.split(" ");

		String action = commands[0];
		
		if (action.equals("GT"))
			pedal.toPatch(Integer.parseInt(commands[1]));

		else if (action.equals("AC"))
			pedal.activeEffect(Integer.parseInt(commands[1]));

		else if (action.equals("DB"))
			pedal.disableEffect(Integer.parseInt(commands[1]));

		else if (action.equals("R")) {
			pedal.send(ZoomGSeriesMessages.LISSEN_ME());

		} else if (action.equals("T")) {
			pedal.send(ZoomGSeriesMessages.YOU_CAN_TALK());

		} else if (action.equals("C")) {
			pedal.send(ZoomGSeriesMessages.REQUEST_CURRENT_PATCH_NUMBER());

		} else if (action.equals("I")) {
			pedal.send(ZoomGSeriesMessages.REQUEST_CURRENT_PATCH_DETAILS());

		} else if (action.equals("IO")) {
			int idPatch = Integer.parseInt(commands[1]);

			pedal.send(ZoomGSeriesMessages.REQUEST_SPECIFIC_PATCH_DETAILS(idPatch));

		} else if (action.equals("P")) {
			int idEffect = Integer.parseInt(commands[1]);
			int idParam = Integer.parseInt(commands[2]);
			int value = Integer.parseInt(commands[3]);

			pedal.setEffectParam(idEffect, idParam, value);

		} else if (action.equals("?")) {
			System.out.println("Recebe sempre: f0 7e 00 06 02 52 5a 00 00 00 32 2e 31 30 f7 ");
			byte[] WHAT = {
				(byte) 0xF0, (byte) 0x7E, (byte) 0x00,
				(byte) 0x06, (byte) 0x01, (byte) 0xF7
			};

			//pedal.send(MessageEncoderUtil.customMessageFor(WHAT));
		}
	}
}