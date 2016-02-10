package br.com.srmourasilva.domain.message.multistomp;

import br.com.srmourasilva.domain.message.Details;

public class MultistompDetails implements Details {
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
