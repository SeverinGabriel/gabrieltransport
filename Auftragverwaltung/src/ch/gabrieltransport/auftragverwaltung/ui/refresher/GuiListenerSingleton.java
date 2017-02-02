package ch.gabrieltransport.auftragverwaltung.ui.refresher;

import java.util.Observable;

public class GuiListenerSingleton extends Observable {
	   private static GuiListenerSingleton instance = null;
	   protected GuiListenerSingleton() {
	      // Exists only to defeat instantiation.
	   }
	   public static GuiListenerSingleton getInstance() {
	      if(instance == null) {
	         instance = new GuiListenerSingleton();
	      }
	      return instance;
	   }
	   
	   public void updated(){
		   this.setChanged();
		   this.notifyObservers();
	   }
	}
