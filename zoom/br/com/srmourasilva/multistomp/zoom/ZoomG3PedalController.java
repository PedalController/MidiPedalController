package br.com.srmourasilva.multistomp.zoom;

import br.com.srmourasilva.architecture.exception.DeviceNotFoundException;
import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.domain.multistomp.OnMultistompListener;
import br.com.srmourasilva.multistomp.controller.PedalController;
import br.com.srmourasilva.multistomp.controller.PedalControllerFactory;
import br.com.srmourasilva.multistomp.zoom.gseries.ZoomGSeriesMessages;

/**
 * Zoom G3 pedal controller implementation for
 * use specific zoom characteristics
 */
public class ZoomG3PedalController implements PedalController {

	private PedalController controller;

	public ZoomG3PedalController() throws DeviceNotFoundException {
		this.controller = PedalControllerFactory.generateControllerFor(ZoomG3Type.class);
	}
	
	@Override
	public void on() throws DeviceUnavailableException {
		this.controller.on();
	}

	@Override
	public void off() {
		this.controller.off();
	}

	@Override
	public boolean hasStated() {
		return this.controller.hasStated();
	}

	@Override
	public Multistomp multistomp() {
		return this.controller.multistomp();
	}

	@Override
	public void nextPatch() {
		this.controller.nextPatch();
	}

	@Override
	public void beforePatch() {
		this.controller.beforePatch();
	}

	@Override
	public void toPatch(int index) {
		this.controller.toPatch(index);
	}

	@Override
	public void toogleEffect(int idEffect) {
		this.controller.toogleEffect(idEffect);
	}

	@Override
	public boolean hasActived(int idEffect) {
		return this.controller.hasActived(idEffect);
	}

	@Override
	public void activeEffect(int idEffect) {
		this.controller.activeEffect(idEffect);
	}

	@Override
	public void disableEffect(int idEffect) {
		this.controller.disableEffect(idEffect);
	}

	@Override
	public void setEffectParam(int idEffect, int idParam, int value) {
		this.controller.setEffectParam(idEffect, idParam, value);
	}

	@Override
	public int getAmountEffects() {
		return this.controller.getAmountEffects();
	}

	@Override
	public void addListener(OnMultistompListener listener) {
		this.controller.addListener(listener);
	}

	@Override
	public void send(Messages messages) {
		this.controller.send(messages);
	}
	
	@Override
	public void send(Message message) {
		this.controller.send(message);
	}

	@Override
	public void disableNotificationChangesToDevice() {
		this.controller.disableNotificationChangesToDevice();
	}

	@Override
	public void activeNotificationChangesToDevice() {
		this.controller.activeNotificationChangesToDevice();
	}
	
	public void tunerMuteOn() {
		this.controller.send(ZoomGSeriesMessages.TUNER_MUTE_ON());
	}
	
	public void tunerMuteOff() {
		this.controller.send(ZoomGSeriesMessages.TUNER_MUTE_OFF());
	}
	
	public void tunerBypassOn() {
		this.controller.send(ZoomGSeriesMessages.TUNER_BYPASS_ON());
	}
	
	public void tunerBypassOff() {
		this.controller.send(ZoomGSeriesMessages.TUNER_BYPASS_OFF());
	}
	
	public void setPatchTapTempo(int bpm) {
		//http://www.thegearpage.net/board/index.php?threads/midi-control-of-zoom-g3-video.1033719/page-3#post-19617949
		/*Tempo:
		F0 52 00 59 50 F7
		F0 52 00 59 31 06 08 02 01 F7
		-------------- ----- -----
		---- Tempo parameter Value (28 00-7A 01) (40-250)
		*/
		this.controller.send(ZoomGSeriesMessages.PATCH_TAP_TEMPO(bpm));
	}
}
