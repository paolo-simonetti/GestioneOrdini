package it.solvingteam.gestioneordini.service.ordine;

import java.util.Date;
import java.util.Set;

import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;
import it.solvingteam.gestioneordini.service.IBaseService;

public interface OrdineService extends IBaseService<Ordine> {
	public Set<Ordine> trovaTramiteStatoOrdine(StatoOrdine statoOrdine);
	public Ordine trovaTramiteIndirizzoSpedizioneEDataEffettuazione(String indirizzoSpedizione, Date dataEffettuazione);
	public Set<Ordine> trovaTramiteDestinatario(Utente destinatario); 
	public Set<Ordine> trovaTramiteCategoria(Categoria categoria);
	public Ordine trovaTramiteArticolo(Articolo articolo);
	public boolean inserisciOrdineInUtente(Ordine ordineInstance,Utente utenteInstance);

}
