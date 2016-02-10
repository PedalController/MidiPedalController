package br.com.srmourasilva.domain.message;

public interface Message {
	public Cause cause();

	public Details details();

	public boolean is(Cause cause);
}
