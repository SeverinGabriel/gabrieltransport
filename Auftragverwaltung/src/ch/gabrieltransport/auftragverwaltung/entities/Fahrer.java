package ch.gabrieltransport.auftragverwaltung.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xdev.dal.DAO;
import com.xdev.util.Caption;

import ch.gabrieltransport.auftragverwaltung.dal.FahrerDAO;

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
	private Set<Fahrerauftrag> fahrerauftrags = new HashSet<Fahrerauftrag>(0);

	public Fahrer() {
	}

	public Fahrer(String nachname, String vorname) {
		this.nachname = nachname;
		this.vorname = vorname;
	}

	public Fahrer(String nachname, String vorname, String telefon, Set<Fahrerauftrag> fahrerauftrags) {
		this.nachname = nachname;
		this.vorname = vorname;
		this.telefon = telefon;
		this.fahrerauftrags = fahrerauftrags;
	}

	@Caption("Idfahrer")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idfahrer", unique = true, nullable = false)
	public int getIdfahrer() {
		return this.idfahrer;
	}

	public void setIdfahrer(int idfahrer) {
		this.idfahrer = idfahrer;
	}

	@Caption("Nachname")
	@Column(name = "Nachname", nullable = false, length = 45)
	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Caption("Vorname")
	@Column(name = "Vorname", nullable = false, length = 45)
	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	@Caption("Telefon")
	@Column(name = "Telefon", length = 45)
	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	@Caption("Fahrerauftrags")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fahrer")
	public Set<Fahrerauftrag> getFahrerauftrags() {
		return this.fahrerauftrags;
	}

	public void setFahrerauftrags(Set<Fahrerauftrag> fahrerauftrags) {
		this.fahrerauftrags = fahrerauftrags;
	}

}
