package davo.demo_libros.controllers;

import davo.demo_libros.Dto.PrestamoDTO;
import davo.demo_libros.Models.Prestamo;
import davo.demo_libros.services.LibroService;
import davo.demo_libros.services.PrestamosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamosService prestamosService;

    @PostMapping
    public ResponseEntity<PrestamoDTO> createPrestamo(@RequestBody Prestamo prestamo) {
        PrestamoDTO savedPrestamo = prestamosService.addPrestamo(prestamo);
        return new ResponseEntity<>(savedPrestamo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> getAllPrestamos() {
        List<PrestamoDTO> prestamos = prestamosService.getAllPrestamos();
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<List<PrestamoDTO>> getPrestamosByUserId(@PathVariable Long id) {
        List<PrestamoDTO> prestamos = prestamosService.getPrestamosByUserId(id);
        if (prestamos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(prestamos, HttpStatus.OK);
    }
}
