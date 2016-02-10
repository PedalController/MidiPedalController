package br.com.srmourasilva.domain.message.multistomp;

import br.com.srmourasilva.domain.message.Cause;
import br.com.srmourasilva.domain.message.Details;
import br.com.srmourasilva.domain.message.Message;

public class MultistompMessage implements Message {

	private Cause cause;
	private Details details;

	public MultistompMessage(Cause cause) {
		this(cause, new MultistompDetails());
	}

	public MultistompMessage(Cause cause, Details details) {
		this.cause = cause;
		this.details = details;
	}
	
	public Cause cause() {
		return cause;
	}

	public Details details() {
		return details;
	}

	public boolean is(Cause cause) {
		return this.cause == cause;
	}

	@Override
	public String toString() {
		StringBuilder retorno = new StringBuilder();

		retorno.append(cause + ": ");

		retorno.append("(");
		retorno.append(details);
		retorno.append(" )");

		return retorno.toString();
	}
}
