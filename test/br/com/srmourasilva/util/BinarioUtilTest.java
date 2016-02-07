package br.com.srmourasilva.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.srmourasilva.util.BinaryUtil;

public class BinarioUtilTest {

	@Test
	public void bytesParaBitsTest() {
		String bytes = "B0 B0 Ca";
		String bits = "10110000 10110000 11001010";

		boolean[] bitsEmBoolean = BinaryUtil.bytesForBits(bytes);
		assertEquals(bits, BinaryUtil.toString(bitsEmBoolean));
	}
	
	@Test
	public void isbytesMensagemToBinaryStringZeroInicioTest() {
		String bytes = "04 20";
		String bits = "00000100 00100000";

		boolean[] bitsEmBoolean = BinaryUtil.bytesForBits(bytes);
		assertEquals(bits, BinaryUtil.toString(bitsEmBoolean));
	}
}