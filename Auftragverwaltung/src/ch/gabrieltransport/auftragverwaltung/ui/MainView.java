
package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.CurrentWeek;
import ch.gabrieltransport.auftragverwaltung.ui.calendar.Week;

public class MainView extends XdevView {

	
	private CurrentWeek currWeek = new CurrentWeek();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
	/**
	 * 
	 */
	public MainView() {
		super();
		this.initUI();
		updateLabels();
		
		
	}
	

	public void updateGuiGenerators(){
			table.refreshRowCache();
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
		this.table = new XdevTable<>();
	
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.button.setIcon(FontAwesome.CARET_LEFT);
		this.button.setCaption("");
		this.button2.setIcon(FontAwesome.CARET_RIGHT);
		this.button2.setCaption("");
		this.horizontalLayout2.setMargin(new MarginInfo(false));
		this.lblWeekInterval.setValue("01.08 - 07.08");
		this.lblYear.setValue("2016");
		this.table.setColumnCollapsingAllowed(true);
		this.table.setContainerDataSource(Fahrzeug.class);
		this.table.addGeneratedColumn("Montag", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Dienstag", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Mittwoch", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Donnerstag", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Freitag", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Samstag", new WeekDayGuiGenerator.Generator());
		this.table.addGeneratedColumn("Sonntag", new WeekDayGuiGenerator.Generator());
		this.table.setVisibleColumns("idfahrzeug", "kennzeichen", "gewicht", "Montag", "Dienstag", "Mittwoch",
				"Donnerstag", "Freitag", "Samstag", "Sonntag");
		this.table.setColumnHeader("idfahrzeug", "#");
		this.table.setColumnWidth("idfahrzeug", 25);
		this.table.setColumnHeader("kennzeichen", "Kennzeichen");
		this.table.setColumnWidth("kennzeichen", 90);
		this.table.setColumnWidth("gewicht", 75);
		this.table.setColumnHeader("Montag", "Montag");
		this.table.setColumnHeader("Dienstag", "Dienstag");
		this.table.setColumnHeader("Mittwoch", "Mittwoch");
		this.table.setColumnHeader("Donnerstag", "Donnerstag");
		this.table.setColumnHeader("Freitag", "Freitag");
		this.table.setColumnHeader("Samstag", "Samstag");
		this.table.setColumnHeader("Sonntag", "Sonntag");
	
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
		this.gridLayout.setRows(3);
		this.horizontalLayout.setWidth(195, Unit.PIXELS);
		this.horizontalLayout.setHeight(40, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout, 0, 0);
		this.gridLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setWidth(195, Unit.PIXELS);
		this.horizontalLayout2.setHeight(29, Unit.PIXELS);
		this.gridLayout.addComponent(this.horizontalLayout2, 0, 1);
		this.gridLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
		this.table.setSizeFull();
		this.gridLayout.addComponent(this.table, 0, 2);
		this.gridLayout.setRowExpandRatio(2, 1.0F);
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	
		button.addClickListener(event -> this.button_buttonClick(event));
		button2.addClickListener(event -> this.button2_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevHorizontalLayout horizontalLayout, horizontalLayout2;
	private XdevLabel lblWeek, lblWeekInterval, lblYear;
	private XdevHorizontalSplitPanel horizontalSplitPanel;
	private XdevButton button, button2;
	private XdevTable<Fahrzeug> table;
	private XdevGridLayout gridLayout; // </generated-code>


}
