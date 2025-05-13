package davo.demo_libros.controllers;

import davo.demo_libros.Dto.GeneroDTO;
import davo.demo_libros.Dto.LibroDTO;
import davo.demo_libros.Models.Genero;
import davo.demo_libros.Models.Libro;
import davo.demo_libros.services.LibroService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<LibroDTO> getAllLibros() {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        return libros
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private LibroDTO convertToDTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setIsbn(libro.getIsbn());
        dto.setEditorial(libro.getEditorial());
        dto.setFechaCreacion(libro.getFechaCreacion());

        List<GeneroDTO> generosDTO = libro
            .getGeneros()
            .stream()
            .map(this::convertGeneroToDTO)
            .collect(Collectors.toList());

        dto.setGeneros(generosDTO);
        return dto;
    }

    private GeneroDTO convertGeneroToDTO(Genero genero) {
        return new GeneroDTO(
            genero.getId(),
            genero.getNombre(),
            genero.getDescripcion()
        );
    }
}
