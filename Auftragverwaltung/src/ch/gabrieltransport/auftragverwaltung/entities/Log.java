package ch.gabrieltransport.auftragverwaltung.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import ch.gabrieltransport.auftragverwaltung.dal.LogDAO;
import com.xdev.dal.DAO;
import com.xdev.util.Caption;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Log
 */
@DAO(daoClass = LogDAO.class)
@Caption("{%logmessage}")
@Entity
@Table(name = "log", catalog = "gabrieltransport")
public class Log implements java.io.Serializable {

	private int idlog;
	private String user;
	private String logmessage;
	private LocalDateTime logtime;

	public Log() {
	}

	public Log(String username, String logmessage, LocalDateTime logtime) {
		this.user = username;
		this.logmessage = logmessage;
		this.logtime = logtime;
	}

	@Caption("Idlog")
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idlog", unique = true, nullable = false, columnDefinition = "INT")
	public int getIdlog() {
		return this.idlog;
	}

	public void setIdlog(int idlog) {
		this.idlog = idlog;
	}

	@Caption("User")
	@Column(name = "username", nullable = false, columnDefinition = "VARCHAR", length = 50)
	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Caption("Logmessage")
	@Column(name = "logmessage", nullable = false, columnDefinition = "VARCHAR", length = 512)
	public String getLogmessage() {
		return this.logmessage;
	}

	public void setLogmessage(String logmessage) {
		this.logmessage = logmessage;
	}
	
	@Caption("logtime")
	@Column(name = "logtime", nullable = false, length = 19)
	public LocalDateTime getlogtime() {
		return this.logtime;
	}

	public void setlogtime(LocalDateTime logtime) {
		this.logtime = logtime;
	}

}
