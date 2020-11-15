package it.solvingteam.gestioneordini.dao.categoria;

import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	public Categoria findAllByDescrizione(String descrizione);
	public Set<Categoria> findAllByOrdine(Ordine ordine);
	public Integer SumPrezzoSingoloByCategoria(Categoria categoria);
	
}
