package davo.demo_libros.services;

import davo.demo_libros.Models.ImagenLibro;
import davo.demo_libros.Models.Libro;
import davo.demo_libros.Models.Usuario;
import davo.demo_libros.Repository.LibroRepository;
import java.util.List;
import java.util.stream.Collectors;

import davo.demo_libros.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public Libro addLibro(Libro libro, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        libro.setUsuario(usuario);

        if (libro.getDisponibleIntercambio() == null) {
            libro.setDisponibleIntercambio(true);
        }

        return libroRepository.save(libro);
    }

    public Libro addLibro(Libro libro) {
        libro.getImagenes().clear();

        List<String> imageUrls = libro.getUrlsImagenes();

        if (imageUrls != null && !imageUrls.isEmpty()) {
            List<ImagenLibro> imagenes = imageUrls.stream()
                    .map(url -> {
                        ImagenLibro imagen = new ImagenLibro();
                        imagen.setUrlImagen(url); // Usando setUrlImagen
                        imagen.setLibro(libro); // Establecer la relación bidireccional
                        // Puedes añadir lógica para nombre de archivo, orden, etc. aquí si tu entidad ImagenLibro lo soporta
                        return imagen;
                    })
                    .collect(Collectors.toList());
            libro.setImagenes(imagenes); // Establecer la lista de imágenes en el libro
        }

        // Guardar el libro (esto también guardará las ImagenLibro asociadas debido al cascade)
        return libroRepository.save(libro);
    }

    // Otros métodos que puedas necesitar
    public Libro obtenerLibroPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }
}
