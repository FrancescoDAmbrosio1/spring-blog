package it.springBlog.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.springBlog.model.Ruolo;
import it.springBlog.model.Utente;
import it.springBlog.repository.RuoloRepository;
import it.springBlog.repository.UtenteRepository;
import it.springBlog.service.DatabaseUserDetailsService;
import it.springBlog.service.UploadFileService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utente")
public class UtenteController {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private RuoloRepository ruoloRepository;
	
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@GetMapping("/create")
	private String registraUtente(Model model) {
		
		List<Ruolo> ruoliList = new ArrayList<Ruolo>();
		
		ruoliList = ruoloRepository.findAll();
		
		model.addAttribute("ruoliList", ruoliList);
		
		Utente utente = new Utente();
		
		model.addAttribute("utente", utente);
		
		return "/utente/create";
	}
	
	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private String saveUtente(@RequestParam("file")MultipartFile file, Model model,
				@Valid @ModelAttribute("utente")Utente utente, BindingResult bindingResult) 
						throws IllegalStateException, IOException {
		
		
		if(bindingResult.hasErrors()) {
			
			return "/utente/create";
		}
		
		Set<Ruolo> ruoloUtente = ruoloRepository.findById(1);
		
		utente.setRuoli(ruoloUtente);

		databaseUserDetailsService.saveCriptedPassword(utente);
		
		if(!file.isEmpty()) {
			
			String profilePhotoName = uploadFileService.saveProfileImage(file, utente);
			
			utente.setImgProfilo(profilePhotoName);
			
			utenteRepository.save(utente);
			
		} else {
			
			utente.setImgProfilo("/images/avatar.jpg");
			
			utenteRepository.save(utente);
		}
		
		
		
		return "redirect:/SpringBlog/preview";
	}

	@GetMapping("/profilo/{id}")
	private String accountPage(@PathVariable("id")Long profiloId, Model model, Utente utenteLogged) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
		String username = authentication.getName();

        Optional<Utente> utente = utenteRepository.findByEmail(username);
        
        utenteLogged = utente.get();
        
        model.addAttribute("imgProfilo", utenteLogged.getImgProfilo());
		
		model.addAttribute("utente", utenteLogged);
		
		return "/utente/profilo";
	}
	
	@GetMapping("/edit/{id}/anagrafica")
	private String editAnagraficaUtente(@PathVariable("id") Long profiloId, Model model, Utente utenteLogged) {
		
		Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
        
        userLogged.getPassword();
        
        String imgProfilo = utenteLogged.getImgProfilo();
        
        model.addAttribute("imgProfilo", imgProfilo);
		
		model.addAttribute("utente", utenteLogged);
		
		return "/utente/edit-anagrafica";
	}
	
	
	@PostMapping(value = "/edit/{id}/anagrafica")
	private String updateAnagraficaUtente(Model model,@ModelAttribute("utente")Utente utente) {
		
		utenteRepository.save(utente);
		
		return "/utente/profilo";
	}
	
	@GetMapping("/edit/{id}/imgProfilo")
	private String editImgProfiloUtente(Model model, @PathVariable("id") Long profiloId, Utente utenteLogged) {
		
		Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
        
        userLogged.getPassword();
        
        String imgProfilo = utenteLogged.getImgProfilo();
        
        model.addAttribute("imgProfilo", imgProfilo);
		
		model.addAttribute("utente", utenteLogged);
        
        return "/utente/edit-img-profilo";
	}
	
	@PostMapping(value = "/edit/{id}/imgProfilo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private String updateImgProfiloUtente(@RequestParam("file")MultipartFile file, Model model,@ModelAttribute("utente")Utente utenteLogged) throws IllegalStateException, IOException {
	
		Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
        
        userLogged.getPassword();
		
        String finalFileName = utenteLogged.getNome() + utenteLogged.getCognome() + LocalDateTime.now() + file.getOriginalFilename();
		
		file.transferTo(new File("/Users/francesco/Desktop/Corso Backend JAVA Developer PT/Progetti personali/SpringBlog/SpringBlog/src/main/resources/static/images/profileImages/" + finalFileName));
		
		String imgPath = "/images/profileImages/" + finalFileName;
		
		utenteRepository.updateImgProfile(imgPath, utenteLogged.getId());
		
		return "/utente/profilo";
	}
}
