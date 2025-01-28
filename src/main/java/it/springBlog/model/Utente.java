package it.springBlog.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "utenti")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "Inserimento nome utente obbligatorio")
	@Column(name = "nome")
	private String nome;
	
	@NotBlank(message = "Inserimento cognome utente obbligatorio")
	@Column(name = "congome")
	private String cognome;
	
	@NotBlank(message = "Inserimento email utente obbligatorio")
	@Column(name = "email")
	private String email;
	
//	@Column(name = "username")
//	private String username;
	
	@NotBlank(message = "Inserimento password obbligatorio")
	@Column(name = "password")
	private String password;
	
	@Column(name = "img_profilo")
	private String imgProfilo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Ruolo> ruoli;
	
	@OneToMany(mappedBy = "autore")
	public List<Articolo> articoli;
	
	@OneToMany(mappedBy = "autore")
	public List<Commento> commenti;
	
	@OneToMany(mappedBy = "autore")
	public List<Like> likes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public List<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}

	public String getImgProfilo() {
		return imgProfilo;
	}

	public void setImgProfilo(String imgProfilo) {
		this.imgProfilo = imgProfilo;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
	
	

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	
	
	

}
