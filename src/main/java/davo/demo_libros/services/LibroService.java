package davo.demo_libros.services;

import davo.demo_libros.Dto.GeneroDTO;
import davo.demo_libros.Dto.LibroDTO;
import davo.demo_libros.Dto.UserDTO;
import davo.demo_libros.Models.Genero;
import davo.demo_libros.Models.ImagenLibro;
import davo.demo_libros.Models.Libro;
import davo.demo_libros.Models.Usuario;
import davo.demo_libros.Repository.LibroRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import davo.demo_libros.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public LibroDTO convertToDTO(Libro book) {
        if (book == null) {
            return null;
        }

        LibroDTO dto = new LibroDTO();
        dto.setId(book.getId());
        dto.setTitulo(book.getTitulo());
        dto.setAutor(book.getAutor());
        dto.setAnioPublicacion(book.getAnioPublicacion());
        dto.setIsbn(book.getIsbn());
        dto.setEditorial(book.getEditorial());
        dto.setEstado(book.getEstadoFisico());
        if (book.getGeneros() != null) {
            dto.setGeneros(
                    book.getGeneros().stream()
                            .map(this::convertGeneroToDTO)
                            .collect(Collectors.toList())
            );
        } else {
            dto.setGeneros(Collections.emptyList());
        }

        dto.setNombreUsuario(
                usuarioService.findById(book.getUsuario().getId())
                        .map(UserDTO::getNombreCompleto)
                        .orElse("Usuario no disponible")
        );
        return dto;
    }

    private GeneroDTO convertGeneroToDTO(Genero genero) {
        GeneroDTO generoDTO = new GeneroDTO();
        generoDTO.setId(genero.getId());
        generoDTO.setNombre(genero.getNombre());
        generoDTO.setDescripcion(genero.getDescripcion());
        return generoDTO;
    }

    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerTodosLosLibrosDTO() {
        return libroRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LibroDTO obtenerLibroPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID no puede ser nulo");
        }
        return libroRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    /*
    @Transactional
    public Libro addLibro(Libro libro, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        libro.setUsuario(usuario);

        if (libro.getDisponibleIntercambio() == null) {
            libro.setDisponibleIntercambio(true);
        }

        return libroRepository.save(libro);
    }
*/
    /*
    @Transactional
    public Libro addLibro(Libro libro) {
        libro.getImagenes().clear();

        List<String> imageUrls = libro.getUrlsImagenes();

        if (imageUrls != null && !imageUrls.isEmpty()) {
            List<ImagenLibro> imagenes = imageUrls.stream()
                    .map(url -> {
                        ImagenLibro imagen = new ImagenLibro();
                        imagen.setUrlImagen(url);
                        imagen.setLibro(libro);
                        return imagen;
                    })
                    .collect(Collectors.toList());
            libro.setImagenes(imagenes);
        }

        return libroRepository.save(libro);
    }*/
/*
    @Transactional
    public void eliminarLibro(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
    }*/
}
