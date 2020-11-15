package it.solvingteam.gestioneordini.model;

import java.util.TreeMap;

public enum StatoOrdine {
	CREATO("creato"),
	IN_CONSEGNA("inConsegna"),
	CONSEGNATO("consegnato"),
	ANNULLATO("annullato");
	private String statoOrdine;
	
	
	StatoOrdine(String statoOrdine) {
		this.statoOrdine=statoOrdine;
	}
	
	@Override
	public String toString() {
		return statoOrdine;
	} 
	
	static {
		TreeMap <String, StatoOrdine> conversioneStatoOrdine=new TreeMap<>();
		conversioneStatoOrdine.put("inConsegna",IN_CONSEGNA);
		conversioneStatoOrdine.put("consegnato",CONSEGNATO);
		conversioneStatoOrdine.put("annullato",ANNULLATO);
		conversioneStatoOrdine.put("creato",CREATO);
	}
	
}
