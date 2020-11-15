package it.solvingteam.gestioneordini.service;

import java.util.Set;

public interface IBaseService<T> {
	public Set<T> elenca() throws Exception;
	public T caricaSingoloElemento(Long id) throws Exception;
	public boolean aggiorna(T TInstance) throws Exception;
	public boolean inserisciNuovo(T TInstance) throws Exception;
	public boolean rimuovi(T TInstance) throws Exception;
}
