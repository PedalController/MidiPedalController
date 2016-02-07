package br.com.srmourasilva.domain.message;


public enum CommonCause implements Cause {

	// Multistomp
	TO_PATCH,
	GENERAL_VOLUME,

	// Patch
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