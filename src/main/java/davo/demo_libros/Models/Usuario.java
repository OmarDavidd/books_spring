package davo.demo_libros.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(columnDefinition = "DECIMAL(3,2) DEFAULT 0.0")
    private Double valoracion;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean activo = true;

    // Relaciones
    @OneToMany(
            mappedBy = "usuario", // Asumiendo que Libro tiene un campo 'usuario' que mapea a Usuario
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Libro> libros = new ArrayList<>();

    @OneToMany(mappedBy = "solicitante") // <-- CAMBIADO: 'usuarioSolicitante' ahora es 'solicitante'
    private List<Prestamo> intercambiosSolicitados = new ArrayList<>();

    @OneToMany(mappedBy = "propietario") // <-- CAMBIADO: 'usuarioPropietario' ahora es 'propietario'
    private List<Prestamo> intercambiosRecibidos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}