package it.springBlog.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.springBlog.model.Articolo;
import it.springBlog.model.Commento;
import it.springBlog.model.Utente;
import it.springBlog.repository.ArticoloRepository;
import it.springBlog.repository.CategoriaRepository;
import it.springBlog.repository.CommentoRepository;
import it.springBlog.service.DatabaseUserDetailsService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/Posts")
public class PostController {
	
	@Autowired
	private ArticoloRepository articoloRepository;
	
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CommentoRepository commentoRepository;

	@GetMapping("/deatailPost/{id}")
		private String deatailPost(@PathVariable("id")Long postId, Model model,
					Articolo post) {
			
			model.addAttribute("post", articoloRepository.getReferenceById(postId));
			
			Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
			
			model.addAttribute("utente", userLogged);
			
			model.addAttribute("imgProfilo", userLogged.getImgProfilo());
			
			model.addAttribute("categorieList", categoriaRepository.findAll());
			
			model.addAttribute("commentiList", commentoRepository.findCommentiByPost(postId));
			
			Commento newCommento = new Commento();
			
			model.addAttribute("commento", newCommento);
		
		return "/articoli/detail";
	}
	
	@PostMapping("/newCommento/post{id}")
	private String saveNewCommento(@PathVariable("id")Long postId, @Valid @ModelAttribute("commento") Commento commento,
			Model model, BindingResult bindingResult) {
		
		
		Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
		
		model.addAttribute("utente", userLogged);
		
		model.addAttribute("imgProfilo", userLogged.getImgProfilo());
		
		model.addAttribute("categorieList", categoriaRepository.findAll());
		
		model.addAttribute("commentiList", commentoRepository.findCommentiByPost(postId));
		
		Articolo post = articoloRepository.getReferenceById(postId);
		
		model.addAttribute("post", post);
		
		commento = new Commento();
		
		if(bindingResult.hasErrors()) {
			
			return "/articoli/detail";
			
		}
		commento.setArticolo(post);
		
		commento.setAutore(userLogged);
		
		commento.setGdo(LocalDateTime.now());
		
		
		commentoRepository.save(commento);
		
		return "redirect:/SpringBlog/home";
	}
}
