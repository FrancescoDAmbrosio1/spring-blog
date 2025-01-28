package it.springBlog.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "commenti")
public class Commento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Testo del commento obbligatorio")
	@Column(name = "testo")
	private String testo;
	
	@Column(name = "gdo")
	private LocalDateTime gdo;
	
	@ManyToOne
	@JoinColumn(name = "autore_id")
	private Utente autore;
	
	@ManyToOne
	@JoinColumn(name = "articolo_id")
	private Articolo articolo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public Articolo getArticolo() {
		return articolo;
	}

	public void setArticolo(Articolo articolo) {
		this.articolo = articolo;
	}

	public LocalDateTime getGdo() {
		return gdo;
	}

	public void setGdo(LocalDateTime gdo) {
		this.gdo = gdo;
	}
	
	
}
