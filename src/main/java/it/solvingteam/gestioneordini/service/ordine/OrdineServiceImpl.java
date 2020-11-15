package it.solvingteam.gestioneordini.service.ordine;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;

public class OrdineServiceImpl implements OrdineService {

	@Override
	public Set<Ordine> elenca() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine caricaSingoloElemento(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiorna(Ordine TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserisciNuovo(Ordine TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuovi(Ordine TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Ordine> trovaTramiteStatoOrdine(StatoOrdine statoOrdine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine trovaTramiteIndirizzoSpedizioneEDataEffettuazione(String indirizzoSpedizione,
			Date dataEffettuazione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Ordine> trovaTramiteDestinatario(Utente destinatario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Ordine> trovaTramiteCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine trovaTramiteArticolo(Articolo articolo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inserisciOrdineInUtente(Ordine ordineInstance, Utente utenteInstance) {
		// TODO Auto-generated method stub
		return false;
	}

}
