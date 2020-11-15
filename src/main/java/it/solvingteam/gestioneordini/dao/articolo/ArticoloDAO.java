package it.solvingteam.gestioneordini.dao.articolo;

import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Set<Articolo> findAllByCategoriaDiAfferenza(Categoria categoriaDiAfferenza) throws Exception;
	public Set<Articolo> findAllByOrdineDiAcquisto(Ordine ordineDiAcquisto) throws Exception;
	public Articolo findByDescrizioneAndPrezzoSingolo(String descrizione, Integer prezzoSingolo) throws Exception;
	
}
