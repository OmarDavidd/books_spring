package davo.demo_libros.controllers;

import davo.demo_libros.Dto.GeneroDTO;
import davo.demo_libros.Dto.LibroDTO;
import davo.demo_libros.Models.Genero;
import davo.demo_libros.Models.Libro;
import davo.demo_libros.services.LibroService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.web.bind.annotation.*;

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

    // Modificado para retornar ResponseEntity<LibroDTO>
    @PostMapping
    public ResponseEntity<LibroDTO> createLibro(@RequestBody Libro libro) {
        // Spring mapea el JSON de entrada al objeto Libro (incluyendo campo transitorio urlsImagenes si existe)
        Libro savedLibro = libroService.addLibro(libro); // Llama al servicio para guardar

        // Convertir la entidad guardada a DTO antes de retornar
        LibroDTO responseDto = convertToDTO(savedLibro);

        // Retornar el DTO con estado 201 Created
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Método auxiliar para convertir Entidad Libro a LibroDTO
    private LibroDTO convertToDTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setIsbn(libro.getIsbn());
        dto.setEditorial(libro.getEditorial());
        dto.setFechaCreacion(libro.getFechaCreacion());

        // Mapear géneros a DTOs
        List<GeneroDTO> generosDTO = libro
                .getGeneros()
                .stream()
                .map(this::convertGeneroToDTO)
                .collect(Collectors.toList());

        dto.setGeneros(generosDTO);

        // NOTA: Si quieres incluir las URLs de las imágenes en el DTO de respuesta,
        // necesitarías añadir un campo List<String> urlsImagenes al LibroDTO
        // y mapearlas aquí:
        // if (libro.getImagenes() != null) {
        //     dto.setUrlsImagenes(libro.getImagenes().stream()
        //                               .map(imagen -> imagen.getUrlImagen())
        //                               .collect(Collectors.toList()));
        // }

        return dto;
    }

    // Método auxiliar para convertir Entidad Genero a GeneroDTO
    private GeneroDTO convertGeneroToDTO(Genero genero) {
        // Asumiendo que GeneroDTO tiene un constructor o setters para id, nombre, descripcion
        return new GeneroDTO(
                genero.getId(),
                genero.getNombre(),
                genero.getDescripcion()
        );
    }
}
