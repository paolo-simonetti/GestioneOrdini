package it.solvingteam.gestioneordini.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ordine")
public class Ordine implements Comparable<Ordine> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ordine")
	private Long idOrdine;

	@Column(name = "indirizzo_spedizione")
	private String indirizzoSpedizione;
	
	@Column(name="data_effettuazione") 
	private LocalDate dataEffettuazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_fk")
	private Utente destinatario;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ordineDiAcquisto")
	private Set<Articolo> articoliOrdinati=new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	private StatoOrdine statoOrdine=StatoOrdine.CREATO;

	public Long getIdOrdine() {
		return idOrdine;
	}

	public Ordine() {}
	
	public Ordine(String indirizzoSpedizione, LocalDate dataEffettuazione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
		this.dataEffettuazione=dataEffettuazione;
	}

	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}
	
	public Utente getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(Utente destinatario) {
		this.destinatario = destinatario;
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
	
	public StatoOrdine getStatoOrdine() {
		return statoOrdine;
	}

	public void setStatoOrdine(StatoOrdine statoOrdine) {
		this.statoOrdine = statoOrdine;
	}
	
	public boolean isCreato() {
		return (statoOrdine==StatoOrdine.CREATO);
	}

	public boolean isInConsegna() {
		return (statoOrdine==StatoOrdine.IN_CONSEGNA);
	}
	
	public boolean isConsegnato() {
		return (statoOrdine==StatoOrdine.CONSEGNATO);
	}
	
	public boolean isAnnullato() {
		return (statoOrdine==StatoOrdine.ANNULLATO);
	}
	
	public LocalDate getDataEffettuazione() {
		return dataEffettuazione;
	}

	public void setDataEffettuazione(LocalDate dataEffettuazione) {
		this.dataEffettuazione = dataEffettuazione;
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
			// Se sono qui, vuol dire che gli articoli dei due ordini coincidono: quindi, confronto destinatari, indirizzi di spedizione e stato
			result=destinatario.equals(ordine.getDestinatario())&&indirizzoSpedizione.equals(ordine.getIndirizzoSpedizione())&&
					dataEffettuazione.equals(ordine.getDataEffettuazione())&&statoOrdine.equals(ordine.getStatoOrdine());
		}
		return result;
	}


	@Override
	public String toString() {
		return "Ordine [idOrdine=" + idOrdine + ", indirizzoSpedizione=" + indirizzoSpedizione + ", dataEffettuazione="
				+ dataEffettuazione + ", destinatario=" + destinatario + ", articoli ordinati:" + articoliOrdinati.size()
				+ ", statoOrdine=" + statoOrdine + "]";
	}

	@Override
	public int compareTo(Ordine ordine) {
		return dataEffettuazione.compareTo(ordine.getDataEffettuazione());
	}
	
}
