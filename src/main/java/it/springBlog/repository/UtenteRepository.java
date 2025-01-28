package it.springBlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import it.springBlog.model.Utente;
import jakarta.transaction.Transactional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Optional<Utente> findByEmail(String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE Utente u SET u.imgProfilo = :imgPath where u.id = :id")
	
		public void updateImgProfile(String imgPath, long id);

}

