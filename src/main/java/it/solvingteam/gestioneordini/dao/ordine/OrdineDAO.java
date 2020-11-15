package it.solvingteam.gestioneordini.dao.ordine;

import java.time.LocalDate;
import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;

public interface OrdineDAO extends IBaseDAO<Ordine> {
	public Set<Ordine> findAllByStatoOrdine(StatoOrdine statoOrdine) throws Exception;
	public Ordine findByIndirizzoSpedizioneAndDataEffettuazione(String indirizzoSpedizione, LocalDate dataEffettuazione) throws Exception;
	public Set<Ordine> findByDestinatario(Utente destinatario) throws Exception; 
	public Set<Ordine> findAllByCategoria(Categoria categoriaInstance) throws Exception;
	public Ordine findByArticolo(Articolo articoloInstance) throws Exception;
	
}
