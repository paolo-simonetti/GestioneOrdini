package it.solvingteam.gestioneordini.service.categoria;

import java.util.Set;

import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.service.IBaseService;

public interface CategoriaService extends IBaseService<Categoria> {
	public Categoria trovaTramiteDescrizione(String descrizione);
	public Set<Categoria> trovaTramiteOrdine(Ordine ordine);
	public Integer SommaPrezziSingoliDiCategoria(Categoria categoria);
}
