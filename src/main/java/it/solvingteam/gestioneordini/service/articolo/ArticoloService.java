package it.solvingteam.gestioneordini.service.articolo;

import java.util.Set;

import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.service.IBaseService;

public interface ArticoloService extends IBaseService<Articolo> {

	public Set<Articolo> trovaTramiteCategoriaDiAfferenza(Categoria categoriaDiAfferenza);
	public Set<Articolo> trovaTramiteOrdineDiAcquisto(Ordine ordineDiAcquisto);
	public Articolo trovaTramiteDescrizioneEPrezzoSingolo(String descrizione, Integer prezzoSingolo);
	public boolean inserisciArticoloInCategoria(Articolo articoloInstance, Categoria categoriaInstance);
	public boolean inserisciArticoloInOrdine(Articolo articoloInstance,Ordine ordineInstance);
	
}
