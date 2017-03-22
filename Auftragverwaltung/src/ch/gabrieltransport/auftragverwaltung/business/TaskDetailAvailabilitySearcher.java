package ch.gabrieltransport.auftragverwaltung.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Observable;

import ch.gabrieltransport.auftragverwaltung.dal.AnhaengerDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrzeugauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;
import javafx.beans.InvalidationListener;

public class TaskDetailAvailabilitySearcher extends Observable{

	private LocalDate dateFrom;
	private LocalDate dateUntil;
	private LocalTime timeFrom;
	private LocalTime timeUntil;
	private Fahrzeug vehicle;
	private Fahrzeugauftrag currentTask;
	
	private AnhaengerDAO trailerDAO = new AnhaengerDAO();
	private FahrerDAO personalDAO = new FahrerDAO();
	private FahrzeugauftragDAO faDAO = new FahrzeugauftragDAO();
	
	public TaskDetailAvailabilitySearcher(Fahrzeug vehicle){
		this.vehicle = vehicle;
	}
	
	public TaskDetailAvailabilitySearcher(LocalDate dateFrom, LocalDate dateUntil, Fahrzeug vehicle) {
		super();
		this.dateFrom = dateFrom;
		this.dateUntil = dateUntil;
		this.vehicle = vehicle;
	}
	public LocalDate getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
		changed();
	}
	public LocalDate getDateUntil() {
		return dateUntil;
	}
	public void setDateUntil(LocalDate dateUntil) {
		this.dateUntil = dateUntil;
		changed();
	}
	public void setDates(LocalDate dateFrom, LocalDate dateUntil) {
		this.dateFrom = dateFrom;
		this.dateUntil = dateUntil;
		changed();
	}
	public LocalTime getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(LocalTime timeFrom) {
		this.timeFrom = timeFrom;
		changed();
	}
	public LocalTime getTimeUntil() {
		return timeUntil;
	}
	public void setTimeUntil(LocalTime timeUntil) {
		this.timeUntil = timeUntil;
		changed();
	}
	public Fahrzeug getVehicle() {
		return vehicle;
	}
	public void setVehicle(Fahrzeug vehicle) {
		this.vehicle = vehicle;
		changed();
	}
	
	
	public Fahrzeugauftrag getCurrentTrailer() {
		return currentTask;
	}

	public void setCurrentTrailer(Fahrzeugauftrag currenttask) {
		this.currentTask = currenttask;
	}

	public List<Anhaenger> getAvailableTrailer(){
		List<Anhaenger> resultList;
		LocalTime timeU = timeUntil;
		LocalTime timeF = timeFrom;
		if(vehicleHasTrailerType()){
			resultList = trailerDAO.getAllbyTyp(vehicle.getAnhaengertyp());
		}else{
			resultList = trailerDAO.findAll();
		}
		if(!timesSet()){
			timeU = LocalTime.MAX;
			timeF = LocalTime.MIN;
		}
		if(datesSet()){
			List<Fahrzeugauftrag> currentTasks = faDAO.findTasksBetween(LocalDateTime.of(dateFrom, timeF), LocalDateTime.of(dateUntil, timeU));
			for (Fahrzeugauftrag fahrzeugauftrag : currentTasks) {
				if(currentTask != null){
					if (fahrzeugauftrag.getIdfahrzeugauftrag() != currentTask.getIdfahrzeugauftrag()){
						resultList.remove(fahrzeugauftrag.getAnhaenger());
					}
				} else{
					resultList.remove(fahrzeugauftrag.getAnhaenger());
				}
			}
		}
		
		return resultList;
	}
	
	
	public List<Fahrer> getAvailablePersonal(){
		List<Fahrer> resultList;
		LocalTime timeU = timeUntil;
		LocalTime timeF = timeFrom;
		
		resultList = personalDAO.findAll();
		
		if(!timesSet()){
			timeU = LocalTime.MAX;
			timeF = LocalTime.MIN;
		}
		if(datesSet()){
			List<Fahrzeugauftrag> currentTasks = faDAO.findTasksBetween(LocalDateTime.of(dateFrom, timeF), LocalDateTime.of(dateUntil, timeU));
			for (Fahrzeugauftrag fahrzeugauftrag : currentTasks) {
				if(currentTask != null){
					if (fahrzeugauftrag.getIdfahrzeugauftrag() != currentTask.getIdfahrzeugauftrag()){
						for (Fahrerauftrag fa : fahrzeugauftrag.getAuftrag().getFahrerauftrags()) {
							resultList.remove(fa.getFahrer());
						}
					}
				} else{
					for (Fahrerauftrag fa : fahrzeugauftrag.getAuftrag().getFahrerauftrags()) {
						resultList.remove(fa.getFahrer());
					}
				}
			}
		}
		
		return resultList;
	}
	
	public void changed(){
		setChanged();
		notifyObservers();
	}
	
	private boolean datesSet(){
		return (dateFrom != null && dateUntil != null);
	}
	private boolean timesSet(){
		return (timeFrom != null && timeUntil != null);
	}
	private boolean vehicleHasTrailerType(){
		return (vehicle.getAnhaengertyp() != null);
	}
	public boolean vehicleHasTrailer(){
		return vehicle.getAnhaenger();
	}

	
	
	
}
