package br.com.srmourasilva.multistomp.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.OnMultistompListener;
import br.com.srmourasilva.multistomp.connection.Connection;
import br.com.srmourasilva.multistomp.simulator.Log;

public class PedalControllerImpl implements PedalController {

	private boolean started;
	private boolean notifyChangesToDevice = true;

	private Multistomp pedal;

	private Connection connection;

	private List<OnMultistompListener> controllerListeners = new ArrayList<>();
	private List<OnMultistompListener> realMultistompListeners = new ArrayList<>();

	public PedalControllerImpl(Multistomp pedal, Connection conection, ChangeApplier applier) throws DeviceNotFoundException {
		this.started = false;

		this.pedal = pedal;

		this.connection = conection;
		// Real multistomp Change
		this.connection.setListener(messages -> {
			messages.forEach(message -> applier.attempt(message, this));

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

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#on()
	 */
	@Override
	public final void on() throws DeviceUnavailableException {
		if (started)
			return;

		started = true;
		connection.start();

		connection.send(pedal.start());
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#off()
	 */
	@Override
	public final void off() {
		if (!started)
			return;

		started = false;
		connection.stop();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#hasStated()
	 */

	@Override
	public final boolean hasStated() {
		return started;
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#multistomp()
	 */
	@Override
	public final Multistomp multistomp() {
		return pedal;
	}


	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#nextPatch()
	 */
	@Override
	public void nextPatch() {
		this.pedal.nextPatch();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#beforePatch()
	 */
	@Override
	public void beforePatch() {
		this.pedal.beforePatch();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#toPatch(int)
	 */
	@Override
	public void toPatch(int index) {
		this.pedal.toPatch(index);
	}


	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#toogleEffect(int)
	 */

	@Override
	public void toogleEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).toggle();	
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#hasActived(int)
	 */
	@Override
	public boolean hasActived(int idEffect) {
		return this.pedal.currentPatch().effects().get(idEffect).hasActived();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#activeEffect(int)
	 */
	@Override
	public void activeEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).active();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#disableEffect(int)
	 */
	@Override
	public void disableEffect(int idEffect) {
		this.pedal.currentPatch().effects().get(idEffect).disable();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#setEffectParam(int, int, int)
	 */
	@Override
	public void setEffectParam(int idEffect, int idParam, int value) {
		this.pedal.currentPatch().effects().get(idEffect).params().get(idParam).setValue(value);
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#getAmountEffects()
	 */
	@Override
	public int getAmountEffects() {
		return this.pedal.currentPatch().effects().size();
	}

	/* (non-Javadoc)
	 * @see br.com.srmourasilva.multistomp.controller.IPedalController#addListener(br.com.srmourasilva.domain.multistomp.OnMultistompListener)
	 */
	@Override
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
	
	public void send(Message message) {
		this.connection.send(Messages.For(message));
	}
	
	public void send(Messages messages) {
		this.connection.send(messages);
	}
}