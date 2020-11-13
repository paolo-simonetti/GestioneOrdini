package it.solvingteam.gestioneordini.model;

import java.util.Set;
import java.util.TreeSet;

public class Categoria {
	private Long idCategoria;
	private String descrizioneCategoria;
	private Set<Articolo> articoliContenuti=new TreeSet<>();
	
	public Long getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public String getDescrizioneCategoria() {
		return descrizioneCategoria;
	}
	
	public void setDescrizioneCategoria(String descrizioneCategoria) {
		this.descrizioneCategoria = descrizioneCategoria;
	}
	
	public Set<Articolo> getArticoliContenuti() {
		return articoliContenuti;
	}
	
	public void setArticoliContenuti(Set<Articolo> articoliContenuti) {
		this.articoliContenuti = articoliContenuti;
	}

	public Categoria(String descrizioneCategoria) {
		this.descrizioneCategoria = descrizioneCategoria;
	}

	@Override
	public String toString() {
		return "Categoria [descrizioneCategoria=" + descrizioneCategoria + ", articoliContenuti=" + articoliContenuti
				+ "]";
	}
	
	public boolean equals(Object o) {
		if (o instanceof Categoria) {
			Categoria categoria=(Categoria) o;
			return (descrizioneCategoria==(categoria.getDescrizioneCategoria()));
		} else {
			return false;
		}
	}
	
	
}
