package ch.gabrieltransport.auftragverwaltung.ui;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class UIUpdater {
	
	    

    public UIUpdater(final Runnable uiRunnable)
    {
        UI.getCurrent().access(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    VaadinSession.getCurrent().getLockInstance().lock();
                    uiRunnable.run();
                }
                catch(Throwable e)
                {
                	Notification.show(e.getClass().getSimpleName() + ":" + e.getMessage(), Type.ERROR_MESSAGE);
                    throw(e);
                    
                }
                finally
                {
                    VaadinSession.getCurrent().getLockInstance().unlock();
                }

            }
        });

    }

	
}
