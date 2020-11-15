package it.solvingteam.gestioneordini.dao.articolo;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.solvingteam.gestioneordini.dao.MyDAOFactory;
import it.solvingteam.gestioneordini.dao.ordine.OrdineDAO;
import it.solvingteam.gestioneordini.model.Articolo;
import it.solvingteam.gestioneordini.model.Categoria;
import it.solvingteam.gestioneordini.model.Ordine;
import it.solvingteam.gestioneordini.model.StatoOrdine;

public class ArticoloDAOImpl implements ArticoloDAO {
	
	@Override
	public Set<Articolo> list() throws Exception {
		try {
			return entityManager.createQuery("from Articolo",Articolo.class).getResultList().stream().collect(Collectors.toSet());			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'elencazione degli articoli");
		}		
	}

	@Override
	public Articolo get(Long idArticolo) throws Exception {
		if (idArticolo==null||idArticolo==null) {
			throw new Exception("Errore nell'id in input");
		}
		try {
			return entityManager.find(Articolo.class, idArticolo);				
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero dell'articolo con id="+idArticolo);
		}
	}

	@Override
	public boolean update(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema articolo in input");
		}
		//Voglio che, se l'articolo è stato ordinato da un utente, l'aggiornamento sia proibito (non voglio modificare i prezzi, per esempio) 
		entityManager.getTransaction().begin();
		OrdineDAO ordineDAO=MyDAOFactory.getOrdineDAOInstance();
		ordineDAO.setEntityManager(entityManager);
		Ordine ordineArticolo=new Ordine();
		try {
			ordineArticolo=ordineDAO.findByArticolo(articoloInstance);			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero dell'ordine associato all'articolo "+articoloInstance);
		}
		if (ordineArticolo!=null&&ordineArticolo.getStatoOrdine()!=StatoOrdine.ANNULLATO) {
			System.err.println("Aggiornamento negato: l'articolo "+articoloInstance +" è stato ordinato da un utente ed è in consegna");
			entityManager.getTransaction().rollback();
			return false;
		}		
		/* Non aggiorno qui le copie di articoloInstance che le categorie di afferenza hanno nei loro attributi articoliContenuti.
 		* Lo faccio nel metodo update di CategoriaDAOImpl*/
		try {
			articoloInstance = entityManager.merge(articoloInstance);			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'aggiornamento dell'articolo "+articoloInstance);
		}
 		entityManager.getTransaction().commit();
 		return true;
	}

	@Override
	public boolean insert(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema articolo in input");
		}
		try {
			entityManager.persist(articoloInstance);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nell'inserimento dell'articolo "+articoloInstance);
		}		
	}

	@Override
	public boolean delete(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.getTransaction().begin();
		if (articoloInstance.getOrdineDiAcquisto().getStatoOrdine()!=StatoOrdine.ANNULLATO) {
			System.err.println("Rimozione fallita: l'articolo è in consegna o è stato consegnato a un utente");
			entityManager.getTransaction().rollback();
			return false;
		}
		try {
			entityManager.remove(entityManager.merge(articoloInstance));
			entityManager.getTransaction().commit();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception("Errore nella rimozione dell'articolo");
		}
	}

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager=entityManager;
		
	}

	@Override
	public Set<Articolo> findAllByCategoriaDiAfferenza(Categoria categoriaDiAfferenza) throws Exception { 
		if (categoriaDiAfferenza==null) {
			throw new Exception("Problema categoria in input");
		}
		return categoriaDiAfferenza.getArticoliContenuti();
		
	}

	@Override
	public Set<Articolo> findAllByOrdineDiAcquisto(Ordine ordineDiAcquisto) throws Exception {
		if (ordineDiAcquisto==null) {
			throw new Exception("Problema ordine in input");
		}
		return ordineDiAcquisto.getArticoliOrdinati();
	}

	@Override
	public Articolo findByDescrizioneAndPrezzoSingolo(String descrizione, Integer prezzoSingolo) throws Exception {
		if (descrizione==null || descrizione.isEmpty()) {
			throw new Exception("Problema descrizione in input");
		}
		if (prezzoSingolo==null) {
			throw new Exception("Problema prezzo in input");
		}
		try {
			return this.list().stream().filter(articolo->articolo.equals(new Articolo(descrizione,prezzoSingolo))).findFirst().orElse(null);			
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println("Errore nell'elencazione degli articoli");
			return null;
		}
	}

}
