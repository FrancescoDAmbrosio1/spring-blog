package it.springBlog.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer{

//	crea in ogni ambiente in cui runna l'applicazione il path assoluto 
//	alla cartella upload cosicché le immagini caricate per ogni utente/post
//	possano essere visualizzate idipendentemente dal percorso del progetto
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String profiloLocation = "upload";
		
		Path profiloDirectory = Paths.get(profiloLocation);
		
		String profiloPath = profiloDirectory.toFile().getAbsolutePath();
		
		System.out.println("profiloPath" + profiloPath);
		
		registry.addResourceHandler("/" + profiloLocation + "/**")
			.addResourceLocations("file:" + profiloPath + "/");
	}

}
