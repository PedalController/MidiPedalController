package br.com.srmourasilva.domain.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Messages implements Iterable<Messages.Message> {
	
	public static class Details {
		public final static int NULL = -1;

		public int patch  = NULL;
		public int effect = NULL;
		public int param  = NULL;
		public Object value    = null;
		public Object origin   = null;
		
		@Override
		public String toString() {
			String retorno = "";
			if (patch != NULL)
				retorno += " patch=" + patch;
			if (effect != NULL)
				retorno += " effect=" + effect;
			if (param != NULL)
				retorno += " param=" + param;
			if (value != null)
				retorno += " value=" + value;

			return retorno;
		}
	}

	public static class Message {
		private Cause cause;
		private Details details;

		public Message(Cause cause) {
			this(cause, new Details());
		}

		public Message(Cause cause, Details details) {
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

	List<Message> messages = new ArrayList<>();

	public static Messages Empty() {
		return new Messages();
	}

	public static Messages For(Message ... messages) {
		Messages returned = new Messages();

		for (Message message : messages)
			returned.add(message);
		
		return returned;
	}

	private Messages() {}

	public Messages add(Cause cause) {
		add(cause, new Details());
		return this;
	}

	public Messages add(Cause cause, Details details) {
		add(new Message(cause, details));
		return this;
	}

	public Messages add(Message message) {
		messages.add(message);
		return this;
	}

	public Messages concatWith(Messages messages) {
		messages.forEach(message -> this.add(message));

		return this;
	}

	@Override
	public Iterator<Message> iterator() {
		return messages.iterator();
	}

	public Messages getBy(Cause cause) {
		Messages returned = new Messages();
		
		for (Message message : this)
			if (message.is(cause))
				returned.add(message);

		return returned;
	}

	@Override
	public String toString() {
		StringBuilder returned = new StringBuilder();

		for (Message message : messages)
			returned.append(message.toString());

		return returned.toString();
	}
}