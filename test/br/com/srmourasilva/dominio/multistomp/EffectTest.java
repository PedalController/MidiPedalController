package br.com.srmourasilva.dominio.multistomp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.srmourasilva.domain.multistomp.Effect;

public class EffectTest {
	
	@Test
	public void activeTest() {
		Effect effect = new Effect(0, "Chorus");

		assertEquals(false, effect.hasActived());

		effect.active();
		assertEquals(true, effect.hasActived());
		
		effect.active();
		assertEquals(true, effect.hasActived());
	}

	@Test
	public void disableTest() {
		Effect effect = new Effect(0, "Chorus");

		assertEquals(false, effect.hasActived());

		effect.disable();
		assertEquals(false, effect.hasActived());
		
		effect.active();
		assertEquals(true, effect.hasActived());
		
		effect.disable();
		assertEquals(false, effect.hasActived());
	}
	
	@Test
	public void toggleTest() {
		Effect effect = new Effect(0, "Chorus");

		assertEquals(false, effect.hasActived());

		effect.toggle();
		assertEquals(true, effect.hasActived());
		
		effect.toggle();
		assertEquals(false, effect.hasActived());
	}
}