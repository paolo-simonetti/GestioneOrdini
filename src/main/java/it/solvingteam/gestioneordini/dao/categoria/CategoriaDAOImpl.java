package it.solvingteam.gestioneordini.dao.categoria;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.dao.MyDAOFactory;
import it.solvingteam.gestioneordini.dao.articolo.ArticoloDAO;
import it.solvingteam.gestioneordini.dao.ordine.OrdineDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {

	@Override
	public Set<Categoria> list() throws Exception {
		try {
			return entityManager.createQuery("from Categoria",Categoria.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione delle categorie");
		}		
	}

	@Override
	public Categoria get(Long idCategoria) throws Exception {
		try {
			return entityManager.find(Categoria.class, idCategoria);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero della categoria con id="+idCategoria);
		}
	}

	@Override
	public boolean update(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema categoria in input");
		}
		entityManager.getTransaction().begin();	
		// Aggiorno gli articoli che l'oggetto categoria ha come proprio attributo
		ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
		articoloDAO.setEntityManager(entityManager);
		Set<Articolo> articoliDaRimuovereDaCategoriaInstance=new TreeSet<>();
		for (Articolo articolo:categoriaInstance.getArticoliContenuti()) {
			try {
				articolo=articoloDAO.get(articolo.getIdArticolo());
				// Se l'articolo non ha più categoriaInstance come categoria di afferenza, lo rimuovo dagli articoli contenuti in categoriaInstance
				boolean articoloIsInCategoriaInstance=false;
				for (Categoria categoria:articolo.getCategorieDiAfferenza()) {
					if (categoria.equals(categoriaInstance)) {
						articoloIsInCategoriaInstance=true;
					}
				}
				if (!articoloIsInCategoriaInstance) {
					articoliDaRimuovereDaCategoriaInstance.add(articolo); /* non posso rimuovere articolo direttamente: devo		
					* aggiungere gli articoli ad articoliDaRimuovereDaCategoriaInstance, altrimenti starei accorciando il Set 
					* categoriaInstance.getArticoliContenuti() mentre lo ciclo */
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.err.println("Errore nell'aggiornamento dell'articolo "+articolo+ " contenuto in "+categoriaInstance);
				entityManager.getTransaction().rollback();
				return false;
			}
		}
		//Finalmente posso rimuovere gli articoli che non sono più in categoriaInstance
		if (articoliDaRimuovereDaCategoriaInstance.size()>0) {
			for (Articolo articolo:articoliDaRimuovereDaCategoriaInstance) {
				categoriaInstance.getArticoliContenuti().remove(articolo); /* Il fatto che articolo sia in articoliDaRimuovereDaCategoriaInstance,
				e non in categoriaInstance.getArticoliContenuti(), non è un problema: il metodo remove() controlla, per ogni elemento presente
				nel Set categoriaInstance.getArticoliContenuti(), se tale elemento equals(articolo); e io, nella classe Articolo, ho 
				fatto l'override del meotodo equals sulla base di descrizione e prezzoSingolo, quindi sono a posto. */ 				
			}
		}
		try {
			categoriaInstance = entityManager.merge(categoriaInstance);
	 		entityManager.getTransaction().commit();
	 		return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento della "+categoriaInstance);
		}


	}

	@Override
	public boolean insert(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema categoria in input");
		}
		try {
			entityManager.persist(categoriaInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento della categoria "+categoriaInstance);
		}	
	}

	@Override
	public boolean delete(Categoria categoriaInstance) throws Exception {
		entityManager.getTransaction().begin();
		//Se alla categoria afferiscono degli articoli, blocco la rimozione
		if (categoriaInstance.getArticoliContenuti().size()>0) {
			System.err.println("Eliminazione fallita: alla categoria in input afferiscono degli articoli");
			entityManager.getTransaction().rollback();
			return false;
		} 
		try {
			entityManager.remove(entityManager.merge(categoriaInstance));
			entityManager.getTransaction().commit();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione della categoria");
		}

	}

	private EntityManager entityManager;
	
	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
		
	}

	@Override
	public Categoria findByDescrizione(String descrizione) throws Exception {
		Categoria categoriaResult=null;
		try {
			categoriaResult=entityManager.createQuery("from Categoria c where c.descrizione like "+descrizione,Categoria.class)
				.getResultList().stream().findFirst().orElse(null);
			if (categoriaResult==null) {
				throw new NullPointerException("Non è stata trovata alcuna categoria con descrizione "+descrizione);
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			return categoriaResult;
		} catch(Exception e) {
			throw new Exception("Errore nel recupero della categoria richiesta");
		} 
		entityManager.getTransaction().commit();
		return categoriaResult;			
	}

	@Override
	public Set<Categoria> findAllByOrdine(Ordine ordineInstance) throws Exception {
		if (ordineInstance==null) {
			throw new Exception("Problema ordine in input");
		} 
		//Aggiorno l'instanza di ordine, recuperandola dal db
		entityManager.getTransaction().begin();
		try {
			OrdineDAO ordineDAO=MyDAOFactory.getOrdineDAOInstance();
			ordineInstance=ordineDAO.get(ordineInstance.getIdOrdine());			
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nel recupero dell'ordine di input dal db!");
		}
		if(ordineInstance.getArticoliOrdinati().size()==0) {
			entityManager.getTransaction().rollback();
			throw new Exception("Nell'ordine in input non sono presenti articoli!");
		} else {
			Set<Articolo> articoliOrdinati=ordineInstance.getArticoliOrdinati();
			/* Aggiorno i singoli articoli presenti, prendendoli dal db. Lancio un'eccezione se il recupero fallisce, o se uno degli 
			* articoli non è presente nel db. */
			ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
			for (Articolo articolo:articoliOrdinati) {
				try {
					articolo=articoloDAO.get(articolo.getIdArticolo());					
				} catch(Exception e) {
					entityManager.getTransaction().rollback();
					throw new Exception("Errore nel recupero dal db di "+articolo);
				}
				if (articolo==null) {
					entityManager.getTransaction().rollback();
					throw new Exception("Uno degli articoli di "+ordineInstance+ "non è presente nel db!");
				}
			}
			//Ora che gli articoli sono aggiornati, posso prenderne le rispettive categorie
			Map<Articolo,Set<Categoria>> tabellaArticoliOrdinatiVsCategorieDiAfferenza=new TreeMap<>();
			articoliOrdinati.stream().filter(articolo->articolo.getCategorieDiAfferenza().size()>0)
			.forEach(articolo->tabellaArticoliOrdinatiVsCategorieDiAfferenza.put(articolo,articolo.getCategorieDiAfferenza()));
			if (tabellaArticoliOrdinatiVsCategorieDiAfferenza.size()==0) {
				entityManager.getTransaction().rollback();
				throw new Exception("Nessuno degli articoli presenti in "+ordineInstance+ " è categorizzato!");
			}
			// Ora che so che almeno un articolo è categorizzato, posso formare il Set di categorie richiesto
			Set<Categoria> categorieResult=new TreeSet<>();
			tabellaArticoliOrdinatiVsCategorieDiAfferenza.values().forEach(setCategorie->setCategorie.stream().forEach(categoria->categorieResult.add(categoria)));
			entityManager.getTransaction().commit();
			return categorieResult;
		}
		
	}

	@Override
	public Integer SumPrezzoSingoloByCategoria(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance==null) {
			throw new Exception("Problema categoria in input");
		}
		entityManager.getTransaction().begin();
		//Aggiorno la categoria prendendola dal db
		try {
			categoriaInstance=this.get(categoriaInstance.getIdCategoria());			
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nel recupero della categoria in input dal db");
		}
		// Prendo tutti gli articoli che afferiscono a quella categoria
		Set<Articolo> articoliContenuti=categoriaInstance.getArticoliContenuti();
		if (articoliContenuti.size()==0) {
			System.err.println("Alla categoria in input non afferisce alcun articolo!");
			entityManager.getTransaction().rollback();
			return 0;
		}
		// Aggiorno gli articoli prendendoli da db
		ArticoloDAO articoloDAO=MyDAOFactory.getArticoloDAOInstance();
		for (Articolo articolo:articoliContenuti) {	
			try {
				articolo=articoloDAO.get(articolo.getIdArticolo());				
			} catch(Exception e) {
				entityManager.getTransaction().rollback();
				throw new Exception("Problema nel recupero di "+articolo+ " dal db");
			}
			if (articolo==null) {
				entityManager.getTransaction().rollback();
				throw new Exception("Uno degli articoli di "+categoriaInstance+ " non è presente nel db!");
			}			
		}
		//Ora che tutti gli articoli sono aggiornati, posso calcolare il prezzo totale
		entityManager.getTransaction().commit();
		Integer prezzoTotale=0;
		for (Articolo articolo:articoliContenuti) {
			prezzoTotale+=articolo.getPrezzoSingolo();
		}
		return prezzoTotale;
	}

}
