 package it.solvingteam.gestioneordini.dao.ordine;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.dao.MyDAOFactory;
import it.solvingteam.gestioneordini.dao.articolo.ArticoloDAO;
import it.solvingteam.gestioneordini.dao.utente.UtenteDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;
import it.solvingteam.gestioneordini.model.Utente;

public class OrdineDAOImpl implements OrdineDAO {

	@Override
	public Set<Ordine> list() throws Exception {
		try {
			return entityManager.createQuery("from Ordine",Ordine.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione degli ordini");
		}		
	}

	@Override
	public Ordine get(Long idOrdine) throws Exception {
		if (idOrdine==null||idOrdine<=0) {
			throw new Exception("Errore nell'id dell'ordine in input");
		}
		try {
			return entityManager.find(Ordine.class, idOrdine);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero dell'ordine con id="+idOrdine);
		}
	}

	@Override
	public boolean update(Ordine ordineInstance) throws Exception {
		if (ordineInstance==null) {
			throw new Exception("Problemi nell'ordine in input");
		} else if(!ordineInstance.isCreato()) {
			throw new Exception("Aggiornamento non riuscito: "+ordineInstance+" è in consegna, consegnato o annullato");
		} else {
			//Aggiorno gli articoli contenuti in ordineInstance
			entityManager.getTransaction().begin();
			ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
			articoloDAO.setEntityManager(entityManager);
			boolean aggiornamentoRiuscito=true;
			for (Articolo articolo:ordineInstance.getArticoliOrdinati()) {
				try {
					aggiornamentoRiuscito=articoloDAO.update(articolo);
					if (!aggiornamentoRiuscito) {
						entityManager.getTransaction().rollback();
						throw new Exception("Update di "+ ordineInstance+" annullato: aggiornamento di "+articolo+" non riuscito.");
					} 
				} catch(Exception e) {
					entityManager.getTransaction().rollback();
					throw new Exception("Update di "+ ordineInstance+" annullato: problemi nell'aggiornamento di "+articolo);
				}
			}
			// Se ci sono articoli non più contenuti in ordineInstance, li rimuovo
			Set<Articolo> articoliDaRimuovereDaOrdineInstance=new TreeSet<>();
			for (Articolo articolo:ordineInstance.getArticoliOrdinati()) {
				if (!articolo.getOrdineDiAcquisto().equals(ordineInstance)) {
					articoliDaRimuovereDaOrdineInstance.add(articolo);
				}
			}
			if (articoliDaRimuovereDaOrdineInstance.size()>0) {
				for (Articolo articolo:articoliDaRimuovereDaOrdineInstance) {
					ordineInstance.getArticoliOrdinati().remove(articolo);
				}
			}
			// Prima di aggiornare l'ordine sul db, devo vedere chi è il suo utente destinatario, nel caso in cui cambi a seguito del merge
			Utente vecchioDestinatario=new Utente();
			try {
				vecchioDestinatario=this.get(ordineInstance.getIdOrdine()).getDestinatario();
			} catch(Exception e) {
				entityManager.getTransaction().rollback();
				e.printStackTrace();
				throw new Exception("Errore nel recupero del vecchio destinatario di "+ordineInstance);
			}
			// Se il vecchio e il nuovo destinatario di ordineInstance coincidono, non viene eseguito nulla del prossimo blocco  
			if(vecchioDestinatario!=null && !vecchioDestinatario.equals(ordineInstance.getDestinatario())) {
				vecchioDestinatario.getOrdiniEffettuati().remove(ordineInstance);
				//Ora che ho rimosso l'ordine dal vecchio destinatario, aggiorno i dati di quest'ultimo
				UtenteDAO utenteDAO=MyDAOFactory.getUtenteDAOInstance();
				utenteDAO.setEntityManager(entityManager);
				boolean aggiornamentoVecchioDestinatarioRiuscito=false;
				try {
					aggiornamentoVecchioDestinatarioRiuscito=utenteDAO.update(vecchioDestinatario);				
				} catch(Exception e) {
					entityManager.getTransaction().rollback();
					e.printStackTrace();
					throw new Exception("Errore nell'aggiornamento dei dati del vecchio destinatario "+ vecchioDestinatario +" di " +ordineInstance);
				} 
				if (!aggiornamentoVecchioDestinatarioRiuscito) {
					entityManager.getTransaction().rollback();
					throw new Exception("Fallito aggiornamento dei dati del vecchio destinatario "+ vecchioDestinatario +" di " +ordineInstance);
				}
			}
			// Adesso posso effettivamente aggiornare i dati di ordineInstance
			try {	
				ordineInstance=entityManager.merge(ordineInstance);
				entityManager.getTransaction().commit();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nell'update di "+ ordineInstance);
			}

		}
	}

	@Override
	public boolean insert(Ordine ordineInstance) throws Exception {
		if (ordineInstance==null) {
			throw new Exception("Problemi nell'ordine in input");
		} else if (ordineInstance.getArticoliOrdinati().size()==0) {
			throw new Exception ("Inserimento fallito: a "+ ordineInstance+" non sono associati articoli");
		} else if (ordineInstance.getDestinatario()==null) {
			throw new Exception ("Inserimento fallito: a "+ ordineInstance+" non sono associati destinatari");
		} else if(!ordineInstance.isCreato()) {
			throw new Exception("Inserimento fallito: " + ordineInstance +" è già in consegna, è stato annullato o è già stato consegnato");
		} else if(ordineInstance.getArticoliOrdinati().stream().filter(articolo->articolo.isEsaurito()).findFirst().orElse(null)!=null) {
			//Se sono qui, uno degli articoli ordinati è esaurito
			throw new Exception("Inserimento fallito: uno degli articoli di " +ordineInstance+" è esaurito");
		} else {
			entityManager.getTransaction().begin();
			//Controllo se l'ordine è già presente nel db
			Set<Ordine> elencoOrdini=null;
			try {
				elencoOrdini=this.list();				
			} catch(Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
				throw new Exception("Inserimento fallito: non è stato possibile recuperare l'elenco degli ordini già effettuati");
			}
			//Se ordineInstance è il primo ordine ad essere inserito nel db, questo blocco viene saltato
			if (elencoOrdini!=null) {
				for (Ordine ordine:this.list()) {
					if (ordine.equals(ordineInstance)) {
						entityManager.getTransaction().rollback();
						throw new Exception("L'ordine "+ordineInstance+ " è già presente nel db");
					}
					
				}	
			}
			//Prendo gli articoli ordinati in ordineInstance e inserisco ordineInstance nel loro attributo ordineDiAcquisto
			ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
			articoloDAO.setEntityManager(entityManager);
			boolean aggiornamentoArticoloRiuscito;
			for (Articolo articolo:ordineInstance.getArticoliOrdinati()) {
				articolo.setOrdineDiAcquisto(ordineInstance);
				try {
					aggiornamentoArticoloRiuscito=articoloDAO.update(articolo);
				} catch(Exception e) {
					entityManager.getTransaction().rollback();
					e.printStackTrace();
					throw new Exception("Inserimento di "+ordineInstance+ " fallito: problemi nell'aggiornamento di "+articolo);
				}
				if(!aggiornamentoArticoloRiuscito) {
					entityManager.getTransaction().rollback();
					throw new Exception("Inserimento di "+ordineInstance+ " fallito: aggiornamento di "+articolo+ " non riuscito");
				}
			}
			//Inserisco ordineInstance nell'attributo ordiniEffettuati del destinatario di ordineInstance
			UtenteDAO utenteDAO=MyDAOFactory.getUtenteDAOInstance();
			utenteDAO.setEntityManager(entityManager);
			ordineInstance.getDestinatario().getOrdiniEffettuati().add(ordineInstance);
			boolean aggiornamentoDiDestinatarioRiuscito;
			try {
				aggiornamentoDiDestinatarioRiuscito=utenteDAO.update(ordineInstance.getDestinatario());
			} catch(Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
				throw new Exception("Inserimento di "+ordineInstance+ " fallito: problemi nell'aggiornamento di "+ordineInstance.getDestinatario());
			}
			if (!aggiornamentoDiDestinatarioRiuscito) {
				entityManager.getTransaction().rollback();
				throw new Exception("Inserimento di "+ordineInstance+ " fallito: aggiornamento di "+ordineInstance.getDestinatario()+ " non riuscito");
			}
			//Ora posso effettivamente inserire ordineInstance nel db
			try {
				entityManager.persist(ordineInstance);
				entityManager.getTransaction().commit();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nell'inserimento dell'ordine "+ordineInstance);
			}		
		}
	}

	@Override
	public boolean delete(Ordine ordineInstance) throws Exception {
		if (ordineInstance==null) {
			throw new Exception("Problema nell'ordine in input");
		} else if (!ordineInstance.isAnnullato()) {
			throw new Exception ("Rimozione di "+ordineInstance+" fallita: l'ordine deve essere prima annullato");
		} else {
			/*Se sono qui, l'ordine è stato annullato: lo rimuovo dall'attributo ordineDiAcquisto degli articoli che contiene, così come
			 * dall'attributo ordiniEffettuati dell'utente destinatario */
			entityManager.getTransaction().begin();
			//Rimozione da articolo:
			ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
			articoloDAO.setEntityManager(entityManager);
			boolean aggiornamentoDiArticoloRiuscito;
			for (Articolo articolo:	ordineInstance.getArticoliOrdinati()) {
				articolo.setOrdineDiAcquisto(null);
				try {
					aggiornamentoDiArticoloRiuscito=articoloDAO.update(articolo);
				} catch(Exception e) {
					e.printStackTrace();
					entityManager.getTransaction().rollback();
					throw new Exception("Rimozione di "+ordineInstance+" non riuscita: problemi nell'aggiornamento di "+articolo);
				}
				if (!aggiornamentoDiArticoloRiuscito) {
					entityManager.getTransaction().rollback();
					throw new Exception("Rimozione di "+ordineInstance+" non riuscita: fallito l'aggiornamento di "+articolo);
				}
			}
			//Rimozione da utente:
			ordineInstance.getDestinatario().getOrdiniEffettuati().remove(ordineInstance);
			UtenteDAO utenteDAO=MyDAOFactory.getUtenteDAOInstance();
			utenteDAO.setEntityManager(entityManager);
			boolean aggiornamentoDiUtenteRiuscito;
			try {
				aggiornamentoDiUtenteRiuscito=utenteDAO.update(ordineInstance.getDestinatario());
			} catch(Exception e) {
				entityManager.getTransaction().rollback();
				e.printStackTrace();
				throw new Exception("Rimozione di "+ordineInstance+" fallita: problemi nell'aggiornamento del destinatario");
			}
			if (!aggiornamentoDiUtenteRiuscito) {
				entityManager.getTransaction().rollback();
				throw new Exception("Rimozione di "+ordineInstance+" fallita: aggiornamento del destinatario non riuscito");
			}
			// Ora posso effettivamente rimuovere l'ordine
			try {
				entityManager.remove(entityManager.merge(ordineInstance));
				entityManager.getTransaction().commit();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
				throw new Exception("Errore nella rimozione di "+ordineInstance);
			}

		}
	}

	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
	}
	
	@Override
	public Set<Ordine> findAllByStatoOrdine(StatoOrdine statoOrdine) throws Exception {
		if (statoOrdine==null) {
			throw new Exception("Problema StatoOrdine in input");
		}
		Set<Ordine> ordiniInStatoOrdine=new TreeSet<>();
		ordiniInStatoOrdine=entityManager.createQuery("from Ordine o where o.StatoOrdine="+statoOrdine,Ordine.class).getResultList().stream()
			.collect(Collectors.toSet());
		if (ordiniInStatoOrdine==null) {
			System.err.println("Non sono stati trovati ordini il cui stato sia "+statoOrdine);
		}
		return ordiniInStatoOrdine;
	}

	@Override
	public Ordine findByIndirizzoSpedizioneAndDataEffettuazione(String indirizzoSpedizione, LocalDate dataEffettuazione) throws Exception {
		if (indirizzoSpedizione==null||indirizzoSpedizione.isEmpty()) {
			throw new Exception("Problema descrizione in input");
		} else if(dataEffettuazione==null) {
			throw new Exception ("Problema dataEffettuazione in input");
		} else {
			Ordine ordineResult=new Ordine();
			try {
				ordineResult=entityManager.createQuery("from Ordine o where o.indirizzo_spedizione like "+indirizzoSpedizione+
						" and o.data_effettuazione "+dataEffettuazione, Ordine.class).getResultList().stream().findFirst().orElse(null);
				if (ordineResult==null) {
					System.err.println("Non è stato trovato alcun ordine associato a "+indirizzoSpedizione+ " e "+dataEffettuazione);
				}
				return ordineResult;
			} catch(Exception e) {
				e.printStackTrace();
				throw new Exception("Errore nel recupero dell'ordine richiesto");
			}
			
		}
		
	}

	@Override
	public Set<Ordine> findByDestinatario(Utente destinatario) throws Exception {
		if (destinatario==null) {
			throw new Exception("Problema utente in input");
		}
		entityManager.getTransaction().begin();
		//Aggiorno l'utente presente sul db
		UtenteDAO utenteDAO=MyDAOFactory.getUtenteDAOInstance();
		utenteDAO.setEntityManager(entityManager);
		boolean aggiornamentoDestinatarioRiuscito=false;
		try {
			aggiornamentoDestinatarioRiuscito=utenteDAO.update(destinatario);
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Ricerca dell'ordine fallita: problemi nell'aggiornamento del destinatario in input");
		}
		if (!aggiornamentoDestinatarioRiuscito) {
			entityManager.getTransaction().rollback();
			throw new Exception("Ricerca dell'ordine fallita: aggiornamento del destinatario in input non riuscito");
		}
		// Ora posso effettuare la ricerca. Trascuro gli ordini annullati
		Set<Ordine> ordiniResult=entityManager.createQuery("from Ordine o where o.destinatario.id like "+destinatario.getId(),Ordine.class)
				.getResultList().stream().filter(ordine->!ordine.isAnnullato()).collect(Collectors.toSet());
		if (ordiniResult.size()==0) {
			System.err.println("Non sono stati trovati ordini effettuati da "+destinatario);
			//Storicizzo il risultato nel model 
			destinatario.setOrdiniEffettuati(null);
			entityManager.getTransaction().commit();
			return null;
		} else {
			//Storicizzo il risultato nel model
			entityManager.getTransaction().commit();
			destinatario.setOrdiniEffettuati((TreeSet<Ordine>)ordiniResult);
			return ordiniResult;
		}
	}

	@Override
	public Set<Ordine> findAllByCategoria(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance==null) {
			throw new Exception("Problema categoria in input");
		} else if(categoriaInstance.getArticoliContenuti().size()==0) {
			System.err.println("Nella categoria in input non sono presenti articoli");
			return null;
		} else {
			// Considero anche gli articoli esauriti, perché sono interessato a sapere tutti gli ordini (tranne quelli annullati)
			entityManager.getTransaction().begin();
			ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
			articoloDAO.setEntityManager(entityManager);
			//Raccolgo solo gli articoli che siano stati ordinati e i cui ordiniDiAcquisto non siano stati annullati
			Set<Articolo> articoliEffettivamenteOrdinatiCheAfferisconoACategoriaInstance=articoloDAO
					.findAllByCategoriaDiAfferenza(categoriaInstance).stream()
					.filter(articolo->articolo.getOrdineDiAcquisto()!=null&&!articolo.getOrdineDiAcquisto().isAnnullato())
					.collect(Collectors.toSet());
			if(articoliEffettivamenteOrdinatiCheAfferisconoACategoriaInstance.size()==0) {
				System.err.println("Nessuno degli articoli contenuti in "+categoriaInstance+" è stato ordinato o ha ordini non annullati");
				return null;	
			} else {
				//Se sono qui, ci sono degli articoli in categoriaInstance che sono stati effettivamente ordinati
				Set<Ordine> ordiniResult=null;
				boolean aggiornamentoArticoliRiuscito=false;
				for (Articolo articolo:articoliEffettivamenteOrdinatiCheAfferisconoACategoriaInstance) {
					try {
						aggiornamentoArticoliRiuscito=this.update(articolo.getOrdineDiAcquisto());
						if(!aggiornamentoArticoliRiuscito) {
							entityManager.getTransaction().rollback();
							throw new Exception("Ricerca fallita: l'update di "+articolo+" non è riuscito");
						}
					} catch(Exception e) {
						e.printStackTrace();
						entityManager.getTransaction().rollback();
						throw new Exception("Ricerca fallita: problemi nell'esecuzione dell'update di "+articolo);
					}
				}
				// Ora che ho aggiornato tutti gli articoli, posso inserirli in ordiniResult
				ordiniResult=new TreeSet<Ordine>();
				for (Articolo articolo:articoliEffettivamenteOrdinatiCheAfferisconoACategoriaInstance) {
					ordiniResult.add(articolo.getOrdineDiAcquisto());
				}
				entityManager.getTransaction().commit();
				return ordiniResult;
			}	
		}		
	}

	@Override
	/*Ho la certezza che ci sia un solo ordine relativo ad articoloInstance perché, quando eseguo l'insert di Ordine, controllo che
	l'ordine non sia già presente */
	public Ordine findByArticolo(Articolo articoloInstance) throws Exception {
		if (articoloInstance==null) {
			throw new Exception("Problema in articolo in input");
		}
		entityManager.getTransaction().begin();
		// Carico articoloInstance sul db
		ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
		articoloDAO.setEntityManager(entityManager);
		boolean aggiornamentoDiArticoloInstanceRiuscito=false;
		try {
			aggiornamentoDiArticoloInstanceRiuscito=articoloDAO.update(articoloInstance);
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Ricerca di ordine fallita: problemi nell'aggiornamento di "+articoloInstance);
		}
		if(!aggiornamentoDiArticoloInstanceRiuscito) {
			entityManager.getTransaction().rollback();
			throw new Exception("Ricerca di ordine fallita: aggiornamento di "+articoloInstance+" non riuscito");
		}
		// Ora posso passare alla ricerca dell'ordine richiesto
		Ordine ordineResult=entityManager.createQuery("from Ordine o where o.idOrdine like "+articoloInstance.getOrdineDiAcquisto(),Ordine.class)
				.getResultList().stream().findFirst().orElse(null);
		if(ordineResult==null) {
			System.err.println("Non sono stati trovati ordini relativi ad "+articoloInstance);
			//Aggiorno il model
			articoloInstance.setOrdineDiAcquisto(null);
			return null;
		} else {
			articoloInstance.setOrdineDiAcquisto(ordineResult);
			entityManager.getTransaction().commit();
			return ordineResult;
		}
	}

}
