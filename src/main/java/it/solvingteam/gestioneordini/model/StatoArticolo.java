package it.solvingteam.gestioneordini.model;

import java.util.TreeMap;

public enum StatoArticolo {
	DISPONIBILE("disponibile"),
	ESAURITO("esaurito");
	
	private String statoArticolo;
	
	StatoArticolo(String statoArticolo) {
		this.statoArticolo=statoArticolo;
	}
	
	@Override
	public String toString() {
		return statoArticolo;
	}
	
	static {
		TreeMap<String,StatoArticolo> conversioneStatoArticolo=new TreeMap<>();
		conversioneStatoArticolo.put("disponibile",DISPONIBILE);
		conversioneStatoArticolo.put("esaurito",ESAURITO);
	}
}
