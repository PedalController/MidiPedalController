package br.com.srmourasilva.dominio.multistomp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.srmourasilva.domain.multistomp.Multistomp;
import br.com.srmourasilva.multistomp.simulator.MultistompSimulator;

public class MultistompTest {
	@Test
	public void nextPatchTest() {
		Multistomp pedaleira = new MultistompSimulator(5);

		assertEquals(0, pedaleira.getIdCurrentPatch());
		pedaleira.nextPatch();
		
		assertEquals(1, pedaleira.getIdCurrentPatch());
	}
	
	@Test
	public void nextPatchLimitTest() {
		Multistomp pedaleira = new MultistompSimulator(5);

		assertEquals(0, pedaleira.getIdCurrentPatch());

		pedaleira.toPatch(4);		
		assertEquals(4, pedaleira.getIdCurrentPatch());
		
		pedaleira.nextPatch();
		assertEquals(0, pedaleira.getIdCurrentPatch());
	}
	
	@Test
	public void toPatchTest() {
		Multistomp pedaleira = new MultistompSimulator(5);

		assertEquals(0, pedaleira.getIdCurrentPatch());

		pedaleira.toPatch(3);		
		assertEquals(3, pedaleira.getIdCurrentPatch());
	}
	
	@Test
	public void toPatchLimitsTest() {
		Multistomp pedaleira = new MultistompSimulator(5);

		assertEquals(0, pedaleira.getIdCurrentPatch());

		pedaleira.toPatch(-1);
		assertEquals(4, pedaleira.getIdCurrentPatch());
		
		pedaleira.toPatch(-50);
		assertEquals(4, pedaleira.getIdCurrentPatch());
		
		pedaleira.toPatch(5);
		assertEquals(0, pedaleira.getIdCurrentPatch());
		
		pedaleira.toPatch(50);
		assertEquals(0, pedaleira.getIdCurrentPatch());
	}
	
	@Test
	public void beforePatchTest() {
		Multistomp pedaleira = new MultistompSimulator(5);

		assertEquals(0, pedaleira.getIdCurrentPatch());

		pedaleira.nextPatch();
		assertEquals(1, pedaleira.getIdCurrentPatch());
		
		pedaleira.beforePatch();
		assertEquals(0, pedaleira.getIdCurrentPatch());
		
		pedaleira.beforePatch();
		assertEquals(4, pedaleira.getIdCurrentPatch());
	}

	@Test
	public void beforePatchLimitTest() {
		Multistomp pedaleira = new MultistompSimulator(5);
		
		assertEquals(0, pedaleira.getIdCurrentPatch());

		pedaleira.beforePatch();
		assertEquals(4, pedaleira.getIdCurrentPatch());
	}
}
