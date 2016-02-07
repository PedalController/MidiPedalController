package br.com.srmourasilva.domain;

import br.com.srmourasilva.multistomp.controller.PedalCompany;

public enum PedalType {
	Null(0, "Unknown Pedal", PedalCompany.NULL, "Pedal Unknown is unimplemented"),
	G2Nu(1, "Zoom G2Nu",     PedalCompany.ZoomCorp, "G2Nu/G2.1Nu"),
	G3  (2, "Zoom G3v2.0",   PedalCompany.ZoomCorp, "Series");

    private int id;
    private String name;
    private PedalCompany company;
	private String USBName;

    private PedalType(int id, String name, PedalCompany company, String USBName) {
    	this.id = id;
    	this.name = name;
    	this.company = company;
    	this.USBName = USBName;
    }

    public int getId() {
    	return id;
    }

	public PedalCompany getCompany() {
		return company;
	}

	public String toString() {
		return this.name + " - " + this.company.toString();
	}

	/** 
	 * @return USBName Device
	 * 
	 * The name will be used to find out which is the USB which is connected to the PC
	 * that is corresponding Pedal
	 */
	public String getUSBName() {
		return USBName;
	}
}