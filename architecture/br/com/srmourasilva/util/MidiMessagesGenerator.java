package br.com.srmourasilva.util;

import br.com.srmourasilva.domain.message.Cause;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.domain.message.Messages.Message;
import br.com.srmourasilva.domain.message.MidiMessages;

public class MidiMessagesGenerator {
	public interface Generator {
		void generate(MidiMessageGenerator method);
	}
	
	public static interface MidiMessageGenerator {
		MidiMessages generate(Message message);
	}

	private Messages messages;
	private MidiMessages messagesGenerated;
	
	public MidiMessagesGenerator(Messages messages) {
		this.messages = messages;
		this.messagesGenerated = new MidiMessages();
	}

	public Generator forEachOfType(Cause cause) { 
		return generator -> {
			MidiMessages messages = this.generateMessagesBy(generator, cause);
		    this.messagesGenerated.concatWith(messages);
        };
	}

	private MidiMessages generateMessagesBy(MidiMessageGenerator generator, Cause cause) {
		MidiMessages messages = new MidiMessages();
		Messages messagesOfCause = this.messages.getBy(cause);

		for (Message message : messagesOfCause)
			messages.concatWith(generator.generate(message));

		return messages;
	}

	public MidiMessages generateMidiMessages() {
		return messagesGenerated;
	}
}
