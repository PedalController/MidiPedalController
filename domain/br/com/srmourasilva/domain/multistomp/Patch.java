package br.com.srmourasilva.domain.multistomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.srmourasilva.domain.OnMultistompListener;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Details;
import br.com.srmourasilva.domain.message.Messages.Message;

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
		
		Details details = new Details();
		details.effect = this.effects.size()-1;
		details.value = effect;

		onChange(Messages.Empty().add(CommonCause.EFFECT_TYPE, details));
	}

	public final void setEffect(int index, Effect effect) {
		this.effects.set(index, effect);
		effect.setListener(this);
		
		Details details = new Details();
		details.effect = index;
		details.value = effect;

		onChange(Messages.Empty().add(CommonCause.EFFECT_TYPE, details));	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

		Details details = new Details();
		details.patch = this.id;
		details.value = name;

		onChange(Messages.Empty().add(new Message(CommonCause.PATCH_NAME, details)));
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
			if (message.details().origin instanceof Effect)
				message.details().effect = this.effects.indexOf(message.details().origin);

			message.details().origin = this;
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