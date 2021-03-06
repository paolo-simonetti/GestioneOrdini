package it.solvingteam.gestioneordini.dao.utente;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Ruolo;
import it.solvingteam.gestioneordini.model.StatoUtente;
import it.solvingteam.gestioneordini.model.Utente;

public interface UtenteDAO extends IBaseDAO<Utente> {
	public Set<Utente> findByRuolo(Ruolo ruoloInstance) throws Exception;
	public Set<Utente> findByStatoUtente(StatoUtente statoUtente) throws Exception;
	public Utente findByUsername(String username) throws Exception;
	public Utente findByNomeAndCognomeAndDateCreated(String nome,String cognome,Date dateCreated) throws Exception;
}
