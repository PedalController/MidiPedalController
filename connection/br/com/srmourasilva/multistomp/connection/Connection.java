package br.com.srmourasilva.multistomp.connection;

import br.com.srmourasilva.architecture.exception.DeviceUnavailableException;
import br.com.srmourasilva.domain.message.Messages;
import br.com.srmourasilva.multistomp.connection.codification.MessageDecoder;
import br.com.srmourasilva.multistomp.connection.codification.MessageEncoder;
import br.com.srmourasilva.multistomp.connection.midi.MidiConnection.OnUpdateListener;

public interface Connection {
	void start() throws DeviceUnavailableException;
	void stop();
	void send(Messages messages);
	
	void setEncoder(MessageEncoder encoder);
	void setDecoder(MessageDecoder decoder);

	void setListener(OnUpdateListener listener);
}
