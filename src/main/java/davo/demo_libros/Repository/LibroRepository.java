package davo.demo_libros.Repository;



import davo.demo_libros.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Opcional, pero buena práctica
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByUsuarioId(Long usuarioId);
}
