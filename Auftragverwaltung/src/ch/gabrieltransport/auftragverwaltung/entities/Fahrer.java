package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Fahrer
 */
@DAO(daoClass = FahrerDAO.class)
@Caption("{%nachname}")
@Entity
@Table(name = "fahrer", catalog = "gabrieltransport")
public class Fahrer implements java.io.Serializable {

	private int idfahrer;
	private String nachname;
	private String vorname;
	private String telefon;
	private Set<Fahrerfunktionmap> fahrerfunktionmaps = new HashSet<Fahrerfunktionmap>(0);

	public Fahrer() {
	}

	public Fahrer(String nachname, String vorname) {
		this.nachname = nachname;
		this.vorname = vorname;
	}

	public Fahrer(String nachname, String vorname, String telefon, Set<Fahrerfunktionmap> fahrerfunktionmaps) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.telefon = telefon;
		this.fahrerfunktionmaps = fahrerfunktionmaps;
	}

	@Caption("Idfahrer")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrer", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdfahrer() {
		return this.idfahrer;
	}

	public void setIdfahrer(int idfahrer) {
		this.idfahrer = idfahrer;
	}

	@Caption("Nachname")
	@Column(name = "Nachname", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Caption("Vorname")
	@Column(name = "Vorname", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	@Caption("Telefon")
	@Column(name = "Telefon", columnDefinition = "VARCHAR", length = 45)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Caption("Fahrerfunktionmaps")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "fahrer")
	public Set<Fahrerfunktionmap> getFahrerfunktionmaps() {
		return this.fahrerfunktionmaps;
	}

	public void setFahrerfunktionmaps(Set<Fahrerfunktionmap> fahrerfunktionmaps) {
		this.fahrerfunktionmaps = fahrerfunktionmaps;
	}

}
