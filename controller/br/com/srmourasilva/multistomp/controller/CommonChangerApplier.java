package br.com.srmourasilva.multistomp.controller;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.multistomp.Effect;

public class CommonChangerApplier implements ChangeApplier {

	@Override
	public synchronized void attempt(Message message, PedalController controller) {
		if (!isCommonCaused(message))
			return;

		controller.disableNotificationChangesToDevice();
		
		MultistompDetails details = (MultistompDetails) message.details();

		if (message.is(CommonCause.TO_PATCH))
			controller.toPatch(details.patch);

		else if (this.isActiveEffectCurrentPatch(message))
			controller.activeEffect(details.effect);
		
		else if (this.isActiveEffectSpecificPatch(message))
			controller.multistomp().patchs().get(details.patch).effects().get(details.effect).active();

		else if (this.isDisableEffectCurrentPatch(message))
			controller.disableEffect(details.effect);

		else if (this.isDisableEffectSpecificPatch(message))
			controller.multistomp().patchs().get(details.patch).effects().get(details.effect).disable();

		else if (message.is(CommonCause.EFFECT_TYPE)) {
			int idEffect = details.effect;
			Effect effect = (Effect) details.value;

			controller.multistomp().currentPatch().setEffect(idEffect, effect);

		} else if (message.is(CommonCause.PARAM_VALUE)) {
			int idEffect = details.effect;
			int idParam  = details.param;
			int newValue = (int) details.value;

			controller.setEffectParam(idEffect, idParam, newValue);

		} else if (message.is(CommonCause.PATCH_NAME)) {
			String name = (String) details.value;
			controller.multistomp().currentPatch().setName(name);
		}

		controller.activeNotificationChangesToDevice();
	}
	

	private boolean isCommonCaused(Message message) {
		for (CommonCause cause : CommonCause.values())
			if (message.is(cause))
				return true;

		return false;
	}


	private boolean isActiveEffectCurrentPatch(Message message) {
		return message.is(CommonCause.EFFECT_ACTIVE) && ((MultistompDetails) message.details()).patch == MultistompDetails.NULL;
	}

	private boolean isDisableEffectCurrentPatch(Message message) {
		return message.is(CommonCause.EFFECT_DISABLE) && ((MultistompDetails) message.details()).patch == MultistompDetails.NULL;
	}

	private boolean isActiveEffectSpecificPatch(Message message) {
		return message.is(CommonCause.EFFECT_ACTIVE) && ((MultistompDetails) message.details()).patch != MultistompDetails.NULL;
	}

	private boolean isDisableEffectSpecificPatch(Message message) {
		return message.is(CommonCause.EFFECT_ACTIVE) && ((MultistompDetails) message.details()).patch != MultistompDetails.NULL;
	}
}