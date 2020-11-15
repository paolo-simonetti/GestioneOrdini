package it.solvingteam.gestioneordini.dao.utente;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.model.Ruolo;
import it.solvingteam.gestioneordini.model.StatoUtente;
import it.solvingteam.gestioneordini.model.Utente;

public class UtenteDAOImpl implements UtenteDAO {

	@Override
	public Set<Utente> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente get(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Utente o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean insert(Utente o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Utente o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Utente> findByRuolo(Ruolo ruolo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Utente> findByStatoUtente(StatoUtente statoUtente) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente findByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utente findByNomeAndCognomeAndDateCreated(String nome, String cognome, Date dateCreated) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
