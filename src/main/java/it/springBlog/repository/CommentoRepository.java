package it.springBlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.springBlog.model.Commento;

public interface CommentoRepository extends JpaRepository<Commento, Long>{
	
	@Query(value = "SELECT c.* FROM commenti c WHERE c.articolo_id = :i", nativeQuery = true)
    List<Commento> findCommentiByPost(@Param("i") Long i);
}
