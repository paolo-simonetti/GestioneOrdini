package it.solvingteam.gestioneordini.dao.categoria;

import java.util.Set;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

	@Override
	public Set<Categoria> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria get(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Categoria o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Categoria o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Categoria o) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Categoria findAllByDescrizione(String descrizione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Categoria> findAllByOrdine(Ordine ordine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer SumPrezzoSingoloByCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

}
