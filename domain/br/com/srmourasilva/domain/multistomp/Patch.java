package br.com.srmourasilva.domain.multistomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;

public class Patch implements OnMultistompListener {
	private int id;
	private String name = "";
	private List<Effect> effects = new ArrayList<Effect>();

	private Optional<OnMultistompListener> listener = Optional.empty();

	public Patch(int id) {
		this.id = id;
	}

	public final int getId() {
		return id;
	}

	public final List<Effect> effects() {
		return effects;
	}

	public final void addEffect(Effect effect) {
		this.effects.add(effect);
		effect.setListener(this);
		
		MultistompDetails details = new MultistompDetails();
		details.effect = this.effects.size()-1;
		details.value = effect;

		onChange(CommonCause.EFFECT_TYPE, details);
	}

	public final void setEffect(int index, Effect effect) {
		this.effects.set(index, effect);
		effect.setListener(this);
		
		MultistompDetails details = new MultistompDetails();
		details.effect = index;
		details.value = effect;

		onChange(CommonCause.EFFECT_TYPE, details);	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

		MultistompDetails details = new MultistompDetails();
		details.patch = this.id;
		details.value = name;

		onChange(CommonCause.PATCH_NAME, details);
	}

	/*************************************************/

	public void setListener(OnMultistompListener listener) {
		this.listener = Optional.of(listener);
	}

	private void onChange(CommonCause cause, MultistompDetails details) {
		this.onChange(Messages.For(new MultistompMessage(cause, details)));
	}
	
	@Override
	public void onChange(Messages messages) {
		if (!listener.isPresent())
			return;

		for (Message message : messages) {
			MultistompDetails details = (MultistompDetails) message.details();
			if (details.origin instanceof Effect)
				details.effect = this.effects.indexOf(details.origin);

			details.origin = this;
		}

		listener.get().onChange(messages);
	}

	/*************************************************/

	@Override
	public final String toString() {
		StringBuffer retorno = new StringBuffer();
		retorno.append("Patch ");
		retorno.append(id);
		retorno.append(" - ");
		retorno.append(name);
		retorno.append(" (" + effects.size() + " Effect(s))");

		return retorno.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj); // Same reference
	}
}