package it.springBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.springBlog.model.Articolo;

public interface ArticoloRepository extends JpaRepository<Articolo, Long> {

	@Query("SELECT a FROM Articolo a WHERE a.titolo LIKE '%'||:input||'%' OR "
    		+ "a.contenuto LIKE '%'||:input||'%'")
    public List<Articolo> search( String input);
}
