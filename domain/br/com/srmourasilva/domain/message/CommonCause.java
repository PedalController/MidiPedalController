package br.com.srmourasilva.domain.message;


public enum CommonCause implements Cause {

	// Multistomp
	/**
	 * To patch causes the patch change
	 */
	TO_PATCH,

	// Patch
	/**
	 * Patch number informs a patch number that has a midi parsed
	 */
	PATCH_NUMBER,
	PATCH_VOLUME,
	PATCH_NAME,

	// Effect
	EFFECT_ACTIVE,
	EFFECT_DISABLE,
	EFFECT_TYPE,

	// Param
	PARAM_VALUE;
}