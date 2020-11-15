package it.solvingteam.gestioneordini.service.ruolo;

import it.solvingteam.gestioneordini.model.Ruolo;
import it.solvingteam.gestioneordini.service.IBaseService;

public interface RuoloService extends IBaseService<Ruolo> {

	public Ruolo trovaTramiteDescrizioneECodice(String descrizione,String codice);

}
