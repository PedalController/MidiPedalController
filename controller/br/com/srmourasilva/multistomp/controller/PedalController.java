package br.com.srmourasilva.multistomp.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.OnMultistompListener;
import br.com.srmourasilva.multistomp.connection.Connection;
import br.com.srmourasilva.multistomp.simulator.Log;

public class PedalController {

	private boolean started;
	private boolean notifyChangesToDevice = true;

	private Multistomp pedal;

	private Connection connection;

	private List<OnMultistompListener> controllerListeners = new ArrayList<>();
	private List<OnMultistompListener> realMultistompListeners = new ArrayList<>();

	public PedalController(Multistomp pedal, Connection conection) throws DeviceNotFoundException {
		this.started = false;

		this.pedal = pedal;

		this.connection = conection;
		// Real multistomp Change
		this.connection.setListener(messages -> {
			MultistompChanger changer = new MultistompChanger(this);
			messages.forEach(message -> changer.attempt(message));

			notify(realMultistompListeners, messages);
		});

		// Multistomp Change
		this.pedal.addListener(messages -> {
			if (this.notifyChangesToDevice) {
				connection.send(messages);
				notify(controllerListeners, messages);
			}
		});

		this.controllerListeners.add(new Log("Controller"));
		this.realMultistompListeners.add(new Log("Real Multistomp"));
	}

	/*************************************************/

	/** Turn on and inicialize the pedal
	 */
	public final void on() throws DeviceUnavailableException {
		if (started)
			return;

		started = true;
		connection.start();

		connection.send(pedal.start());
	}

	/** Close connection and turn off the pedal
	 */
	public final void off() {
		if (!started)
			return;

		started = false;
		connection.stop();
	}

	/*************************************************/

	public final boolean hasStated() {
		return started;
	}

	public final Multistomp multistomp() {
		return pedal;
	}


	/*************************************************/

	public void nextPatch() {
		this.pedal.nextPatch();
	}

	public void beforePatch() {
		this.pedal.beforePatch();
	}

	public void toPatch(int index) {
		this.pedal.toPatch(index);
	}


	/*************************************************/

	public void toogleEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).toggle();	
	}

	public boolean hasActived(int idEffect) {
		return this.pedal.currentPatch().effects().get(idEffect).hasActived();
	}

	public void activeEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).active();
	}

	public void disableEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).disable();
	}

	public void setEffectParam(int idEffect, int idParam, int value) {
		this.pedal.currentPatch().effects().get(idEffect).params().get(idParam).setValue(value);
	}

	/** @return Amount of effects that the current patch has
	 */
	public int getAmountEffects() {
		return this.pedal.currentPatch().effects().size();
	}

	public void addListener(OnMultistompListener listener) {
		this.pedal.addListener(listener);
	}

	/*************************************************/

	public void disableNotificationChangesToDevice() {
		this.notifyChangesToDevice = false;
	}

	public void activeNotificationChangesToDevice() {
		this.notifyChangesToDevice = true;
	}
	/*************************************************/

	public final String toString() {
		String retorno = "State: ";
		retorno += started ? "On" : "Off";
		retorno += "\n";

		return retorno + this.pedal.toString();
	}

	private void notify(List<OnMultistompListener> listeners, Messages messages) {
		for (OnMultistompListener listener : listeners)
			listener.onChange(messages);
	}
	
	public void send(Messages messages) {
		this.connection.send(messages);
	}
}