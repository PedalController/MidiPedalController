package br.com.srmourasilva.util;

public class BinarioUtil {
	/**
	 * http://stackoverflow.com/a/3076308/1524997
	 * @return 
	 */
	public static boolean[] bytesParaBits(String bytes) {
		String[] bytesSeparados = bytes.split(" ");

		int numOfBits = 8;
		boolean[] retorno = new boolean[numOfBits * bytesSeparados.length];

		int i=0;
		for (String byt : bytesSeparados) {
			for (int j=0; j<numOfBits; j++) {
				int value = Integer.parseInt(byt, 16);

		        retorno[i] = (value & 1 << j) != 0;
		        i++;
			}
		}

		return retorno;
	}
	
	public static String toString(boolean[] mensagem) {
		StringBuilder builder = new StringBuilder();

		for (int numPalavra = 0; numPalavra < mensagem.length/8; numPalavra++) {
			StringBuilder byteIndividual = new StringBuilder();

			for (int idLetra = numPalavra*8; idLetra < (numPalavra+1)*8; idLetra++) {
				boolean b = mensagem[idLetra];
				byteIndividual.append(b ? '1' : '0');
			}

			builder.append(byteIndividual.reverse());
			builder.append(' ');
		}

		// Remover o espaço sobrando do fim
		builder.deleteCharAt(builder.length()-1);

		return builder.toString();
	}

	public static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for (byte b: a)
			sb.append(String.format("%02x ", b & 0xff));
		return sb.toString();
	}
}