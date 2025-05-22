package davo.demo_libros.Repository;

import davo.demo_libros.Models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, pero buena práctica
public interface GeneroRepository extends JpaRepository<Genero, Long> {
}