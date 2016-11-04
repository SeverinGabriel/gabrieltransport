
package ch.gabrieltransport.auftragverwaltung.business;

import ch.gabrieltransport.auftragverwaltung.entities.User;
import com.xdev.security.authentication.Authenticator;
import com.xdev.security.authentication.AuthenticatorProvider;
import com.xdev.security.authentication.CredentialsUsernamePassword;
import com.xdev.security.authentication.jpa.JPAAuthenticator;
import com.xdev.security.authentication.jpa.HashStrategy.SHA2;

public class GabrielAuthenticatorProvider implements AuthenticatorProvider<CredentialsUsernamePassword, CredentialsUsernamePassword> {
	private static GabrielAuthenticatorProvider INSTANCE;

	public static GabrielAuthenticatorProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GabrielAuthenticatorProvider();
		}

		return INSTANCE;
	}

	private JPAAuthenticator authenticator;

	private GabrielAuthenticatorProvider() {
	}

	@Override
	public Authenticator<CredentialsUsernamePassword, CredentialsUsernamePassword> provideAuthenticator() {
		if (this.authenticator == null) {
			this.authenticator = new JPAAuthenticator(User.class);
			this.authenticator.setHashStrategy(new SHA2());
		}

		return this.authenticator;
	}
}
