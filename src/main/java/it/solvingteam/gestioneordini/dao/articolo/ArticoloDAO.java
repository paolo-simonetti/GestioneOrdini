package it.solvingteam.gestioneordini.dao.articolo;

import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Set<Articolo> findAllByCategoriaDiAfferenza(Categoria categoriaDiAfferenza);
	public Set<Articolo> findAllByOrdineDiAcquisto(Ordine ordineDiAcquisto);
	public Articolo findByDescrizioneAndPrezzoSingolo(String descrizione, Integer prezzoSingolo);
	
}
