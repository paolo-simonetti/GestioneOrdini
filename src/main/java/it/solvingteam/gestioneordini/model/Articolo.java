package it.solvingteam.gestioneordini.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="articolo")
public class Articolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_articolo")
	private Long idArticolo;
	
	@Column(name = "descrizione")
	private String descrizione;
	
	@Column(name = "prezzo_singolo")
	private Integer prezzoSingolo;
	
	@Enumerated(EnumType.STRING)
	private StatoArticolo statoArticolo=StatoArticolo.DISPONIBILE;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ordine_fk")
	private Ordine ordineDiAcquisto;
	
	@ManyToMany
	@JoinTable(name = "articolo_categoria", joinColumns = @JoinColumn(name = "articolo_id", referencedColumnName = "id_articolo"), inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria"))
	private Set<Categoria> categorieDiAfferenza=new HashSet<>(0);
	
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
	
	public StatoArticolo getStatoArticolo() {
		return statoArticolo;
	}

	public void setStatoArticolo(StatoArticolo statoArticolo) {
		this.statoArticolo = statoArticolo;
	}

	public boolean isDisponibile() {
		return (statoArticolo==StatoArticolo.DISPONIBILE);
	}
	
	public boolean isEsaurito() {
		return (statoArticolo==StatoArticolo.ESAURITO);
	}

	
	public Articolo(String descrizione, Integer prezzoSingolo) {
		this.descrizione = descrizione;
		this.prezzoSingolo = prezzoSingolo;
	}
	
	public Articolo() {}

	@Override
	public boolean equals(Object o) {
		if (o==null) {
			return false;
		}
		if (o instanceof Articolo) {
			Articolo articolo=(Articolo) o;
			return (descrizione.equals(articolo.getDescrizione())&&prezzoSingolo==articolo.getPrezzoSingolo()&&
					statoArticolo.equals(articolo.getStatoArticolo()));
		} else {
			return false;
		}
	}
	
	
}
