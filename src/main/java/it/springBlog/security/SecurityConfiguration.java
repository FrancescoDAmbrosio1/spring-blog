package it.springBlog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.springBlog.service.DatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//        	.csrf(csrf -> csrf.disable())
//            .cors(cors -> cors.disable())
            .authorizeHttpRequests(request -> 
                request
                	//queste sono rotte accessibili a tutti
//                	.requestMatchers("/SpringBlog").hasAuthority("ADMIN")
                	.requestMatchers("/SpringBlog/home").hasAnyAuthority("ADMIN", "UTENTE")
                	.requestMatchers("/login", "/logout", "/SpringBlog/preview","/utente/create").permitAll()
                	.requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**").permitAll()
                	//questo vuol dire che TUTTE le altre devono essere sotto autenticazione
                    .anyRequest().authenticated())
            .formLogin(login -> 
                login.loginPage("/login") // Pagina di login personalizzata
//                    .loginProcessingUrl("/authentication") // URL di elaborazione del login
                    .defaultSuccessUrl("/SpringBlog/home") // Reindirizza dopo il successo del login
                    .permitAll())
            .logout(logout -> 
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/SpringBlog/preview")
                    .permitAll()); // Imposta il logout di default

        return http.build();
    }


//	 @Bean
//	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		    http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//		        .requestMatchers("/SpringBlog").hasAuthority("ADMIN")
//		        .requestMatchers("/SpringBlog").hasAnyAuthority("ADMIN", "OPERATORE")
//		    	.requestMatchers("/css/**", "/js/**", "/webjars/**","/images/**").permitAll()
//		    	.anyRequest().permitAll());
//		    	http.formLogin(login -> login
//		    			.defaultSuccessUrl("/user/home",true))
//		    	.logout().permitAll();
//		    ;
//	    return http.build();
//	  }

	@Bean
	DatabaseUserDetailsService userDetailsService() {
		return new DatabaseUserDetailsService();
	}

//	default Spring Encoder
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
