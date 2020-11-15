package it.solvingteam.gestioneordini.dao;

import java.util.Set;

import javax.persistence.EntityManager;

public interface IBaseDAO<T>{

	public Set<T> list() throws Exception;

	public T get(Long id) throws Exception;

	public boolean update(T o) throws Exception;

	public boolean insert(T o) throws Exception;

	public boolean delete(T o) throws Exception;

	public void setEntityManager(EntityManager entityManager);

}
