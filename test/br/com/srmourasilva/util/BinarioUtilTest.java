package br.com.srmourasilva.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.srmourasilva.util.BinarioUtil;

public class BinarioUtilTest {

	@Test
	public void bytesParaBitsTest() {
		String bytes = "B0 B0 Ca";
		String bits = "10110000 10110000 11001010";

		boolean[] bitsEmBoolean = BinarioUtil.bytesParaBits(bytes);
		assertEquals(bits, BinarioUtil.toString(bitsEmBoolean));
	}
	
	@Test
	public void isbytesMensagemToBinaryStringZeroInicioTest() {
		String bytes = "04 20";
		String bits = "00000100 00100000";

		boolean[] bitsEmBoolean = BinarioUtil.bytesParaBits(bytes);
		assertEquals(bits, BinarioUtil.toString(bitsEmBoolean));
	}
}