
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.Week;

public class MainView extends XdevView {

	
	private CurrentWeek currWeek = new CurrentWeek();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
	private List<WeekDayEmployeeColumn> weekDayEmployee = new ArrayList<WeekDayEmployeeColumn>();
	/**
	 * 
	 */
	public MainView() {
		super();
		this.initUI();
		
		updateLabels();
}
	

	public void updateGuiGenerators(){
			tblTask.refreshRowCache();
			tblEmployee.refreshRowCache();
	}
		
	public void updateLabels(){
		Week currentWeek = currWeek.getCurrentWeek();
		lblWeek.setValue("Woche " + currentWeek.getWeekNr());
		LocalDateTime start = currentWeek.getStartDate();
		LocalDateTime end = start.plusDays(6);
		lblWeekInterval.setValue(start.format(formatter) + " - " + end.format(formatter)); // "29.08 - 04.09"
		lblYear.setValue(String.valueOf(start.getYear()));
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button2}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button2_buttonClick(Button.ClickEvent event) {
		currWeek.setNextWeek();
		updateLabels();
		updateGuiGenerators();
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_buttonClick(Button.ClickEvent event) {
		currWeek.setPreviousWeek();
		updateLabels();
		updateGuiGenerators();
	}
	
	
	/**
	 * Event handler delegate method for the {@link XdevTable}
	 * {@link #tblEmployee}.
	 *
	 * @see Table.HeaderClickListener#headerClick(Table.HeaderClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void tblEmployee_headerClick(Table.HeaderClickEvent event) {
		tblEmployee.setVisible(false);
		btnExpandPersonal.setVisible(true);
	}


	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnExpandPersonal}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnExpandPersonal_buttonClick(Button.ClickEvent event) {
		tblEmployee.setVisible(true);
		btnExpandPersonal.setVisible(false);
	}


	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.button = new XdevButton();
		this.lblWeek = new XdevLabel();
		this.button2 = new XdevButton();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.lblWeekInterval = new XdevLabel();
		this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
		this.lblYear = new XdevLabel();
		this.btnExpandPersonal = new XdevButton();
		this.tblEmployee = new XdevTable<>();
		this.tblTask = new XdevTable<>();
	
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.button.setIcon(FontAwesome.CARET_LEFT);
		this.button.setCaption("");
		this.button2.setIcon(FontAwesome.CARET_RIGHT);
		this.button2.setCaption("");
		this.horizontalLayout2.setMargin(new MarginInfo(false));
		this.lblWeekInterval.setValue("01.08 - 07.08");
		this.lblYear.setValue("2016");
		this.btnExpandPersonal.setIcon(FontAwesome.CARET_DOWN);
		this.btnExpandPersonal.setCaption("Personal");
		this.btnExpandPersonal.setVisible(false);
		this.tblEmployee.setCaption("Personal");
		this.tblEmployee.setContainerDataSource(Fahrer.class);
		this.tblEmployee.addGeneratedColumn("Montag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Dienstag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Mittwoch", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Donnerstag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Freitag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Samstag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.addGeneratedColumn("Sonntag", new WeekDayEmployeeColumn.Generator());
		this.tblEmployee.setVisibleColumns("vorname", "nachname", "Montag", "Dienstag", "Mittwoch", "Donnerstag",
				"Freitag", "Samstag", "Sonntag");
		this.tblTask.setCaption("Aufträge");
		this.tblTask.setColumnCollapsingAllowed(true);
		this.tblTask.setContainerDataSource(Fahrzeug.class);
		this.tblTask.addGeneratedColumn("Montag", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Dienstag", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Mittwoch", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Donnerstag", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Freitag", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Samstag", new WeekDayTaskColumn.Generator());
		this.tblTask.addGeneratedColumn("Sonntag", new WeekDayTaskColumn.Generator());
		this.tblTask.setVisibleColumns("idfahrzeug", "kennzeichen", "gewicht", "Montag", "Dienstag", "Mittwoch",
				"Donnerstag", "Freitag", "Samstag", "Sonntag");
		this.tblTask.setColumnHeader("idfahrzeug", "#");
		this.tblTask.setColumnWidth("idfahrzeug", 25);
		this.tblTask.setColumnHeader("kennzeichen", "Kennzeichen");
		this.tblTask.setColumnWidth("kennzeichen", 90);
		this.tblTask.setColumnWidth("gewicht", 75);
		this.tblTask.setColumnHeader("Montag", "Montag");
		this.tblTask.setColumnHeader("Dienstag", "Dienstag");
		this.tblTask.setColumnHeader("Mittwoch", "Mittwoch");
		this.tblTask.setColumnHeader("Donnerstag", "Donnerstag");
		this.tblTask.setColumnHeader("Freitag", "Freitag");
		this.tblTask.setColumnHeader("Samstag", "Samstag");
		this.tblTask.setColumnHeader("Sonntag", "Sonntag");
	
		this.button.setSizeUndefined();
		this.horizontalLayout.addComponent(this.button);
		this.horizontalLayout.setComponentAlignment(this.button, Alignment.MIDDLE_CENTER);
		this.lblWeek.setSizeUndefined();
		this.horizontalLayout.addComponent(this.lblWeek);
		this.horizontalLayout.setComponentAlignment(this.lblWeek, Alignment.MIDDLE_CENTER);
		this.button2.setSizeUndefined();
		this.horizontalLayout.addComponent(this.button2);
		this.horizontalLayout.setComponentAlignment(this.button2, Alignment.MIDDLE_CENTER);
		CustomComponent horizontalLayout_spacer = new CustomComponent();
		horizontalLayout_spacer.setSizeFull();
		this.horizontalLayout.addComponent(horizontalLayout_spacer);
		this.horizontalLayout.setExpandRatio(horizontalLayout_spacer, 1.0F);
		this.horizontalSplitPanel.setSplitPosition(28, Unit.PERCENTAGE);
		this.lblWeekInterval.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblWeekInterval);
		this.horizontalLayout2.setComponentAlignment(this.lblWeekInterval, Alignment.MIDDLE_CENTER);
		this.horizontalSplitPanel.setWidth(15, Unit.PERCENTAGE);
		this.horizontalSplitPanel.setHeight(100, Unit.PERCENTAGE);
		this.horizontalLayout2.addComponent(this.horizontalSplitPanel);
		this.horizontalLayout2.setComponentAlignment(this.horizontalSplitPanel, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.horizontalSplitPanel, 1.0F);
		this.lblYear.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblYear);
		this.horizontalLayout2.setComponentAlignment(this.lblYear, Alignment.MIDDLE_CENTER);
		this.gridLayout.setColumns(1);
		this.gridLayout.setRows(5);
		this.horizontalLayout.setWidth(195, Unit.PIXELS);
		this.horizontalLayout.setHeight(40, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
		this.gridLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setWidth(195, Unit.PIXELS);
		this.horizontalLayout2.setHeight(29, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout2, 0, 1);
		this.gridLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
		this.btnExpandPersonal.setSizeUndefined();
		this.gridLayout.addComponent(this.btnExpandPersonal, 0, 2);
		this.tblEmployee.setSizeFull();
		this.gridLayout.addComponent(this.tblEmployee, 0, 3);
		this.tblTask.setSizeFull();
		this.gridLayout.addComponent(this.tblTask, 0, 4);
		this.gridLayout.setRowExpandRatio(3, 1.0F);
		this.gridLayout.setRowExpandRatio(4, 1.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	
		button.addClickListener(event -> this.button_buttonClick(event));
		button2.addClickListener(event -> this.button2_buttonClick(event));
		btnExpandPersonal.addClickListener(event -> this.btnExpandPersonal_buttonClick(event));
		tblEmployee.addHeaderClickListener(event -> this.tblEmployee_headerClick(event));
	} // </generated-code>


	// <generated-code name="variables">
	private XdevButton button, button2, btnExpandPersonal;
	private XdevTable<Fahrzeug> tblTask;
	private XdevGridLayout gridLayout;
	private XdevHorizontalLayout horizontalLayout, horizontalLayout2;
	private XdevLabel lblWeek, lblWeekInterval, lblYear;
	private XdevTable<Fahrer> tblEmployee;
	private XdevHorizontalSplitPanel horizontalSplitPanel; // </generated-code>


}
