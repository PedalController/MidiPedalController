package br.com.srmourasilva.architecture.exception;

@SuppressWarnings("serial")
public class DeviceUnavailableException extends Exception {
	public DeviceUnavailableException(Exception e) {
		super(e);
	}
}
