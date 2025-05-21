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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;


    @GetMapping
    public List<LibroDTO> getAllLibros() {
        return libroService.obtenerTodosLosLibrosDTO();
    }

    /*
    // Modificado para retornar ResponseEntity<LibroDTO>
    @PostMapping
    public ResponseEntity<LibroDTO> createLibro(@RequestBody Libro libro) {
        // Spring mapea el JSON de entrada al objeto Libro (incluyendo campo transitorio urlsImagenes si existe)
        Libro savedLibro = libroService.addLibro(libro); // Llama al servicio para guardar

        // Convertir la entidad guardada a DTO antes de retornar
        LibroDTO responseDto = convertToDTO(savedLibro);

        // Retornar el DTO con estado 201 Created
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }*/


}
