package it.springBlog.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.springBlog.model.Articolo;
import it.springBlog.model.ArticoloRest;
import it.springBlog.repository.ArticoloRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BlogRestController {
	
	@Autowired
	private ArticoloRepository articoloRepository;
	
	@GetMapping("/searchPosts/{input}")
public Optional<List<ArticoloRest>> search(@PathVariable("input") String input){
	
		List<Articolo> posts = articoloRepository.search(input);
		
		List<ArticoloRest> postsRest = new ArrayList<ArticoloRest>();
		
		for(Articolo item : posts) {
			
			
			String titolo = item.getTitolo() ;
			
			String contenuto = item.getContenuto();
			
			LocalDateTime gdo = item.getGdo();
			
			String nomeAutore = item.getAutore().getNome();
			
			String congomeAutore = item.getAutore().getCognome();
			
			String categoria = item.getCategoria();
			
			String urlImmagine = item.getUrlImmagine();
			
			Integer numCommenti = item.getCommenti().size();
			
			Integer numLikes = item.getLikes().size();
			
			ArticoloRest articolo = new ArticoloRest(titolo, contenuto, gdo, nomeAutore, congomeAutore, categoria, urlImmagine, numCommenti, numLikes);
			
			postsRest.add(articolo);
		}
	
		return Optional.of(postsRest);
	}
}
