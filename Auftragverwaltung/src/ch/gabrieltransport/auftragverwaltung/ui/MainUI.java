
package ch.gabrieltransport.auftragverwaltung.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.xdev.security.authorization.ui.XdevAuthorizationNavigator;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevUI;

@Push
@Theme("Auftragverwaltung")
public class MainUI extends XdevUI {
	public MainUI() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(VaadinRequest request) {
		this.initUI();
	}

	/*
	 * WARNING: Do NOT edit!<br>The content of this method is always regenerated
	 * by the UI designer.
	 */
	// <generated-code name="initUI">
	private void initUI() {
		this.gridLayout = new XdevGridLayout();
		this.navigator = new XdevAuthorizationNavigator(this, this);
	
		this.navigator.setRedirectViewName("home");
		this.navigator.addView("", LoginAuthView.class);
		this.navigator.addView("home", MainView.class);
	
		this.gridLayout.setSizeFull();
		this.setContent(this.gridLayout);
		this.setSizeFull();
	} // </generated-code>

	// <generated-code name="variables">
	private XdevAuthorizationNavigator navigator;
	private XdevGridLayout gridLayout; // </generated-code>
	
}