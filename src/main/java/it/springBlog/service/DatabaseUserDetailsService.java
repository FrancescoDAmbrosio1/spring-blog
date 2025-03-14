package it.springBlog.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
	
	public Utente saveCriptedPassword( Utente utente ) {
		
		utente.setPassword(encoder.encode(utente.getPassword()));
		
		
				
		return utenteRepository.save(utente);
		
	}
	
//	public void loadProfilePhoto(MultipartFile file, Utente utente) throws IllegalStateException, IOException{
//		
//		String finalFileName = utente.getNome() + utente.getCognome() + LocalDateTime.now() + file.getOriginalFilename();
//			
//		file.transferTo(new File("/Users/francesco/Desktop/Corso Backend JAVA Developer PT/Progetti personali/SpringBlog/SpringBlog/src/main/resources/static/images/profileImages/" + finalFileName));
//		
//		if(!file.isEmpty()) {
//			
//			utente.setImgProfilo("/images/profileImages/" + finalFileName);
//			
//		}
//		
//		return ;
//	}
	
	
	public Utente verifyAuthenticationUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		
		Optional<Utente> utente = utenteRepository.findByEmail(username);
		
		Utente userLogged = utente.get();
		
		return userLogged;
	}
	
public void saveProfileImage(MultipartFile file, Utente utente) throws IllegalStateException, IOException {
		
		
//		creazione file name 
		
			Utente userLogged = verifyAuthenticationUser();
			
			String finalFileName = LocalDateTime.now() + "_idUser_" + userLogged.getId() + "_" + file.getOriginalFilename(); 
			
//			creazione/verifica esistenza della cartella Upload e sottocartelle
			Path uploadPath = Paths.get("upload/images/utentiId/" + userLogged.getId() + "/imgProfilo/" );
			
//			verifica esistenza ed eventuale creazione della cartella Upload 
//			e sottocartelle
			if(!Files.exists(uploadPath)){
				Files.createDirectories(uploadPath);
			}
			
//		trasferimento alla directory del file 
			InputStream inputStream = file.getInputStream();
			Path filePath = uploadPath.resolve(finalFileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

//		creazione dell'url che viene salvato a db nella colonna urlLogo
			String imgName = finalFileName;
			
			userLogged.setImgProfilo(imgName);
			
			return;
		}
	
public String savePostImage(MultipartFile file, Utente utente) throws IllegalStateException, IOException {
		
		
//		creazione file name - quando sarà introdotto userLogged aggiungere id utente al nome per univocità
		
			Utente userLogged = verifyAuthenticationUser();
			
			String finalFileName = LocalDateTime.now() + "_idUser_" + userLogged.getId() + "_Post_" + file.getOriginalFilename(); 
			
//			creazione/verifica esistenza della cartella Upload e sottocartelle
			Path uploadPath = Paths.get("upload/images/utentiId/" + userLogged.getId() + "/imgPosts/" );
			
//			verifica esistenza ed eventuale creazione della cartella Upload 
//			e sottocartelle
			if(!Files.exists(uploadPath)){
				Files.createDirectories(uploadPath);
			}
			
//		trasferimento alla directory del file 
			InputStream inputStream = file.getInputStream();
			Path filePath = uploadPath.resolve(finalFileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

//		creazione dell'url che viene salvato a db nella colonna urlLogo
			String imgName = finalFileName;
			
			return imgName;
		}
		
//		metodo che verifica se il formato del file rispecchia i requisiti (JPEG/JPG/HEIC/GIF) e restituisce
//		un boolean per la successiva generazione dell'errore custom relativo al formato
		public Boolean anyErrorFormatImage(MultipartFile file) {
			
			boolean result = false;
			
			String contentType = file.getContentType();
			
			if (!file.isEmpty() && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")
					&& !contentType.equals("image/heic") && !contentType.equals("image/gif")
					) {
			    
				result = true;
				
			}
			
			return result;
		
		}
		
		


	}

	




