package davo.demo_libros.services;

import davo.demo_libros.Dto.PrestamoDTO;
import davo.demo_libros.Models.Prestamo;
import davo.demo_libros.Repository.PrestamoRepository;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamosService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public PrestamoDTO convertToDTO(Prestamo prestamo){
        PrestamoDTO dto = new PrestamoDTO();
        dto.setId(prestamo.getId());
        dto.setNombreSolicitante(prestamo.getSolicitante().getNombre());
        dto.setNombrePropietario(prestamo.getPropietario().getNombre());
        dto.setNombreLibro(prestamo.getLibro().getTitulo());
        dto.setFechaInicio(
                Optional.ofNullable(prestamo.getFechaInicio())
                        .map(Object::toString)
                        .orElse("")
        );

        dto.setFechaDevolucionEsperada(
                Optional.ofNullable(prestamo.getFechaDevolucionEsperada())
                        .map(Object::toString)
                        .orElse("")
        );
        dto.setDuracion(prestamo.getDuracion());
        dto.setLugar(prestamo.getLugar());
        dto.setEstado(prestamo.getEstado().getNombre());

        return dto;
    }

    @Transactional
    public PrestamoDTO addPrestamo(Prestamo prestamo) {
        Prestamo savedPrestamo = prestamoRepository.save(prestamo);
        return convertToDTO(savedPrestamo);
    }

    @Transactional
    public PrestamoDTO updateEstado(Prestamo prestamo) {
        Prestamo updatedPrestamo = prestamoRepository.save(prestamo);
        return convertToDTO(updatedPrestamo);
    }

    //get all prestamos
    @Transactional(readOnly = true)
    public List<PrestamoDTO> getAllPrestamos() {
        return prestamoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PrestamoDTO> getPrestamosByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo.");
        }

        List<Prestamo> allPrestamos = prestamoRepository.findAll();

        List<Prestamo> filteredPrestamos = allPrestamos.stream()
                .filter(prestamo -> {
                    Long solicitanteId = (prestamo.getSolicitante() != null) ? prestamo.getSolicitante().getId() : null;
                    Long propietarioId = (prestamo.getPropietario() != null) ? prestamo.getPropietario().getId() : null;

                    return userId.equals(solicitanteId) || userId.equals(propietarioId);
                })
                .collect(Collectors.toList());

        // 3. Convert the filtered list to DTOs
        return filteredPrestamos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
