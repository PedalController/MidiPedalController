package br.com.srmourasilva.multistomp.controller;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.ImplemetationException;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.Pedal;
import br.com.srmourasilva.multistomp.PedalType;
import br.com.srmourasilva.multistomp.connection.Connection;

public class PedalControllerFactory {

	public static PedalController generateControllerFor(Class<? extends PedalType> classe) throws DeviceNotFoundException {
		PedalType pedalType = null;

		try {
			pedalType = classe.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DeviceNotFoundException(e);
		}

		Multistomp multistomp = pedalType.generate();

		Connection connection = generateConnection(pedalType, multistomp);

		connection.setDecoder(pedalType.generateDecoder());
		connection.setEncoder(pedalType.generateEncoder());

		return new PedalController(multistomp, connection);
	}

	private static Connection generateConnection(PedalType pedalType, Multistomp multistomp) throws DeviceNotFoundException {
		Pedal pedal = pedalType.getClass().getAnnotation(Pedal.class);
		if (pedal == null)
			throw new ImplemetationException("The pedalType " + pedalType.getClass().getName() + " must use @Pedal annotation");

		return pedalType.generateConnectionFor(multistomp, pedal.detection());
	}
}
