package it.solvingteam.gestioneordini.dao.ordine;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;

public class OrdineDAOImpl implements OrdineDAO {

	@Override
	public Set<Ordine> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine get(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Ordine o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Ordine o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Ordine o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Ordine> findAllByStatoOrdine(StatoOrdine statoOrdine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine findByIndirizzoSpedizioneAndDataEffettuazione(String indirizzoSpedizione, Date dataEffettuazione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Ordine> findByDestinatario(Utente destinatario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Ordine> findAllByCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordine findByArticolo(Articolo articolo) {
		// TODO Auto-generated method stub
		return null;
	}

}
