package it.solvingteam.gestioneordini.dao.ordine;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;

public interface OrdineDAO extends IBaseDAO<Ordine> {
	public Set<Ordine> findAllByStatoOrdine(StatoOrdine statoOrdine);
	public Ordine findByIndirizzoSpedizioneAndDataEffettuazione(String indirizzoSpedizione, Date dataEffettuazione);
	public Set<Ordine> findByDestinatario(Utente destinatario); 
	public Set<Ordine> findAllByCategoria(Categoria categoria);
	public Ordine findByArticolo(Articolo articolo);
	
}
