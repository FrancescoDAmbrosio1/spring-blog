package it.springBlog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.springBlog.model.Articolo;
import it.springBlog.model.Utente;
import it.springBlog.repository.ArticoloRepository;
import it.springBlog.repository.CategoriaRepository;
import it.springBlog.service.DatabaseUserDetailsService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/SpringBlog")
public class IndexController {
	
	@Autowired
	private ArticoloRepository articoloRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	
	
	@GetMapping("/preview")
	private String preview(Model model) {
		
		model.addAttribute("categorieList", categoriaRepository.findAll());
		
		model.addAttribute("postList", articoloRepository.findAll(Sort.by(Sort.Direction.DESC, "gdo")));
		
		return "preview";
	}
	
	@GetMapping("/home")
	private String index(Model model) {
        
        Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
		
		model.addAttribute("utente", userLogged);
		
		model.addAttribute("imgProfilo", userLogged.getImgProfilo());
		
		model.addAttribute("categorieList", categoriaRepository.findAll());
		
		model.addAttribute("postList", articoloRepository.findAll(Sort.by(Sort.Direction.DESC, "gdo")));
		
        return "index";
	}
	
	@GetMapping("/home/newPost")
	private String newPost(Model model) {
        
        Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
		
		model.addAttribute("utente", userLogged);
		
		model.addAttribute("imgProfilo", userLogged.getImgProfilo());
		
		Articolo articolo = new Articolo();
		
		model.addAttribute("articolo", articolo);
		
		model.addAttribute("categorieList", categoriaRepository.findAll());
		
		return "/articoli/new-post";
	}
	
	@PostMapping(value ="/home/newPost", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private String saveNewPost(@RequestParam("file")MultipartFile file, Model model, 
				@Valid @ModelAttribute("articolo")Articolo articolo, BindingResult bindingResult) 
						throws IllegalStateException, IOException{
			
//			@Valid @RequestParam("file")MultipartFile file, Model model,
//			BindingResult bindingResult,
//			@ModelAttribute("articolo")Articolo articolo) throws IllegalStateException, IOException {
		
		Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
		
		model.addAttribute("utente", userLogged);
		
		model.addAttribute("imgProfilo", userLogged.getImgProfilo());
		
		model.addAttribute("categorieList", categoriaRepository.findAll());
		
		if(bindingResult.hasErrors()) {
			
			return "/articoli/new-post";

		}
		
		
		articolo.setAutore(userLogged);
		
		articolo.setGdo(LocalDateTime.now());
		
		if(!file.isEmpty()) {
			
			String finalFileName = userLogged.getNome() + userLogged.getCognome() + articolo.getTitolo() + LocalDateTime.now() + file.getOriginalFilename();
			
			file.transferTo(new File("/Users/francesco/Desktop/Corso Backend JAVA Developer PT/Progetti personali/SpringBlog/SpringBlog/src/main/resources/static/images/postImages/" + finalFileName));
			
			String imgPath = "/images/postImages/" + finalFileName;
			
			articolo.setUrlImmagine(imgPath);
			
		}
		
		
		
		articoloRepository.save(articolo);
		
		
		return "redirect:/SpringBlog/home";
	}
	
}
