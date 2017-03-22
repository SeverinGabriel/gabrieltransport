package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import ch.gabrieltransport.auftragverwaltung.dal.LogDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Log;

public class LogDeletion {
	private static LocalDateTime lastDelete = LocalDateTime.now();
	private static LogDAO logDAO = new LogDAO();
	public static synchronized void deleteOldLogs(){
		if (lastDelete.until(LocalDateTime.now(), ChronoUnit.DAYS) >= 21){
			List<Log> oldLogs = logDAO.findLogsOlderThan(lastDelete);
			for (Log log : oldLogs) {
				logDAO.remove(log);
			}
		}
	}
}
