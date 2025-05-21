package davo.demo_libros.Dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {

    private Long id;
    private String titulo;
    private String autor;
    private Integer anioPublicacion;
    private String isbn;
    private String editorial;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<GeneroDTO> generos;
    private String nombreUsuario;
}
