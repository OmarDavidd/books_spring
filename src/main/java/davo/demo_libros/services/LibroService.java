package davo.demo_libros.services;

import davo.demo_libros.Models.Libro;
import davo.demo_libros.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired // Inyecta el repositorio
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll(); // Usa el método proporcionado por JpaRepository
    }

    // Puedes añadir otros métodos aquí, como obtenerLibroPorId, guardarLibro, etc.
}
