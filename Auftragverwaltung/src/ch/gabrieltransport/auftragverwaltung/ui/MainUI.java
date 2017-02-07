
package ch.gabrieltransport.auftragverwaltung.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.xdev.security.authorization.ui.XdevAuthorizationNavigator;
import com.xdev.ui.XdevGridLayout;
import com.xdev.ui.XdevUI;

import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster;
import ch.gabrieltransport.auftragverwaltung.business.refresher.Broadcaster.BroadcastListener;

@Push
@Theme("Auftragverwaltung")
public class MainUI extends XdevUI implements BroadcastListener {
	private MainView view;
	public MainUI() {
		super();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(VaadinRequest request) {
		this.initUI();
		//this.setPollInterval(1000);
		Broadcaster.register(this);
		view = new MainView();
		this.navigator.addView("home", view);
		UI.getCurrent().setPollInterval( 5000 );
		
	}
	
	
	@Override
	public void detach() {
		Broadcaster.unregister(this);
		super.detach();
	}
	@Override
    public void receiveBroadcast(final String message) {
		//view.receiveBroadcast(message);
		//view.markAsDirty();
		/*this.access(() ->
		view.update());*/
		/*new UIUpdater(new Runnable()
        {
            @Override
            public void run() {
            	view.receiveBroadcast(message);
            }
        });*/
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