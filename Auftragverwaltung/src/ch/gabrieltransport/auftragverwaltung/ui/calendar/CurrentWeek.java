package ch.gabrieltransport.auftragverwaltung.ui.calendar;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Observable;

import com.vaadin.server.VaadinService;

public class CurrentWeek extends Observable {	
	private Week weekMember;
	public CurrentWeek(){
		weekMember = new Week();
	}
	
	public void setNextWeek(){
		LocalDateTime nextStart = getCurrentWeek().getStartDate().plusDays(7);
        setWeek(new Week(nextStart)); 
	}
	public void setPreviousWeek(){
		LocalDateTime previousStart = getCurrentWeek().getStartDate().minusDays(7);
		setWeek(new Week(previousStart));
	}
	
	public void setWeek(Week week){
		setCurrentWeek(week); 
        this.setChanged();
        notifyObservers();
	}
	
	public Week getCurrentWeek(){
		
		if (VaadinService.getCurrentRequest() == null){
			return weekMember;
		}else{
		if ((Week) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("week") == null){
			setCurrentWeek(new Week());
		}
		return (Week) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("week");}
	}
	
	private void setCurrentWeek(Week week){
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("week", week);
		weekMember = week;
		
	}
}
