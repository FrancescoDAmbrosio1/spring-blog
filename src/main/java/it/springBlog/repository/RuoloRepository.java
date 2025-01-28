package it.springBlog.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import it.springBlog.model.Ruolo;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
	
	public Set<Ruolo> findById(Integer utente);

}
