package br.com.srmourasilva.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.SysexMessage;

import org.junit.Test;

public class MidiMessageUtilsTest {
	
	@Test
	public void isSmallTest() throws InvalidMidiDataException {
		byte[] bytesMessage = new byte[] {
			(byte) 0xf0, 0x52, 0x00
		};

		MidiMessage message = new SysexMessage(bytesMessage, bytesMessage.length);

		MidiMessageTester tester = new MidiMessageTester(message);

		byte[] small = new byte[] {
			(byte) 0x00, 0x01
		};
		
		byte[] sameSize = new byte[] {
			(byte) 0xff, 0x55, 0x55
		};
		
		byte[] bigger = new byte[] {
			(byte) 0xff, 0x55, 0x55, 0x55,
			(byte) 0x00, 0x01, 0x02, 0x03
		};

		assertFalse(tester.init().isLessThen(small).test());
		assertFalse(tester.init().isLessThen(sameSize).test());
		assertTrue(tester.init().isLessThen(bigger).test());
	}
	
	@Test
	public void sizeIsTest() throws InvalidMidiDataException {
		byte[] bytesMessage = new byte[] {
				(byte) 0xf0, 0x52, 0x00
		};

		MidiMessage message = new SysexMessage(bytesMessage, bytesMessage.length);

		MidiMessageTester tester = new MidiMessageTester(message);
		
		assertFalse(tester.init().sizeIs(2).test());
		assertTrue(tester.init().sizeIs(bytesMessage.length).test());
		assertFalse(tester.init().sizeIs(5).test());
	}
	
	@Test
	public void startingWithTest() throws InvalidMidiDataException {
		byte[] bytesMessage = new byte[] {
				(byte) 0xf0, 0x52, 0x00
		};

		MidiMessage message = new SysexMessage(bytesMessage, bytesMessage.length);

		MidiMessageTester tester = new MidiMessageTester(message);

		byte[] same = bytesMessage;
		byte[] big = Util.concat(bytesMessage, new byte[]{0x37});
		byte[] small = new byte[]{(byte) 0xf0, 0x52};
		byte[] diff = new byte[]{0x00, 0x52, 0x37};
		byte[] diff2 = new byte[]{(byte) 0xf0, 0x52, 0x37};

		assertTrue(tester.init().startingWith(same).test());
		assertFalse(tester.init().startingWith(big).test());
		assertTrue(tester.init().startingWith(small).test());
		assertFalse(tester.init().startingWith(diff).test());
		assertFalse(tester.init().startingWith(diff2).test());
	}
	
	@Test
	public void endingWithTest() throws InvalidMidiDataException {
		byte[] bytesMessage = new byte[] {
				(byte) 0xf0, 0x52, 0x00
		};

		MidiMessage message = new SysexMessage(bytesMessage, bytesMessage.length);

		MidiMessageTester tester = new MidiMessageTester(message);

		byte[] same = bytesMessage;
		byte[] big = Util.concat(bytesMessage, new byte[]{0x37});
		byte[] small = new byte[]{(byte) 0x52, 0x00};
		byte[] diff = new byte[]{0x00, 0x52};
		byte[] diff2 = new byte[]{(byte) 0xf1, 0x52, 0x37};

		assertTrue(tester.init().endingWith(same).test());
		assertFalse(tester.init().endingWith(big).test());
		assertTrue(tester.init().endingWith(small).test());
		assertFalse(tester.init().endingWith(diff).test());
		assertFalse(tester.init().endingWith(diff2).test());
	}
}
