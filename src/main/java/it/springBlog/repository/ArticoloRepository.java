package it.springBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.springBlog.model.Articolo;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {

}
