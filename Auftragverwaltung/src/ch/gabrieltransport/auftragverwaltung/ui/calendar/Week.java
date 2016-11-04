package ch.gabrieltransport.auftragverwaltung.ui.calendar;

import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;

public class Week {

	private LocalDateTime startDate;
	
	
	public Week(){
		LocalDate now = LocalDate.now();
		LocalDate monday = now.with(DayOfWeek.MONDAY);
		LocalTime morning = LocalTime.MIN;
		startDate = LocalDateTime.of(monday, morning);
	}
	public Week(LocalDateTime start){
		startDate = start.with(DayOfWeek.MONDAY);
	}
	public int getWeekNr() {
		WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
		return startDate.get(weekFields.weekOfWeekBasedYear());
	}
	
	//public String getWeekString(){
		//SimpleDateFormat format1 = new SimpleDateFormat("dd.MM");
		//return format1.format(cal.getTime()) + " - " + cal.;
	//}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	
}
