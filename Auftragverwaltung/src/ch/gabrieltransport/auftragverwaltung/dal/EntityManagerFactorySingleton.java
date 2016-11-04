package ch.gabrieltransport.auftragverwaltung.dal;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	private static EntityManagerFactory emf = null;
	   private final static String unit = "Auftragverwaltung";
	   
	   public final static EntityManagerFactory getInstance(){
	       if (emf == null)
	       {    
	            emf = Persistence.createEntityManagerFactory(unit);
	       }
	       return emf;
	   }
	   public final static void close(){
	       emf.close();
	   }
}
