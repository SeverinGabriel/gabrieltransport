
package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.entities.Resource;
import ch.gabrieltransport.auftragverwaltung.entities.Role;
import ch.gabrieltransport.auftragverwaltung.entities.User;
import com.xdev.security.authorization.AuthorizationConfiguration;
import com.xdev.security.authorization.AuthorizationConfigurationProvider;
import com.xdev.security.authorization.jpa.JPAAuthorizationConfigurationProvider;

public class GabrielAuthorizationProvider implements AuthorizationConfigurationProvider {
	private static GabrielAuthorizationProvider INSTANCE;

	public static GabrielAuthorizationProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GabrielAuthorizationProvider();
		}

		return INSTANCE;
	}

	private JPAAuthorizationConfigurationProvider provider;

	private GabrielAuthorizationProvider() {
	}

	@Override
	public AuthorizationConfiguration provideConfiguration() {
		if (this.provider == null) {
			this.provider = new JPAAuthorizationConfigurationProvider(User.class, Role.class, Resource.class);
		}

		return this.provider.provideConfiguration();
	}
}
