package it.solvingteam.gestioneordini.service.utente;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.model.Ruolo;
import it.solvingteam.gestioneordini.model.StatoUtente;
import it.solvingteam.gestioneordini.model.Utente;

public class UtenteServiceImpl implements UtenteService {

	@Override
	public Set<Utente> elenca() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente caricaSingoloElemento(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean aggiorna(Utente TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserisciNuovo(Utente TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rimuovi(Utente TInstance) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Utente> trovaTramiteRuolo(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Utente> trovaTramiteStatoUtente(StatoUtente statoUtente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente trovaTramiteUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente trovaNomeECognomeEDateCreated(String nome, String cognome, Date dateCreated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inserisciRuoloInUtente(Ruolo ruoloInstance, Utente utenteInstance) {
		// TODO Auto-generated method stub
		return false;
	}

}
