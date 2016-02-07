package br.com.srmourasilva.architecture.exception;

@SuppressWarnings("serial")
public class ImplemetationException extends RuntimeException {
	public ImplemetationException(String erro) {
		super(erro);
	}
}