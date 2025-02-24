package it.springBlog.model;

import java.time.LocalDateTime;

public class ArticoloRest {

	
	private String titolo;
	
	private String contenuto;
	
	private LocalDateTime gdo;
	
	private String nomeAutore;
	
	private String cognomeAutore;
	
	private String categoria;
	
	private String urlImmagine;
	
	private Integer numCommenti;
	
	private Integer numLikes;

	

	public ArticoloRest(String titolo, String contenuto, LocalDateTime gdo, String nomeAutore, String cognomeAutore,
			String categoria, String urlImmagine, Integer numCommenti, Integer numLikes) {
		super();
		this.titolo = titolo;
		this.contenuto = contenuto;
		this.gdo = gdo;
		this.nomeAutore = nomeAutore;
		this.cognomeAutore = cognomeAutore;
		this.categoria = categoria;
		this.urlImmagine = urlImmagine;
		this.numCommenti = numCommenti;
		this.numLikes = numLikes;
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

	public LocalDateTime getGdo() {
		return gdo;
	}

	public void setGdo(LocalDateTime gdo) {
		this.gdo = gdo;
	}

	

	public String getNomeAutore() {
		return nomeAutore;
	}

	public void setNomeAutore(String nomeAutore) {
		this.nomeAutore = nomeAutore;
	}

	public String getCognomeAutore() {
		return cognomeAutore;
	}

	public void setCognomeAutore(String cognomeAutore) {
		this.cognomeAutore = cognomeAutore;
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

	public Integer getNumCommenti() {
		return numCommenti;
	}

	public void setNumCommenti(Integer numCommenti) {
		this.numCommenti = numCommenti;
	}

	public Integer getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(Integer numLikes) {
		this.numLikes = numLikes;
	}
	
	
	
}
