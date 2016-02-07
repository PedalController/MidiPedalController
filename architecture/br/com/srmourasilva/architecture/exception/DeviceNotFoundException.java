package br.com.srmourasilva.architecture.exception;

@SuppressWarnings("serial")
public class DeviceNotFoundException extends Exception {
	public DeviceNotFoundException(String erro) {
		super(erro);
	}

	public DeviceNotFoundException(Exception e) {
		super(e);
	}
}
