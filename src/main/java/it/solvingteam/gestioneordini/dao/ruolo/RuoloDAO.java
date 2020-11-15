package it.solvingteam.gestioneordini.dao.ruolo;

import it.solvingteam.gestioneordini.dao.IBaseDAO;
import it.solvingteam.gestioneordini.model.Ruolo;

public interface RuoloDAO extends IBaseDAO<Ruolo> {

	public Ruolo findByDescrizioneAndCodice(String descrizione,String codice);

}
