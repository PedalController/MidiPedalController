package br.com.srmourasilva.domain.multistomp;

import java.util.Optional;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;

public class Param {

	private String name;

	private int minValue;
	private int maxValue;
	private int currentValue;

	/** Pula de TANTO em TANTO */
	private int stepByStep = 1;

	private Optional<OnMultistompListener> listener = Optional.empty();

	public Param(String name, int minValue, int maxValue, int defaultValue, int stepByStep) {
		this.name = name;

		this.minValue = minValue;
		this.maxValue = maxValue;

		setCurrentValue(defaultValue);
		this.stepByStep = stepByStep;
	}
	
	private void setCurrentValue(int newValue) {
		if (!isValidValue(newValue)) {
			if (newValue > maxValue)
				newValue = maxValue;
			else
				newValue = minValue;
		}

		this.currentValue = newValue;
		
		MultistompDetails details = new MultistompDetails();
		details.value = currentValue;

		notify(CommonCause.PARAM_VALUE, details);
	}

	private boolean isValidValue(int value) {
		return !(value < minValue || value > maxValue);
	}

	private void notify(CommonCause cause, MultistompDetails details) {
		if (!listener.isPresent())
			return;
		details.origin = this;

		listener.get().onChange(Messages.For(new MultistompMessage(cause, details)));
	}

	public final String getName() {
		return name;
	}

	public final int getValue() {
		return currentValue;
	}

	public final void setValue(int value) {
		setCurrentValue(value);
	}

	public void addValue() {
		int newValue = currentValue + stepByStep;

		if (!isValidValue(newValue))
			// Don't change current value
			return;

		setValue(newValue);
	}
	
	public final int getMinValue() {
		return minValue;
	}
	public final int getMaxValue() {
		return maxValue;
	}

	/*************************************************/

	public void setListener(OnMultistompListener listener) {
		this.listener = Optional.of(listener);
	}

	/*************************************************/

	@Override
	public String toString() {
		return name + "=" + currentValue + "[" + minValue + "-" + maxValue + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj); // Same reference
	}
}