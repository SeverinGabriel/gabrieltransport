package ch.gabrieltransport.auftragverwaltung.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.xdev.dal.DAOs;
import com.xdev.ui.XdevAbsoluteLayout;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevLabel;
import com.xdev.ui.XdevPopupDateField;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.XdevView;
import com.xdev.ui.entitycomponent.combobox.XdevComboBox;
import com.xdev.ui.entitycomponent.listselect.XdevOptionGroup;
import com.xdev.ui.entitycomponent.listselect.XdevTwinColSelect;
import com.xdev.ui.filter.XdevContainerFilterComponent;
import ch.gabrieltransport.auftragverwaltung.business.facade.AuftragServiceFacade;
import ch.gabrieltransport.auftragverwaltung.dal.AnhaengerDAO;
import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger;
import ch.gabrieltransport.auftragverwaltung.entities.Anhaenger_;
import ch.gabrieltransport.auftragverwaltung.entities.Auftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerauftrag;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug_;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeugauftrag;

public class taskDetail extends XdevView {

	public static class Callback{
		public void onDialogResult(boolean result){}
	}
	/**
	 * 
	 */
	private static final String time_day = "Ganztägig";
	private static final String time_morning = "Vormittag"; 
	private static final String time_afternoon = "Nachmittag";
	private static final String time_specific = "Spezifische Zeit"; 
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	private Auftrag currentTask = new Auftrag();
	private Fahrzeugauftrag fahrzeugAuftrag = new Fahrzeugauftrag();
	private Callback callback;
	private AuftragServiceFacade auftragServiceFacade = new AuftragServiceFacade();
	public taskDetail(Fahrzeug fz) {
		super();
		this.initUI();
		initTrailer(fz);
		optionGroup.addItems(time_day, time_morning, time_afternoon, time_specific);
	}
	public taskDetail(Fahrzeugauftrag auftrag, Callback callback) {
		this(auftrag.getFahrzeug());
		this.callback = callback;
		currentTask = auftrag.getAuftrag();
		txtDescription.setValue(currentTask.getBezeichung());
		this.fahrzeugAuftrag = auftrag;
		setTime(fahrzeugAuftrag.getVonDatum(), fahrzeugAuftrag.getBisDatum());
		fromDate.setValue(Date.from(fahrzeugAuftrag.getVonDatum().atZone(ZoneId.systemDefault()).toInstant()));
		untilDate.setValue(Date.from(fahrzeugAuftrag.getBisDatum().atZone(ZoneId.systemDefault()).toInstant()));
		cmbVehicle.select(fahrzeugAuftrag.getFahrzeug().getIdfahrzeug());
		
		for(Fahrerauftrag fa: currentTask.getFahrerauftrags()){
			for (Iterator i = tsDriver.getItemIds().iterator(); i.hasNext();) {
				Fahrer iid = (Fahrer) i.next();
				if (iid.getIdfahrer() == fa.getFahrer().getIdfahrer()){
					tsDriver.select(iid);
				}
			}
		}
		if(auftrag.getAnhaenger() != null){
			List<Anhaenger> test = (List<Anhaenger>)cmbTrailer.getItemIds();
			for (Anhaenger test2: test) {
				if (test2.getNummer() == auftrag.getAnhaenger().getNummer()){
					cmbTrailer.select(test2);
				}
			}
			//cmbTrailer.select(auftrag.getAnhaenger());
		}
		
	}
	public taskDetail(LocalDateTime ldt, Fahrzeug fz, Callback callback) {
		this(fz);
		this.callback = callback;
		Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		optionGroup.select(time_day);
		fromDate.setValue(date);
		untilDate.setValue(date);
		cmbVehicle.select(fz.getIdfahrzeug());
	}
	
	public void initTrailer(Fahrzeug fz){
		cmbTrailer.removeAllItems();
		if(fz.getAnhaenger()){
			hlTrailer.setVisible(true);
			if(fz.getAnhaengertyp() != null){
				cmbTrailer.getBeanContainerDataSource().addAll(new AnhaengerDAO().getAllbyTyp(fz.getAnhaengertyp()));
			}else{
				cmbTrailer.getBeanContainerDataSource().addAll(new AnhaengerDAO().findAll());
			}
		}else{
			hlTrailer.setVisible(false);
		}
	}

	/**
	 * Event handler delegate method for the {@link XdevOptionGroup}
	 * {@link #optionGroup}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void optionGroup_valueChange(Property.ValueChangeEvent event) {
		if (event.getProperty().getValue() == "Spezifische Zeit"){
			gridLayout2.setVisible(true);
		}
		else {
			gridLayout2.setVisible(false);
		}
	}

	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnCancel}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnCancel_buttonClick(Button.ClickEvent event) {
		((Window)this.getParent()).close();
	}

	/**
	 * Event handler delegate method for the {@link XdevComboBox}
	 * {@link #cmbVehicle}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmbVehicle_valueChange(Property.ValueChangeEvent event) {
		lblWeight.setValue( String.valueOf(cmbVehicle.getSelectedItem().getBean().getNutzlast()));
		lblSign.setValue(cmbVehicle.getSelectedItem().getBean().getKennzeichen());
		initTrailer(cmbVehicle.getSelectedItem().getBean());
	}
	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #button3}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button3_buttonClick(Button.ClickEvent event) {
		auftragServiceFacade.deleteAuftrag(currentTask);
		callback.onDialogResult(true);
		((Window)this.getParent()).close();
		
	}
	/**
	 * Event handler delegate method for the {@link XdevButton}
	 * {@link #btnSave}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void btnSave_buttonClick(Button.ClickEvent event) {
		currentTask.setBezeichung(txtDescription.getValue());
		
		LocalDate ldUntil = untilDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ldFrom = fromDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(ldFrom.isBefore(ldUntil) || ldFrom.isEqual(ldUntil)){
			LocalDateTime ldt = LocalDateTime.of(ldUntil, getTime(false));
			currentTask.setBisDatum(ldt);
			ldt = LocalDateTime.of(ldFrom, getTime(true));
			currentTask.setVonDatum(ldt);
			
			List<Fahrerauftrag> driverList = new ArrayList<Fahrerauftrag>();
			for(BeanItem<Fahrer> bean : tsDriver.getSelectedItems()){
		    	Fahrerauftrag faA = new Fahrerauftrag();
		    	faA.setAuftrag(currentTask);
		    	faA.setBisDatum(currentTask.getBisDatum());
		    	faA.setVonDatum(currentTask.getVonDatum());
		    	faA.setFahrer(bean.getBean());
				driverList.add(faA);
			}
			List<Fahrzeugauftrag> fahrzeugauftraege = 
					Arrays.asList(auftragServiceFacade.createTaskFromVehicle(currentTask, cmbVehicle.getSelectedItem().getBean()));
			for (Fahrzeugauftrag fa: fahrzeugauftraege) {
				if(cmbTrailer.getValue() != null){
					fa.setAnhaenger((Anhaenger)cmbTrailer.getValue());
				}else{
					fa.setAnhaenger(null);
				}
			}
			
			if(currentTask.getIdAuftrag() == 0){
				auftragServiceFacade.persistAuftrag(currentTask, fahrzeugauftraege, driverList);
			}else{
				auftragServiceFacade.mergeAuftrag(currentTask, fahrzeugauftraege, driverList);
			}
			callback.onDialogResult(true);
			((Window)this.getParent()).close();
		}else{
			new Notification("Eingabefehler",
				    "<br/>Enddatum vor Startdatum",
				    Notification.TYPE_ERROR_MESSAGE, true)
				    .show(Page.getCurrent());
		}
		
	}
	
	private LocalTime getTime(boolean start){
		if(optionGroup.getValue() != null){
			if(optionGroup.getValue().toString() == time_day){
				return LocalTime.parse("00:00");
			}
			else if(optionGroup.getValue().toString() == time_morning){
				return (start ? LocalTime.parse("08:00") : LocalTime.parse("12:00"));
				
			}
			else if(optionGroup.getValue().toString() == time_afternoon){
				return (start ? LocalTime.parse("13:00") : LocalTime.parse("18:00"));
			}
			else {
				return (start ? LocalTime.parse(lblTimeFrom.getValue()) : LocalTime.parse(txtTimeUntil.getValue()));
				
			}
		}else{
			return LocalTime.parse("00:00");
		}
	}
	private void setTime(LocalDateTime start, LocalDateTime end){
		boolean setSpecific = false;
		if(start.getHour() == 0 && end.getHour() == 0){
			optionGroup.setValue(time_day);
			setSpecific = true;
		}
		else if(start.getDayOfYear() == end.getDayOfYear()){
			if(start.getHour() == 8  && end.getHour() == 12){
				optionGroup.setValue(time_morning);
				setSpecific = true;
			}
			else if(start.getHour() == 13  && end.getHour() == 18){
				optionGroup.setValue(time_afternoon);
				setSpecific = true;
			}
		}
		if(!setSpecific){
			optionGroup.setValue(time_specific);
			txtTimeUntil.setValue(end.format(formatter));
			lblTimeFrom.setValue(start.format(formatter));
		}
		
	}
	
	/**
	 * Event handler delegate method for the {@link XdevButton} {@link #button}.
	 *
	 * @see Button.ClickListener#buttonClick(Button.ClickEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_buttonClick(Button.ClickEvent event) {
	
	}
	
	/**
	 * Event handler delegate method for the {@link XdevPopupDateField}
	 * {@link #fromDate}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void fromDate_valueChange(Property.ValueChangeEvent event) {
		checkDates();
	}
	
	/**
	 * Event handler delegate method for the {@link XdevPopupDateField}
	 * {@link #untilDate}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void untilDate_valueChange(Property.ValueChangeEvent event) {
		checkDates();
	}
	
	private void checkDates(){
		if(untilDate.getValue() != null && fromDate.getValue() != null){
			LocalDate ldUntil = untilDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate ldFrom = fromDate.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if(ldFrom.getDayOfYear() == ldUntil.getDayOfYear()){
				setupOptionGroup(true);
			}else{
				setupOptionGroup(false);
			}
		}
	}
	
	private void setupOptionGroup(boolean full){
		String selected = "";
		if (optionGroup.getValue() != null){
			selected = optionGroup.getValue().toString();
		}
		optionGroup.removeAllItems();
		if(full){
			optionGroup.addItems(time_day, time_morning, time_afternoon, time_specific);
		}else{
			optionGroup.addItems(time_day, time_specific);
		}
		if(selected != ""){
			optionGroup.setValue(selected);;
		}
	}
	
	
	/**
	 * Event handler delegate method for the {@link XdevComboBox} {@link #cmbTrailer}.
	 *
	 * @see Property.ValueChangeListener#valueChange(Property.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void cmbTrailer_valueChange(Property.ValueChangeEvent event) {
		if(cmbTrailer.getValue() != null){
			lblTrailerSign.setValue(cmbTrailer.getSelectedItem().getBean().getKennzeichen());
			lblTrailerWeight.setValue(String.valueOf(cmbTrailer.getSelectedItem().getBean().getNutzlast()));
			lblTrailerTyp.setValue(cmbTrailer.getSelectedItem().getBean().getAnhaengertyp().getBeschreibung());
		}else{
			lblTrailerSign.setValue("");
			lblTrailerWeight.setValue("");
			lblTrailerTyp.setValue("");
		}
	}
	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated by
	 * the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.absoluteLayout = new XdevAbsoluteLayout();
		this.verticalLayout = new XdevVerticalLayout();
		this.horizontalLayout4 = new XdevHorizontalLayout();
		this.lblMission = new XdevLabel();
		this.button3 = new XdevButton();
		this.horizontalLayout = new XdevHorizontalLayout();
		this.lblDescription = new XdevLabel();
		this.txtDescription = new XdevTextField();
		this.gridLayout = new XdevGridLayout();
		this.label3 = new XdevLabel();
		this.fromDate = new XdevPopupDateField();
		this.label4 = new XdevLabel();
		this.untilDate = new XdevPopupDateField();
		this.horizontalLayout3 = new XdevHorizontalLayout();
		this.optionGroup = new XdevOptionGroup<>();
		this.gridLayout2 = new XdevGridLayout();
		this.label10 = new XdevLabel();
		this.label8 = new XdevLabel();
		this.lblTimeFrom = new XdevTextField();
		this.label9 = new XdevLabel();
		this.txtTimeUntil = new XdevTextField();
		this.lblVehicle = new XdevLabel();
		this.horizontalLayout2 = new XdevHorizontalLayout();
		this.cmbVehicle = new XdevComboBox<>();
		this.lblSign = new XdevLabel();
		this.lblWeight = new XdevLabel();
		this.hlTrailer = new XdevHorizontalLayout();
		this.cmbTrailer = new XdevComboBox<>();
		this.lblTrailerSign = new XdevLabel();
		this.lblTrailerWeight = new XdevLabel();
		this.lblTrailerTyp = new XdevLabel();
		this.lblDriver = new XdevLabel();
		this.horizontalLayout6 = new XdevHorizontalLayout();
		this.containerFilterComponent = new XdevContainerFilterComponent();
		this.button = new XdevButton();
		this.horizontalLayout5 = new XdevHorizontalLayout();
		this.tsDriver = new XdevTwinColSelect<>();
		this.btnSave = new XdevButton();
		this.btnCancel = new XdevButton();
	
		this.verticalLayout.setMargin(new MarginInfo(true, false, false, false));
		this.lblMission.setValue("Auftrag");
		this.button3.setCaption("Delete");
		this.horizontalLayout.setMargin(new MarginInfo(false, true, true, true));
		this.lblDescription.setValue("Bezeichnung");
		this.gridLayout.setMargin(new MarginInfo(false));
		this.label3.setValue("Von");
		this.label4.setValue("Bis");
		this.horizontalLayout3.setMargin(new MarginInfo(false, true, true, true));
		this.gridLayout2.setMargin(new MarginInfo(false));
		this.gridLayout2.setVisible(false);
		this.label10.setValue("Zeit (hh:mm)");
		this.label8.setValue("Von");
		this.label9.setValue("Bis");
		this.lblVehicle.setValue("Fahrzeug");
		this.horizontalLayout2.setMargin(new MarginInfo(false, true, true, true));
		this.cmbVehicle.setItemCaptionFromAnnotation(false);
		this.cmbVehicle.setNullSelectionAllowed(false);
		this.cmbVehicle.setContainerDataSource(Fahrzeug.class);
		this.cmbVehicle.setItemCaptionPropertyId(Fahrzeug_.nummer.getName());
		this.lblSign.setValue("Kennzeichen");
		this.lblWeight.setValue("Gewicht");
		this.hlTrailer.setMargin(new MarginInfo(false, true, true, true));
		this.hlTrailer.setVisible(false);
		this.cmbTrailer.setInvalidAllowed(false);
		this.cmbTrailer.setItemCaptionFromAnnotation(false);
		this.cmbTrailer.setContainerDataSource(Anhaenger.class, false);
		this.cmbTrailer.setItemCaptionPropertyId(Anhaenger_.nummer.getName());
		this.lblTrailerSign.setValue("Label");
		this.lblTrailerWeight.setValue("Label");
		this.lblTrailerTyp.setValue("Label");
		this.lblDriver.setValue("Fahrer");
		this.button.setCaption("Detailplanung");
		this.horizontalLayout5.setMargin(new MarginInfo(false));
		this.tsDriver.setItemCaptionFromAnnotation(false);
		this.tsDriver.setRightColumnCaption("zugewiesen");
		this.tsDriver.setLeftColumnCaption("verfügbar");
		this.tsDriver.setRows(4);
		this.tsDriver.setContainerDataSource(Fahrer.class, DAOs.get(FahrerDAO.class).findAll());
		this.tsDriver.setItemCaptionPropertyId(Fahrer_.nachname.getName());
		this.btnSave.setCaption("OK");
		this.btnCancel.setCaption("Abbrechen");
	
		this.containerFilterComponent.setContainer(this.tsDriver.getBeanContainerDataSource(), "nachname", "vorname");
		this.containerFilterComponent.setSearchableProperties("nachname", "vorname");
	
		this.lblMission.setWidth(100, Unit.PERCENTAGE);
		this.lblMission.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout4.addComponent(this.lblMission);
		this.horizontalLayout4.setComponentAlignment(this.lblMission, Alignment.BOTTOM_LEFT);
		this.horizontalLayout4.setExpandRatio(this.lblMission, 60.0F);
		this.button3.setWidth(-1, Unit.PIXELS);
		this.button3.setHeight(30, Unit.PIXELS);
		this.horizontalLayout4.addComponent(this.button3);
		this.horizontalLayout4.setComponentAlignment(this.button3, Alignment.BOTTOM_LEFT);
		this.gridLayout.setColumns(2);
		this.gridLayout.setRows(3);
		this.label3.setSizeUndefined();
		this.gridLayout.addComponent(this.label3, 0, 0);
		this.fromDate.setWidth(100, Unit.PERCENTAGE);
		this.fromDate.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.fromDate, 1, 0);
		this.label4.setSizeUndefined();
		this.gridLayout.addComponent(this.label4, 0, 1);
		this.untilDate.setWidth(100, Unit.PERCENTAGE);
		this.untilDate.setHeight(-1, Unit.PIXELS);
		this.gridLayout.addComponent(this.untilDate, 1, 1);
		this.gridLayout.setColumnExpandRatio(1, 10.0F);
		final CustomComponent gridLayout_vSpacer = new CustomComponent();
		gridLayout_vSpacer.setSizeFull();
		this.gridLayout.addComponent(gridLayout_vSpacer, 0, 2, 1, 2);
		this.gridLayout.setRowExpandRatio(2, 1.0F);
		this.lblDescription.setSizeUndefined();
		this.horizontalLayout.addComponent(this.lblDescription);
		this.horizontalLayout.setComponentAlignment(this.lblDescription, Alignment.TOP_CENTER);
		this.txtDescription.setWidth(100, Unit.PERCENTAGE);
		this.txtDescription.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout.addComponent(this.txtDescription);
		this.horizontalLayout.setComponentAlignment(this.txtDescription, Alignment.TOP_CENTER);
		this.horizontalLayout.setExpandRatio(this.txtDescription, 10.0F);
		this.gridLayout.setSizeFull();
		this.horizontalLayout.addComponent(this.gridLayout);
		this.horizontalLayout.setComponentAlignment(this.gridLayout, Alignment.MIDDLE_RIGHT);
		this.horizontalLayout.setExpandRatio(this.gridLayout, 10.0F);
		this.gridLayout2.setColumns(2);
		this.gridLayout2.setRows(4);
		this.label10.setSizeUndefined();
		this.gridLayout2.addComponent(this.label10, 0, 0);
		this.label8.setSizeUndefined();
		this.gridLayout2.addComponent(this.label8, 0, 1);
		this.lblTimeFrom.setWidth(70, Unit.PERCENTAGE);
		this.lblTimeFrom.setHeight(-1, Unit.PIXELS);
		this.gridLayout2.addComponent(this.lblTimeFrom, 1, 1);
		this.label9.setSizeUndefined();
		this.gridLayout2.addComponent(this.label9, 0, 2);
		this.txtTimeUntil.setSizeUndefined();
		this.gridLayout2.addComponent(this.txtTimeUntil, 1, 2);
		this.gridLayout2.setColumnExpandRatio(1, 10.0F);
		final CustomComponent gridLayout2_vSpacer = new CustomComponent();
		gridLayout2_vSpacer.setSizeFull();
		this.gridLayout2.addComponent(gridLayout2_vSpacer, 0, 3, 1, 3);
		this.gridLayout2.setRowExpandRatio(3, 1.0F);
		this.optionGroup.setSizeUndefined();
		this.horizontalLayout3.addComponent(this.optionGroup);
		this.horizontalLayout3.setExpandRatio(this.optionGroup, 20.0F);
		this.gridLayout2.setWidth(100, Unit.PERCENTAGE);
		this.gridLayout2.setHeight(90, Unit.PERCENTAGE);
		this.horizontalLayout3.addComponent(this.gridLayout2);
		this.horizontalLayout3.setExpandRatio(this.gridLayout2, 30.0F);
		this.cmbVehicle.setWidth(100, Unit.PERCENTAGE);
		this.cmbVehicle.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout2.addComponent(this.cmbVehicle);
		this.horizontalLayout2.setComponentAlignment(this.cmbVehicle, Alignment.MIDDLE_LEFT);
		this.horizontalLayout2.setExpandRatio(this.cmbVehicle, 30.0F);
		this.lblSign.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblSign);
		this.horizontalLayout2.setComponentAlignment(this.lblSign, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.lblSign, 30.0F);
		this.lblWeight.setSizeUndefined();
		this.horizontalLayout2.addComponent(this.lblWeight);
		this.horizontalLayout2.setComponentAlignment(this.lblWeight, Alignment.MIDDLE_CENTER);
		this.horizontalLayout2.setExpandRatio(this.lblWeight, 30.0F);
		this.cmbTrailer.setSizeUndefined();
		this.hlTrailer.addComponent(this.cmbTrailer);
		this.hlTrailer.setComponentAlignment(this.cmbTrailer, Alignment.MIDDLE_CENTER);
		this.hlTrailer.setExpandRatio(this.cmbTrailer, 30.0F);
		this.lblTrailerSign.setSizeUndefined();
		this.hlTrailer.addComponent(this.lblTrailerSign);
		this.hlTrailer.setComponentAlignment(this.lblTrailerSign, Alignment.MIDDLE_CENTER);
		this.hlTrailer.setExpandRatio(this.lblTrailerSign, 20.0F);
		this.lblTrailerWeight.setSizeUndefined();
		this.hlTrailer.addComponent(this.lblTrailerWeight);
		this.hlTrailer.setComponentAlignment(this.lblTrailerWeight, Alignment.MIDDLE_CENTER);
		this.hlTrailer.setExpandRatio(this.lblTrailerWeight, 20.0F);
		this.lblTrailerTyp.setSizeUndefined();
		this.hlTrailer.addComponent(this.lblTrailerTyp);
		this.hlTrailer.setComponentAlignment(this.lblTrailerTyp, Alignment.MIDDLE_CENTER);
		this.hlTrailer.setExpandRatio(this.lblTrailerTyp, 20.0F);
		this.containerFilterComponent.setWidth(100, Unit.PERCENTAGE);
		this.containerFilterComponent.setHeight(-1, Unit.PIXELS);
		this.horizontalLayout6.addComponent(this.containerFilterComponent);
		this.horizontalLayout6.setComponentAlignment(this.containerFilterComponent, Alignment.MIDDLE_CENTER);
		this.horizontalLayout6.setExpandRatio(this.containerFilterComponent, 100.0F);
		this.button.setSizeUndefined();
		this.horizontalLayout6.addComponent(this.button);
		this.horizontalLayout6.setComponentAlignment(this.button, Alignment.MIDDLE_CENTER);
		this.tsDriver.setSizeUndefined();
		this.horizontalLayout5.addComponent(this.tsDriver);
		this.horizontalLayout5.setComponentAlignment(this.tsDriver, Alignment.MIDDLE_CENTER);
		final CustomComponent horizontalLayout5_spacer = new CustomComponent();
		horizontalLayout5_spacer.setSizeFull();
		this.horizontalLayout5.addComponent(horizontalLayout5_spacer);
		this.horizontalLayout5.setExpandRatio(horizontalLayout5_spacer, 1.0F);
		this.horizontalLayout4.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout4.setHeight(100, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout4);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout4, Alignment.MIDDLE_CENTER);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.horizontalLayout, 10.0F);
		this.horizontalLayout3.setSizeFull();
		this.verticalLayout.addComponent(this.horizontalLayout3);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout3, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.horizontalLayout3, 20.0F);
		this.lblVehicle.setSizeUndefined();
		this.verticalLayout.addComponent(this.lblVehicle);
		this.verticalLayout.setComponentAlignment(this.lblVehicle, Alignment.BOTTOM_LEFT);
		this.verticalLayout.setExpandRatio(this.lblVehicle, 3.0F);
		this.horizontalLayout2.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout2.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout2);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout2, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.horizontalLayout2, 10.0F);
		this.hlTrailer.setSizeFull();
		this.verticalLayout.addComponent(this.hlTrailer);
		this.verticalLayout.setComponentAlignment(this.hlTrailer, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setExpandRatio(this.hlTrailer, 10.0F);
		this.lblDriver.setSizeUndefined();
		this.verticalLayout.addComponent(this.lblDriver);
		this.verticalLayout.setComponentAlignment(this.lblDriver, Alignment.BOTTOM_LEFT);
		this.verticalLayout.setExpandRatio(this.lblDriver, 3.0F);
		this.horizontalLayout6.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout6.setHeight(100, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout6);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout6, Alignment.MIDDLE_CENTER);
		this.horizontalLayout5.setWidth(430, Unit.PIXELS);
		this.horizontalLayout5.setHeight(181, Unit.PIXELS);
		this.verticalLayout.addComponent(this.horizontalLayout5);
		this.verticalLayout.setComponentAlignment(this.horizontalLayout5, Alignment.MIDDLE_CENTER);
		this.verticalLayout.setWidth(100, Unit.PERCENTAGE);
		this.verticalLayout.setHeight(93, Unit.PERCENTAGE);
		this.absoluteLayout.addComponent(this.verticalLayout, "left:29px; top:0px");
		this.absoluteLayout.addComponent(this.btnSave, "left:561px; top:985px");
		this.absoluteLayout.addComponent(this.btnCancel, "left:640px; top:985px");
		this.absoluteLayout.setWidth(800, Unit.PIXELS);
		this.absoluteLayout.setHeight(1050, Unit.PIXELS);
		this.setContent(this.absoluteLayout);
		this.setWidth(800, Unit.PIXELS);
		this.setHeight(1050, Unit.PIXELS);
	
		this.button3.addClickListener(event -> this.button3_buttonClick(event));
		this.fromDate.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.fromDate_valueChange(event);
			}
		});
		this.untilDate.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.untilDate_valueChange(event);
			}
		});
		this.optionGroup.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.optionGroup_valueChange(event);
			}
		});
		this.cmbVehicle.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.cmbVehicle_valueChange(event);
			}
		});
		this.cmbTrailer.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				taskDetail.this.cmbTrailer_valueChange(event);
			}
		});
		this.button.addClickListener(event -> this.button_buttonClick(event));
		this.btnSave.addClickListener(event -> this.btnSave_buttonClick(event));
		this.btnCancel.addClickListener(event -> this.btnCancel_buttonClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevLabel lblMission, lblDescription, label3, label4, label10, label8, label9, lblVehicle, lblSign, lblWeight,
			lblTrailerSign, lblTrailerWeight, lblTrailerTyp, lblDriver;
	private XdevButton button3, button, btnSave, btnCancel;
	private XdevGridLayout gridLayout, gridLayout2;
	private XdevOptionGroup<String> optionGroup;
	private XdevComboBox<Fahrzeug> cmbVehicle;
	private XdevContainerFilterComponent containerFilterComponent;
	private XdevComboBox<Anhaenger> cmbTrailer;
	private XdevTwinColSelect<Fahrer> tsDriver;
	private XdevHorizontalLayout horizontalLayout4, horizontalLayout, horizontalLayout3, horizontalLayout2, hlTrailer,
			horizontalLayout6, horizontalLayout5;
	private XdevAbsoluteLayout absoluteLayout;
	private XdevPopupDateField fromDate, untilDate;
	private XdevTextField txtDescription, lblTimeFrom, txtTimeUntil;
	private XdevVerticalLayout verticalLayout;
	// </generated-code>


}
