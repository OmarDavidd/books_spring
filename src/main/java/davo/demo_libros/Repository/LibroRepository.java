package davo.demo_libros.Repository;



import davo.demo_libros.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, pero buena práctica
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Spring Data JPA proporciona métodos como findAll(), findById(), save(), delete(), etc.
    // Puedes añadir métodos personalizados si necesitas consultas específicas (e.g., findByAutor(String autor))
}
