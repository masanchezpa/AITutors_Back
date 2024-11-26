package co.edu.uniandes.dse.aitutors.services;




import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import co.edu.uniandes.dse.aitutors.dto.UsuarioResponseDTO;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioEntity createUsuario(UsuarioEntity usuario) throws IllegalOperationException {
        log.info("Creating a new usuario");
        
        if (!usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
            throw new IllegalOperationException("EL CORREO YA ESTA REGISTRADO EN EL SISTEMA");
        }
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public List<UsuarioEntity> getUsuarios() {
        log.info("Retrieving all usuarios");
        return usuarioRepository.findAll();
    }

    @Transactional
    public UsuarioEntity getUsuario(Long usuarioId) throws EntityNotFoundException {
        log.info("Retrieving the usuario with id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }

        return usuarioEntity.get();
    }

    
    @Transactional
    public UsuarioResponseDTO getUserWithDtypeByEmail(String email) throws EntityNotFoundException  {
        Map<String, Object> userWithDtype = usuarioRepository.findUserWithDtypeByEmail(email);

        Long id = ((Number) userWithDtype.get("id")).longValue();
        String nameValue = (String) userWithDtype.get("nombre");
        String emailValue = (String) userWithDtype.get("email");
        String dtype = (String) userWithDtype.get("DTYPE");

        
        return new UsuarioResponseDTO(id, nameValue, emailValue, dtype);
    }




    @Transactional
    public UsuarioEntity updateUsuario(Long usuarioId, UsuarioEntity usuario) throws EntityNotFoundException {
        log.info("Updating the usuario with id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (!usuarioEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }
        usuario.setId(usuarioId);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuario(Long usuarioId) throws EntityNotFoundException {
        log.info("Deleting the usuario with id = {0}", usuarioId);
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(usuarioId);
        if (!usuario.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }
        usuarioRepository.deleteById(usuarioId);
    }




}
