package it.solvingteam.gestioneordini.service.articolo;

import java.util.Set;

import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public class ArticoloServiceImpl implements ArticoloService {

	@Override
	public Set<Articolo> elenca() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Articolo caricaSingoloElemento(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiorna(Articolo TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserisciNuovo(Articolo TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuovi(Articolo TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Articolo> trovaTramiteCategoriaDiAfferenza(Categoria categoriaDiAfferenza) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Articolo> trovaTramiteOrdineDiAcquisto(Ordine ordineDiAcquisto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Articolo trovaTramiteDescrizioneEPrezzoSingolo(String descrizione, Integer prezzoSingolo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inserisciArticoloInCategoria(Articolo articoloInstance, Categoria categoriaInstance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserisciArticoloInOrdine(Articolo articoloInstance, Ordine ordineInstance) {
		// TODO Auto-generated method stub
		return false;
	}

}
