package it.springBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.springBlog.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
