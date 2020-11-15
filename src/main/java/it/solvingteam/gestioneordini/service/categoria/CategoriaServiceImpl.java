package it.solvingteam.gestioneordini.service.categoria;

import java.util.Set;

import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public class CategoriaServiceImpl implements CategoriaService {

	@Override
	public Set<Categoria> elenca() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria caricaSingoloElemento(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiorna(Categoria TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserisciNuovo(Categoria TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuovi(Categoria TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Categoria trovaTramiteDescrizione(String descrizione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Categoria> trovaTramiteOrdine(Ordine ordine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SommaPrezziSingoliDiCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

}
