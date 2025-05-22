package davo.demo_libros.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String autor;

    private String editorial;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(unique = true)
    private String isbn;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado_fisico", nullable = false, length = 50)
    private String estadoFisico;

    @Column(name = "disponible_intercambio", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean disponibleIntercambio = true;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenLibro> imagenes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "libros_generos",
        joinColumns = @JoinColumn(name = "libro_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos = new HashSet<>();

    @Transient
    private List<String> urlsImagenes;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
