package it.solvingteam.gestioneordini.service.utente;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.model.Ruolo;
import it.solvingteam.gestioneordini.model.StatoUtente;
import it.solvingteam.gestioneordini.model.Utente;
import it.solvingteam.gestioneordini.service.IBaseService;

public interface UtenteService extends IBaseService<Utente> {
	public Set<Utente> trovaTramiteRuolo(Ruolo ruoloInstance);
	public Set<Utente> trovaTramiteStatoUtente(StatoUtente statoUtente);
	public Utente trovaTramiteUsername(String username);
	public Utente trovaNomeECognomeEDateCreated(String nome,String cognome,Date dateCreated);
	public boolean inserisciRuoloInUtente(Ruolo ruoloInstance, Utente utenteInstance);
}
