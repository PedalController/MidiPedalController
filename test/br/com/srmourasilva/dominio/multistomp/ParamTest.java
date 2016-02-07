package br.com.srmourasilva.dominio.multistomp;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.srmourasilva.domain.multistomp.Param;

public class ParamTest {
	
	@Test
	public void defaultValueTest() {
		final int DEFAULT_VALUE_1 = 0;
		final int DEFAULT_VALUE_2 = 5;
		final int DEFAULT_VALUE_3 = 10;

		Param param1 = new Param("Volume", 0, 10, DEFAULT_VALUE_1, 1);
		Param param2 = new Param("Volume", 0, 10, DEFAULT_VALUE_2, 1);
		Param param3 = new Param("Volume", 0, 10, DEFAULT_VALUE_3, 1);
		
		assertEquals(DEFAULT_VALUE_1, param1.getValue());
		assertEquals(DEFAULT_VALUE_2, param2.getValue());
		assertEquals(DEFAULT_VALUE_3, param3.getValue());
	}
	
	@Test
	public void invalidDefaultValueMinusTest() {
		final int DEFAULT_VALUE_1 = -1;
		final int MINUS_VALUE = 0;

		Param param1 = new Param("Volume", MINUS_VALUE, 10, DEFAULT_VALUE_1, 1);
		
		assertEquals(MINUS_VALUE, param1.getValue());
	}
	
	@Test
	public void invalidDefaultValueTestPlus() {
		final int DEFAULT_VALUE_2 = 11;
		final int MAX_VALUE = 10;

		Param param2 = new Param("Volume", 0, MAX_VALUE, DEFAULT_VALUE_2, 1);
		
		assertEquals(MAX_VALUE, param2.getValue());
	}

	@Test
	public void addValueTest() {
		final int STEP_BY_STEP = 1;
		Param param = new Param("Volume", 0, 5, 0, STEP_BY_STEP);

		param.addValue();
		assertEquals(1, param.getValue());
		
		param.addValue();
		assertEquals(2, param.getValue());
		
		param.addValue();
		assertEquals(3, param.getValue());
	}
	
	@Test
	public void addValueDontPassMaxTest() {
		final int STEP_BY_STEP = 1;
		Param param = new Param("Volume", 0, 3, 0, STEP_BY_STEP);

		param.addValue();
		assertEquals(1, param.getValue());
		
		param.addValue();
		assertEquals(2, param.getValue());
		
		param.addValue();
		assertEquals(3, param.getValue());
		
		param.addValue();
		assertEquals(3, param.getValue());
	}
	
	@Test
	public void addValueDontPassMaxStepTest() {
		final int STEP_BY_STEP = 2;
		Param param = new Param("Volume", 0, 3, 0, STEP_BY_STEP);

		assertEquals(0, param.getValue());

		param.addValue();
		assertEquals(2, param.getValue());
		
		param.addValue();
		assertEquals(2, param.getValue());
	}
	
	/**
	 * Set value does not depend of STEP_BY_STEP
	 */
	@Test
	public void setValueTest() {
		final int STEP_BY_STEP = 2;
		Param param = new Param("Volume", 0, 10, 0, STEP_BY_STEP);

		param.setValue(5);
		assertEquals(5, param.getValue());

		param.setValue(8);
		assertEquals(8, param.getValue());
		
		param.setValue(2);
		assertEquals(2, param.getValue());
		
		param.setValue(0);
		assertEquals(0, param.getValue());
		
		param.setValue(10);
		assertEquals(10, param.getValue());
	}
	
	@Test
	public void setInvalidValueMinusTest() {
		final int STEP_BY_STEP = 2;
		final int MINUS_VALUE = 0;
		Param param = new Param("Volume", MINUS_VALUE, 10, 0, STEP_BY_STEP);

		param.setValue(-1);
		
		assertEquals(MINUS_VALUE, param.getValue());
	}
	
	@Test
	public void setInvalidValuePlusTest() {
		final int STEP_BY_STEP = 2;
		final int PLUS_VALUE = 10;
		Param param = new Param("Volume", 0, PLUS_VALUE, 0, STEP_BY_STEP);

		param.setValue(11);
		
		assertEquals(PLUS_VALUE, param.getValue());		
	}
}