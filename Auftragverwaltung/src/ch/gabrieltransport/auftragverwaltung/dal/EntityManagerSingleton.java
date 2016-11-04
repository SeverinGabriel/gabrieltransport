package ch.gabrieltransport.auftragverwaltung.dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerSingleton {
	private static EntityManager em = null;
    
    public static EntityManager getInstance(){
        if (em == null){
            EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
            em = emf.createEntityManager();
        }
        return em;
    }

}
