package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Libros repetidos
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    // Para la Opción 5 del menu: Listar libros por idioma
    List<Libro> findByIdioma(String idioma);
}
