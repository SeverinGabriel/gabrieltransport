package ch.gabrieltransport.auftragverwaltung.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import ch.gabrieltransport.auftragverwaltung.dal.AnhaengerDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Anhaenger
 */
@DAO(daoClass = AnhaengerDAO.class)
@Caption("{%kennzeichen}")
@Entity
@Table(name = "anhaenger", catalog = "gabrieltransport", uniqueConstraints = @UniqueConstraint(columnNames = "Nummer"))
public class Anhaenger implements java.io.Serializable {

	private int idanhaenger;
	private Anhaengertyp anhaengertyp;
	private FahrzeugFunktion fahrzeugFunktion;
	private int nummer;
	private String kennzeichen;
	private Integer nutzlast;

	public Anhaenger() {
	}

	public Anhaenger(Anhaengertyp anhaengertyp, int nummer, String kennzeichen) {
		this.anhaengertyp = anhaengertyp;
		this.nummer = nummer;
		this.kennzeichen = kennzeichen;
	}

	public Anhaenger(Anhaengertyp anhaengertyp, FahrzeugFunktion fahrzeugFunktion, int nummer, String kennzeichen,
			Integer nutzlast) {
		this.anhaengertyp = anhaengertyp;
		this.fahrzeugFunktion = fahrzeugFunktion;
		this.nummer = nummer;
		this.kennzeichen = kennzeichen;
		this.nutzlast = nutzlast;
	}

	@Caption("Idanhaenger")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idanhaenger", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdanhaenger() {
		return this.idanhaenger;
	}

	public void setIdanhaenger(int idanhaenger) {
		this.idanhaenger = idanhaenger;
	}

	@Caption("Anhaengertyp")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkTyp", nullable = false, columnDefinition = "INT")
	public Anhaengertyp getAnhaengertyp() {
		return this.anhaengertyp;
	}

	public void setAnhaengertyp(Anhaengertyp anhaengertyp) {
		this.anhaengertyp = anhaengertyp;
	}

	@Caption("FahrzeugFunktion")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fkFunktion", columnDefinition = "INT")
	public FahrzeugFunktion getFahrzeugFunktion() {
		return this.fahrzeugFunktion;
	}

	public void setFahrzeugFunktion(FahrzeugFunktion fahrzeugFunktion) {
		this.fahrzeugFunktion = fahrzeugFunktion;
	}

	@Caption("Nummer")
	@Column(name = "Nummer", unique = true, nullable = false, columnDefinition = "INT")
	public int getNummer() {
		return this.nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	@Caption("Kennzeichen")
	@Column(name = "Kennzeichen", nullable = false, columnDefinition = "VARCHAR", length = 45)
	public String getKennzeichen() {
		return this.kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	@Caption("Nutzlast")
	@Column(name = "Nutzlast", columnDefinition = "INT")
	public Integer getNutzlast() {
		return this.nutzlast;
	}

	public void setNutzlast(Integer nutzlast) {
		this.nutzlast = nutzlast;
	}

}
