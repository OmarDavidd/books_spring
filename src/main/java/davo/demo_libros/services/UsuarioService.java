package davo.demo_libros.services;

import davo.demo_libros.Dto.UserDTO;
import davo.demo_libros.Models.Usuario;
import davo.demo_libros.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UserDTO convertToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setNombreCompleto(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setFechaRegistro(usuario.getFechaRegistro().toString());
        dto.setValoracion(usuario.getValoracion());
        dto.setActivo(usuario.getActivo());
        dto.setIdLibros(usuario.getLibros());

        return dto;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UserDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertToDTO); // Convierte Usuario -> UsuarioDTO dentro del Optional
    }

    public Usuario addUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        if (usuario.getId() != null && usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("El usuario con ID " + usuario.getId() + " no existe");
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario desactivarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(false);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }

    public Usuario activarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setActivo(true);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado con ID: " + id);
    }
}
