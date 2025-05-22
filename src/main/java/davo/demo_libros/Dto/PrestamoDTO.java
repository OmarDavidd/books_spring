package davo.demo_libros.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDTO {
    private Long id;
    private String nombreSolicitante;
    private String nombrePropietario;
    private String nombreLibro;
    private String fechaInicio;
    private String fechaDevolucionEsperada;
    private int duracion;
    private String lugar;
    private String estado;
}
