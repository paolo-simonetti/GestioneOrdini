package it.solvingteam.gestioneordini.model;

import java.util.HashSet;
import java.util.Set;

public class Ordine implements Comparable<Ordine> {
	private Long idOrdine;
	private String nomeDestinatario, indirizzoSpedizione;
	private Set<Articolo> articoliOrdinati=new HashSet<>();
	public Long getIdOrdine() {
		return idOrdine;
	}

	public Ordine() {}
	
	public Ordine(String nomeDestinatario, String indirizzoSpedizione) {
		super();
		this.nomeDestinatario = nomeDestinatario;
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}
	
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}
	
	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	
	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}
	
	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}
	
	public Set<Articolo> getArticoliOrdinati() {
		return articoliOrdinati;
	}
	
	public void setArticoliOrdinati(Set<Articolo> articoliOrdinati) {
		this.articoliOrdinati = articoliOrdinati;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result=false;
		if (o instanceof Ordine) {
			Ordine ordine=(Ordine) o;
			boolean articoliCoincidono=true; 
			for (Articolo articolo:ordine.getArticoliOrdinati()) {
				articoliCoincidono=articoliCoincidono&&(articoliOrdinati.stream().filter(a->!a.equals(articolo)).
						findFirst().orElse(null)==null); // appena trovo un articolo di this non presente in ordine, questa variable diventa false 
				if (!articoliCoincidono) {
					return false; // se i due ordini differiscono per almeno un articolo, sono due ordini diversi
				}
			}
			// Se sono qui, vuol dire che gli articoli dei due ordini coincidono: quindi, confronto destinatari e indirizzi di spedizione
			result=nomeDestinatario.equals(ordine.getNomeDestinatario())&&indirizzoSpedizione.equals(ordine.getIndirizzoSpedizione());
		}
		return result;
	}

	@Override
	public String toString() {
		return ("Ordine[id="+idOrdine+",nomeDestinatario="+nomeDestinatario+",indirizzoSpedizione"+indirizzoSpedizione+"]");
	}

	@Override
	public int compareTo(Ordine ordine) {
		return nomeDestinatario.compareTo(ordine.getNomeDestinatario());
	}
	
}
