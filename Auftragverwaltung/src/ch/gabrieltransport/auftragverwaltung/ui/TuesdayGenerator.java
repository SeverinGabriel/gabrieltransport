
package ch.gabrieltransport.auftragverwaltung.ui;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.xdev.ui.XdevButton;
import com.xdev.ui.XdevHorizontalLayout;
import com.xdev.ui.XdevHorizontalSplitPanel;
import com.xdev.ui.XdevTextField;
import com.xdev.ui.XdevVerticalLayout;
import com.xdev.ui.entitycomponent.table.XdevTable;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrzeug;

public class TuesdayGenerator extends XdevHorizontalLayout {

	public static class Generator implements ColumnGenerator {
		@Override
		public Object generateCell(Table table, Object itemId, Object columnId) {

			return new TuesdayGenerator(table, itemId, columnId);
		}
	}

	private final Table customizedTable;
	private final Object itemId;
	private final Object columnId;

	private TuesdayGenerator(Table customizedTable, Object itemId, Object columnId) {
		super();

		this.customizedTable = customizedTable;
		this.itemId = itemId;
		this.columnId = columnId;

		this.initUI();
	}

	public Table getTable() {
		return customizedTable;
	}

	public Object getItemId() {
		return itemId;
	}

	public Object getColumnId() {
		return columnId;
	}

	@SuppressWarnings("unchecked")
	public Fahrzeug getBean() {
		return ((XdevTable<Fahrzeug>) getTable()).getBeanContainerDataSource().getItem(getItemId()).getBean();
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
		this.horizontalLayout = new XdevHorizontalLayout();
		this.horizontalSplitPanel = new XdevHorizontalSplitPanel();
		this.verticalLayout = new XdevVerticalLayout();
		this.textField = new XdevTextField();
		this.textField2 = new XdevTextField();
		this.button = new XdevButton();
		this.verticalLayout2 = new XdevVerticalLayout();
		this.textField3 = new XdevTextField();
		this.textField4 = new XdevTextField();
		this.button2 = new XdevButton();
	
		this.setSpacing(false);
		this.setMargin(new MarginInfo(false));
		this.horizontalLayout.setMargin(new MarginInfo(false));
		this.horizontalSplitPanel.setReadOnly(true);
		this.verticalLayout.setMargin(new MarginInfo(false));
		this.textField.setValue("Auftrag 1");
		this.textField.setReadOnly(true);
		this.textField.addStyleName("FontAwesome");
		this.textField.addStyleName("borderless");
		this.textField2.setValue("Auftrag 2");
		this.textField2.setReadOnly(true);
		this.textField2.addStyleName("borderless");
		this.button.setCaption("");
		this.button.addStyleName("borderless");
		this.verticalLayout2.setMargin(new MarginInfo(false));
		this.textField3.setValue("Auftrag Nachmittag");
		this.textField3.setReadOnly(true);
		this.textField3.addStyleName("FontAwesome");
		this.textField3.addStyleName("borderless");
		this.textField3.addStyleName("popupContent");
		this.textField4.setValue("Auftrag 2");
		this.textField4.setReadOnly(true);
		this.textField4.addStyleName("borderless");
		this.button2.setCaption("");
		this.button2.addStyleName("borderless");
	
		this.textField.setWidth(100, Unit.PERCENTAGE);
		this.textField.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.textField);
		this.verticalLayout.setComponentAlignment(this.textField, Alignment.MIDDLE_CENTER);
		this.textField2.setWidth(100, Unit.PERCENTAGE);
		this.textField2.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.textField2);
		this.verticalLayout.setComponentAlignment(this.textField2, Alignment.MIDDLE_CENTER);
		this.button.setWidth(100, Unit.PERCENTAGE);
		this.button.setHeight(-1, Unit.PIXELS);
		this.verticalLayout.addComponent(this.button);
		this.verticalLayout.setComponentAlignment(this.button, Alignment.MIDDLE_LEFT);
		CustomComponent verticalLayout_spacer = new CustomComponent();
		verticalLayout_spacer.setSizeFull();
		this.verticalLayout.addComponent(verticalLayout_spacer);
		this.verticalLayout.setExpandRatio(verticalLayout_spacer, 1.0F);
		this.textField3.setWidth(100, Unit.PERCENTAGE);
		this.textField3.setHeight(-1, Unit.PIXELS);
		this.verticalLayout2.addComponent(this.textField3);
		this.verticalLayout2.setComponentAlignment(this.textField3, Alignment.MIDDLE_CENTER);
		this.textField4.setWidth(100, Unit.PERCENTAGE);
		this.textField4.setHeight(-1, Unit.PIXELS);
		this.verticalLayout2.addComponent(this.textField4);
		this.verticalLayout2.setComponentAlignment(this.textField4, Alignment.MIDDLE_CENTER);
		this.button2.setWidth(100, Unit.PERCENTAGE);
		this.button2.setHeight(-1, Unit.PIXELS);
		this.verticalLayout2.addComponent(this.button2);
		this.verticalLayout2.setComponentAlignment(this.button2, Alignment.MIDDLE_LEFT);
		CustomComponent verticalLayout2_spacer = new CustomComponent();
		verticalLayout2_spacer.setSizeFull();
		this.verticalLayout2.addComponent(verticalLayout2_spacer);
		this.verticalLayout2.setExpandRatio(verticalLayout2_spacer, 1.0F);
		this.verticalLayout.setSizeFull();
		this.horizontalSplitPanel.setFirstComponent(this.verticalLayout);
		this.verticalLayout2.setSizeFull();
		this.horizontalSplitPanel.setSecondComponent(this.verticalLayout2);
		this.horizontalSplitPanel.setSplitPosition(50, Unit.PERCENTAGE);
		this.horizontalSplitPanel.setSizeFull();
		this.horizontalLayout.addComponent(this.horizontalSplitPanel);
		this.horizontalLayout.setComponentAlignment(this.horizontalSplitPanel, Alignment.MIDDLE_CENTER);
		this.horizontalLayout.setExpandRatio(this.horizontalSplitPanel, 1.0F);
		this.horizontalLayout.setWidth(100, Unit.PERCENTAGE);
		this.horizontalLayout.setHeight(92, Unit.PIXELS);
		this.addComponent(this.horizontalLayout);
		this.setComponentAlignment(this.horizontalLayout, Alignment.MIDDLE_CENTER);
		this.setExpandRatio(this.horizontalLayout, 0.1F);
		this.setWidth(200, Unit.PIXELS);
		this.setHeight(92, Unit.PIXELS);
	
		this.addLayoutClickListener(event -> this.this_layoutClick(event));
	} // </generated-code>

	// <generated-code name="variables">
	private XdevHorizontalLayout horizontalLayout;
	private XdevVerticalLayout verticalLayout, verticalLayout2;
	private XdevHorizontalSplitPanel horizontalSplitPanel;
	private XdevTextField textField, textField2, textField3, textField4;
	private XdevButton button, button2; // </generated-code>


}
