package br.com.srmourasilva.domain.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.srmourasilva.domain.message.multistomp.MultistompMessage;

public class Messages implements Iterable<Message> {

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
		add(new MultistompMessage(cause));
		return this;
	}

	@Deprecated
	public Messages add(Cause cause, Details details) {
		add(new MultistompMessage(cause, details));
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