package davo.demo_libros.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estados_prestamo")
@Data
public class EstadoPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre; // Solicitado, Aprobado, En préstamo, Devuelto, Rechazado

    private String descripcion;

    @OneToMany(mappedBy = "estado")
    private List<Prestamo> prestamos = new ArrayList<>();
}
