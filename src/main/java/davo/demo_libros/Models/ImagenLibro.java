package davo.demo_libros.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "imagenes_libro")
@Data
public class ImagenLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(name = "url_imagen", nullable = false, length = 255)
    private String urlImagen;
}
