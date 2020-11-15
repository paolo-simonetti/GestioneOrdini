package it.solvingteam.gestioneordini.dao.categoria;

import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	public Categoria findByDescrizione(String descrizione) throws Exception;
	public Set<Categoria> findAllByOrdine(Ordine ordineInstance) throws Exception;
	public Integer SumPrezzoSingoloByCategoria(Categoria categoriaInstance) throws Exception;
	
}
