package br.com.srmourasilva.domain.multistomp;

import java.util.ArrayList;
import java.util.List;

import br.com.srmourasilva.architecture.exception.ImplemetationException;
import br.com.srmourasilva.domain.message.CommonCause;
import br.com.srmourasilva.domain.message.Message;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.multistomp.MultistompDetails;
import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;

public abstract class Multistomp implements OnMultistompListener {

	private List<OnMultistompListener> listeners = new ArrayList<>();

	private List<Patch> patchs = new ArrayList<Patch>();

	private int idCurrentPatch = 0;

	protected void addPatch(Patch patch) {
		patchs.add(patch);
		patch.setListener(this);
	}

	public Patch currentPatch() {
		try {
			return patchs.get(idCurrentPatch);
		} catch (IndexOutOfBoundsException e) {
			throw new ImplemetationException("This multistomp havent any Patch. \nAdd the Patchs in the Pedal Construtor: " + this.getClass().getCanonicalName());
		}
	}

	public int getIdCurrentPatch() {
		return idCurrentPatch;
	}

	public void nextPatch() {
		this.toPatch(idCurrentPatch+1);
	}

	public void beforePatch() {
		this.toPatch(idCurrentPatch-1);
	}

	public void toPatch(int index) {
		if (index >= patchs.size())
			index = 0;

		else if (index < 0)
			index = patchs.size()-1;

		idCurrentPatch = index;
		
		MultistompDetails details = new MultistompDetails();
		details.patch = idCurrentPatch;

		onChange(Messages.For(new MultistompMessage(CommonCause.TO_PATCH, details)));		
	}

	public List<Patch> patchs() {
		return patchs;
	}
	/*************************************************/

	@Override
	public final String toString() {
		StringBuffer retorno = new StringBuffer();
		retorno.append("Multistomp: "  + this.getClass().getSimpleName() + "\n");
		retorno.append(" - Current Patch: " + this.currentPatch().toString() + "\n");
		retorno.append(" - Effects: \n");

		for (Effect effect : this.currentPatch().effects())
			retorno.append("  |- " + effect.toString() + "\n");

		return retorno.toString();
	}


	/*************************************************/

	public void addListener(OnMultistompListener listener) {
		this.listeners.add(listener);
	}
	
	public List<OnMultistompListener> listeners() {
		return this.listeners;
	}

	@Override
	public void onChange(Messages messages) {
		for (Message message : messages) {
			MultistompDetails details = (MultistompDetails) message.details();
			if (details.origin instanceof Patch)
				details.patch = this.patchs.indexOf(details.origin);

			details.origin = this;
		}

		listeners.forEach(listener -> listener.onChange(messages));
	}

	/*************************************************/

	public abstract Messages start();
}