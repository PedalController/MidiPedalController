package br.com.srmourasilva.domain.message;

import java.util.Iterator;

@Deprecated
class Mensagem implements Iterable<Mensagem> {

	public static class Details {
		public final static int NULL = -1;

		public int patch  = NULL;
		public int effect = NULL;
		public int param  = NULL;
		public int value  = NULL;
		
		@Override
		public String toString() {
			String retorno = "";
			if (patch != NULL)
				retorno += " patch=" + patch;
			if (effect != NULL)
				retorno += " effect=" + effect;
			if (param != NULL)
				retorno += " param=" + param;
			if (value != NULL)
				retorno += " value=" + value;

			return retorno;
		}
	}

	private Cause cause;
	private Details details;

	private Mensagem next = null;

	public Mensagem(Cause cause) {
		this(cause, new Details());
	}

	public Mensagem(Cause cause, Details details) {
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

	public void add(Mensagem message) {
		last().next = message;
	}

	private Mensagem last() {
		Mensagem pointer = this;
		
		while (pointer.next != null)
			pointer = pointer.next;
		return pointer;
	}

	@Override
	public Iterator<Mensagem> iterator() {
		return new MessageIterator(this);
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

	private class MessageIterator implements Iterator<Mensagem> {
		
		private Mensagem pointer;

		public MessageIterator(Mensagem root) {
			this.pointer = root;
		}

		@Override
		public boolean hasNext() {
			return pointer.next != null;
		}

		@Override
		public Mensagem next() {
			Mensagem returned = pointer.next;
			pointer = pointer.next;

			return returned;
		}
	}
}