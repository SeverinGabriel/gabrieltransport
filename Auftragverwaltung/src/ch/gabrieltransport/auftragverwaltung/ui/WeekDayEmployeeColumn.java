
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevPanel;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerauftragDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;

public class WeekDayEmployeeColumn extends XdevHorizontalLayout {

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new WeekDayEmployeeColumn(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;

	private WeekDayEmployeeColumn(Table customizedTable, Object itemId, Object columnId) {
		super();

		this.customizedTable = customizedTable;
		this.itemId = itemId;
		this.columnId = columnId;
		
		this.initUI();
		update();
	}

	public Table getTable() {
		return customizedTable;
	}

	private LocalDateTime getDateForDay(){
		int dayOfWeekOffset = 0;
		switch(getColumnId().toString()){
		case "Montag":
			dayOfWeekOffset = 0;
			break;
		case "Dienstag":
			dayOfWeekOffset = 1;
			break;
		case "Mittwoch":
			dayOfWeekOffset = 2;
			break;
		case "Donnerstag":
			dayOfWeekOffset = 3;
			break;
		case "Freitag":
			dayOfWeekOffset = 4;
			break;
		case "Samstag":
			dayOfWeekOffset = 5;
			break;
		case "Sonntag":
			dayOfWeekOffset = 6;
			break;
		}
		return (new CurrentWeek()).getCurrentWeek().getStartDate().plusDays(dayOfWeekOffset);
	}
	
	@SuppressWarnings("unchecked")
	public Fahrer getBean() {
		return ((XdevTable<Fahrer>) getTable()).getBeanContainerDataSource().getItem(getItemId()).getBean();
	}
	
	public void update() {
		double workHourCounter = 0;
		double workMinuteCounter = 0;
		final FahrerauftragDAO auftragServiceFacade = new FahrerauftragDAO();
		List<Fahrerauftrag> auftraege = auftragServiceFacade.findAuftrageon(getDateForDay(), getBean());
		for(Fahrerauftrag auftrag: auftraege){
			LocalDateTime fromDate = auftrag.getVonDatum();
			LocalDateTime untilDate = auftrag.getBisDatum();
			
			if(fromDate.getHour() == 0){
				workHourCounter += 8;
			}else{
				long hours = fromDate.until( untilDate, ChronoUnit.HOURS);
				fromDate = fromDate.plusHours( hours );
				workHourCounter += hours;
				
				long minutes = fromDate.until( untilDate, ChronoUnit.MINUTES);
				workMinuteCounter += minutes;
				if (workMinuteCounter >= 60){
					workMinuteCounter -= 60;
					workHourCounter ++;
				}
			}
			
			
		}
		
		if(workHourCounter == 0){
			panel.setStyleName("green");
		}else if(workHourCounter <=3){
			panel.setStyleName("yellow");
		}else if(workHourCounter <=5){
			panel.setStyleName("orange");
		}else {
			panel.setStyleName("red");
		}
		
	}
	
	public Object getItemId() {
		return itemId;
	}

	public Object getColumnId() {
		return columnId;
	}

	/**
	 * Event handler delegate method for the {@link XdevHorizontalLayout}.
	 *
	 * @see LayoutClickListener#layoutClick(LayoutClickEvent)
	 * @eventHandlerDelegate
	 */
	private void this_layoutClick(LayoutClickEvent event) {
		selectItem();
	}

	private void selectItem() {
		getTable().select(getItemId());
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.panel = new XdevPanel();
	
		this.setSpacing(false);
		this.setMargin(new MarginInfo(false));
	
		this.panel.setWidth(100, Unit.PERCENTAGE);
		this.panel.setHeight(25, Unit.PIXELS);
		this.addComponent(this.panel);
		this.setComponentAlignment(this.panel, Alignment.MIDDLE_CENTER);
		this.setExpandRatio(this.panel, 0.6F);
		this.setSizeFull();
	
		this.addLayoutClickListener(event -> this.this_layoutClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevPanel panel; // </generated-code>


}
