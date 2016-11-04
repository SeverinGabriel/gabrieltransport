
package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.entities.Resource;
import ch.gabrieltransport.auftragverwaltung.entities.Role;
import ch.gabrieltransport.auftragverwaltung.entities.User;
import com.xdev.security.authorization.AuthorizationConfiguration;
import com.xdev.security.authorization.AuthorizationConfigurationProvider;
import com.xdev.security.authorization.jpa.JPAAuthorizationConfigurationProvider;

public class GabrielAuthorization implements AuthorizationConfigurationProvider {
	private static GabrielAuthorization INSTANCE;

	public static GabrielAuthorization getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GabrielAuthorization();
		}

		return INSTANCE;
	}

	private JPAAuthorizationConfigurationProvider provider;

	private GabrielAuthorization() {
	}

	@Override
	public AuthorizationConfiguration provideConfiguration() {
		if (this.provider == null) {
			this.provider = new JPAAuthorizationConfigurationProvider(User.class, Role.class, Resource.class);
		}

		return this.provider.provideConfiguration();
	}
}
