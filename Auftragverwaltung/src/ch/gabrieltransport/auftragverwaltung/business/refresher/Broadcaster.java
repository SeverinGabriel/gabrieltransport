package ch.gabrieltransport.auftragverwaltung.business.refresher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Broadcaster implements Serializable{
	
	static ExecutorService executorService =
	Executors.newSingleThreadExecutor();
	public interface BroadcastListener {
		void receiveBroadcast(String message);
	}
	//private static LinkedList<BroadcastListener> listeners = new LinkedList<BroadcastListener>();
	private static Map<BroadcastListener, LocalDateTime> listeners = new HashMap<BroadcastListener, LocalDateTime>();
	public static synchronized void register(BroadcastListener listener) {
			listeners.put(listener, LocalDateTime.now());
	}
	public static synchronized void unregister(BroadcastListener listener) {
		listeners.remove(listener);
	}
	public static synchronized void broadcast(final String message) {
		List<BroadcastListener> toRemove = new ArrayList<Broadcaster.BroadcastListener>();
		for (final BroadcastListener listener: listeners.keySet()){
			long minutes = listeners.get(listener).until(LocalDateTime.now(), ChronoUnit.MINUTES);
			if(minutes <= 29 ){
				listener.receiveBroadcast(message);
			}
			else {
				listener.receiveBroadcast("Session abgelaufen. Drücken Sie F5 für eine neue Session. Nicht gespeicherte Änderungen gehen verloren");
				toRemove.add(listener);
			}
		}
		for (BroadcastListener listener: toRemove) {
			unregister(listener);
		}
	}
}

