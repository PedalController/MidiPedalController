package br.com.srmourasilva.multistomp.controller;

public enum PedalCompany {
	NULL(0, "Unknown Company"),
	ZoomCorp(1, "Zoom Corporation"),
	Line6(2, "Line 6"),
	Roland(3, "Roland Corporation");

	@SuppressWarnings("unused")
	private int id;
	private String name;

	private PedalCompany(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
}