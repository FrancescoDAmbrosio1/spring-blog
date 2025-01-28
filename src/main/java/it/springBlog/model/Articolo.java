//Gli articoli avranno un titolo, un contenuto, l'autore (dato dalla relazione con
//l'utente che lo andrà a scrivere), una categoria selezionabile tra quelle inserite
//nel db ed un'immagine.

package it.springBlog.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
@Table(name = "articolo")
public class Articolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Inserimento del titolo obbligatorio")
	@Column(name = "titolo", nullable = false)
	private String titolo;
	
	@NotBlank(message = "Inserimento del contenuto obbligatorio")
	@Column(name = "contenuto", columnDefinition="longtext", nullable = false)
	private String contenuto;
	
	@Column(name = "gdo")
	private LocalDateTime gdo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "autore_id")
	private Utente autore;
	
	@Column(name = "categoria")
	private String categoria;
	
	@Column(name = "url-immagine")
	private String urlImmagine;
	
	
	@OneToMany(mappedBy = "articolo")
	public List<Commento> commenti;
	
	@OneToMany(mappedBy = "articolo")
	public List<Like> likes;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getUrlImmagine() {
		return urlImmagine;
	}

	public void setUrlImmagine(String urlImmagine) {
		this.urlImmagine = urlImmagine;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public LocalDateTime getGdo() {
		return gdo;
	}

	public void setGdo(LocalDateTime gdo) {
		this.gdo = gdo;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	
}
