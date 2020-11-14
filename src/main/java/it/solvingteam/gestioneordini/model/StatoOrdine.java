package it.solvingteam.gestioneordini.model;

import java.util.TreeMap;

public enum StatoOrdine {
	IN_CONSEGNA("inConsegna"),
	CONSEGNATO("consegnato");
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
	}
	
}
