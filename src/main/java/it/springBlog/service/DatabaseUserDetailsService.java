package it.springBlog.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import it.springBlog.model.Utente;
import it.springBlog.repository.UtenteRepository;
import it.springBlog.security.DatabaseUserDetails;

public class DatabaseUserDetailsService implements UserDetailsService{

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Optional<Utente> utente = utenteRepository.findByEmail(username);
		
		if(utente.isPresent()) {
		
			return new DatabaseUserDetails(utente.get());
		
		} else {
		
			throw new UsernameNotFoundException("Username non trovato");
		
			}
	
		}

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	
	public Utente saveCriptedPassword(MultipartFile file, Utente utente ) throws IllegalStateException, IOException{
		
		utente.setPassword(encoder.encode(utente.getPassword()));
		
		if(!file.isEmpty()) {
			
			loadProfilePhoto(file, utente);
			
		}
				
		return utenteRepository.save(utente);
		
	}
	
	public void loadProfilePhoto(MultipartFile file, Utente utente) throws IllegalStateException, IOException{
		
		String finalFileName = utente.getNome() + utente.getCognome() + LocalDateTime.now() + file.getOriginalFilename();
			
		file.transferTo(new File("/Users/francesco/Desktop/Corso Backend JAVA Developer PT/Progetti personali/SpringBlog/SpringBlog/src/main/resources/static/images/profileImages/" + finalFileName));
		
		if(!file.isEmpty()) {
			
			utente.setImgProfilo("/images/profileImages/" + finalFileName);
			
		}
		
		return ;
	}
	
	
	public Utente verifyAuthenticationUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		
		Optional<Utente> utente = utenteRepository.findByEmail(username);
		
		Utente userLogged = utente.get();
		
		return userLogged;
	}
	

	

}


