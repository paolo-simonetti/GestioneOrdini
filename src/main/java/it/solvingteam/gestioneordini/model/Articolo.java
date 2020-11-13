package it.solvingteam.gestioneordini.model;

import java.util.HashSet;
import java.util.Set;

public class Articolo {
	private Long idArticolo;
	private String descrizione;
	private Integer prezzoSingolo;
	private Ordine ordineDiAcquisto;
	private Set<Categoria> categorieDiAfferenza=new HashSet<>();
	
	public Long getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(Long idArticolo) {
		this.idArticolo = idArticolo;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Integer getPrezzoSingolo() {
		return prezzoSingolo;
	}
	
	public void setPrezzoSingolo(Integer prezzoSingolo) {
		this.prezzoSingolo = prezzoSingolo;
	}
	
	public Ordine getOrdineDiAcquisto() {
		return ordineDiAcquisto;
	}
	
	public void setOrdineDiAcquisto(Ordine ordineDiAcquisto) {
		this.ordineDiAcquisto = ordineDiAcquisto;
	}
	
	public Set<Categoria> getCategorieDiAfferenza() {
		return categorieDiAfferenza;
	}
	
	public void setCategorieDiAfferenza(Set<Categoria> categorieDiAfferenza) {
		this.categorieDiAfferenza = categorieDiAfferenza;
	}
	
	public Articolo(String descrizione, Integer prezzoSingolo, Ordine ordineDiAcquisto) {
		this.descrizione = descrizione;
		this.prezzoSingolo = prezzoSingolo;
		this.ordineDiAcquisto = ordineDiAcquisto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((ordineDiAcquisto == null) ? 0 : ordineDiAcquisto.hashCode());
		result = prime * result + ((prezzoSingolo == null) ? 0 : prezzoSingolo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (o instanceof Articolo) {
			Articolo articolo=(Articolo) o;
			return (descrizione==articolo.getDescrizione())&&(prezzoSingolo==articolo.getPrezzoSingolo());
		} else {
			return false;
		}
	}
	
	
}
