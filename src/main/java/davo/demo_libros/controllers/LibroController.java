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


    @GetMapping("/get")
    public ResponseEntity<List<LibroDTO>> getAllLibros() {
        List<LibroDTO> libros = libroService.obtenerTodosLosLibrosDTO();
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroById(@PathVariable Long id) {
        LibroDTO libro = libroService.obtenerLibroPorId(id);
        if (libro != null) {
            return new ResponseEntity<>(libro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<LibroDTO> createLibro(@RequestBody Libro libro,
                                                @RequestParam Long usuarioId) {
        LibroDTO responseDto = libroService.addLibro(libro, usuarioId);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //obtener libros por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LibroDTO>> getBooksByUserId(@PathVariable Long usuarioId) {
        List<LibroDTO> libros = libroService.obtenerLibrosPorUsuario(usuarioId);
        if (libros != null && !libros.isEmpty()) {
            return new ResponseEntity<>(libros, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            System.out.println(libro);
            LibroDTO libroActualizado = libroService.updateLibro(id, libro);
            return ResponseEntity.ok(libroActualizado); // Retorna 200 OK con el libro actualizado
        } catch (RuntimeException e) {
            // Manejo de error si el libro no se encuentra para actualizar
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found
        }
    }

    
}
