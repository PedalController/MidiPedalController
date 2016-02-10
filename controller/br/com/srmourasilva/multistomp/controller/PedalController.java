package br.com.srmourasilva.multistomp.controller;

import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.OnMultistompListener;

public interface PedalController {

	/** Turn on and inicialize the pedal
	 */
	void on() throws DeviceUnavailableException;

	/** Close connection and turn off the pedal
	 */
	void off();

	/*************************************************/

	boolean hasStated();

	Multistomp multistomp();

	/*************************************************/

	void nextPatch();

	void beforePatch();

	void toPatch(int index);

	/*************************************************/

	void toogleEffect(int idEffect);

	boolean hasActived(int idEffect);

	void activeEffect(int idEffect);

	void disableEffect(int idEffect);

	void setEffectParam(int idEffect, int idParam, int value);

	/** @return Amount of effects that the current patch has
	 */
	int getAmountEffects();

	/*************************************************/

	void addListener(OnMultistompListener listener);

	void send(Messages messages);

	void send(Message message);

	void disableNotificationChangesToDevice();

	void activeNotificationChangesToDevice();
}