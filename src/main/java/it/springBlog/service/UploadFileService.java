package it.springBlog.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.springBlog.model.Utente;

@Service
public class UploadFileService {
	
	@Autowired
	private DatabaseUserDetailsService databaseUserDetailsService;
	
	public String saveProfileImage(MultipartFile file, Utente utente) throws IllegalStateException, IOException {
		
		
//		creazione file name 
		
//			Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
			
			String finalFileName = LocalDateTime.now() + "_idUser_" + utente.getId() + "_" + file.getOriginalFilename(); 
			
//			creazione/verifica esistenza della cartella Upload e sottocartelle
			Path uploadPath = Paths.get("upload/images/utentiId/" + utente.getId() + "/imgProfilo/" );
			
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
//	
//public String savePostImage(MultipartFile file, Utente utente) throws IllegalStateException, IOException {
//		
//		
////		creazione file name - quando sarà introdotto userLogged aggiungere id utente al nome per univocità
//		
//			Utente userLogged = databaseUserDetailsService.verifyAuthenticationUser();
//			
//			String finalFileName = LocalDateTime.now() + "_idUser_" + userLogged.getId() + "_Post_" + file.getOriginalFilename(); 
//			
////			creazione/verifica esistenza della cartella Upload e sottocartelle
//			Path uploadPath = Paths.get("upload/images/utentiId/" + userLogged.getId() + "/imgPosts/" );
//			
////			verifica esistenza ed eventuale creazione della cartella Upload 
////			e sottocartelle
//			if(!Files.exists(uploadPath)){
//				Files.createDirectories(uploadPath);
//			}
//			
////		trasferimento alla directory del file 
//			InputStream inputStream = file.getInputStream();
//			Path filePath = uploadPath.resolve(finalFileName);
//			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//
////		creazione dell'url che viene salvato a db nella colonna urlLogo
//			String imgName = finalFileName;
//			
//			return imgName;
//		}
//		
////		metodo che verifica se il formato del file rispecchia i requisiti (JPEG/JPG/HEIC/GIF) e restituisce
////		un boolean per la successiva generazione dell'errore custom relativo al formato
//		public Boolean anyErrorFormatImage(MultipartFile file) {
//			
//			boolean result = false;
//			
//			String contentType = file.getContentType();
//			
//			if (!file.isEmpty() && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")
//					&& !contentType.equals("image/heic") && !contentType.equals("image/gif")
//					) {
//			    
//				result = true;
//				
//			}
//			
//			return result;
//		
//		}
//		
//		
//
//
	}

