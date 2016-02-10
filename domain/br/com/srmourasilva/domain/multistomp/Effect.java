package br.com.srmourasilva.domain.multistomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.srmourasilva.domain.message.Cause;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;

public class Effect implements OnMultistompListener {

	private int midiId;
	private String name;
	private boolean state = false;

	private List<Param> params = new ArrayList<Param>();

	private Optional<OnMultistompListener> listener = Optional.empty();

	public Effect(int midiId, String name) {
		this.midiId = midiId;
		this.name = name;
	}

	/** Midi Id for send message */
	public int getMidiId() {
		return midiId;
	}

	public String getName() {
		return name;
	}

	public void active() {
		setState(true);
	}

	public void disable() {
		setState(false);
	}
	
	public void toggle() {
		if (hasActived())
			disable();
		else
			active();
	}

	private void setState(boolean state) {
		this.state = state;

		Cause cause = state ? CommonCause.EFFECT_ACTIVE : CommonCause.EFFECT_DISABLE;
		onChange(Messages.Empty().add(cause));
	}

	public boolean hasActived() {
		return state;
	}
	
	public void addParam(Param param) {
		this.params.add(param);
		param.setListener(this);
	}

	public List<Param> params() {
		return this.params;
	}

	/*************************************************/

	public void setListener(OnMultistompListener listener) {
		this.listener = Optional.of(listener);
	}

	@Override
	public void onChange(Messages messages) {
		if (!listener.isPresent())
			return;

		for (Message message : messages) {
			MultistompDetails details = (MultistompDetails) message.details();
			if (details.origin instanceof Param)
				details.param = this.params.indexOf(details.origin);

			details.origin = this;
		}

		listener.get().onChange(messages);
	}
	

	/*************************************************/

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName() + ": "+ midiId + " " + name + " - ");
		builder.append(state ? "Actived" : "Disabled");

		builder.append(" ( ");
		for (Param param : params)
			builder.append(param + " ");
		builder.append(")");

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}