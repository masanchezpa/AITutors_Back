
package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.repositories.ComentarioRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ComentarioUsuarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
    @Transactional
    public UsuarioEntity addUsuario(Long comentarioId, Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregar un usuario al comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);

        comentarioEntity.get().setAutor(usuarioEntity.get());
        log.info("Termina proceso de agregar un usuario al comentario con id = {0}", comentarioId);

        return usuarioEntity.get();
    }


    @Transactional
    public UsuarioEntity getUsuario(Long comentarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el usuario del comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        if (comentarioEntity.get().getAutor() == null) {
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }
        log.info("Termina proceso de consultar el usuario del comentario con id = {0}", comentarioId);
        return comentarioEntity.get().getAutor();
    }

	

    @Transactional
    public UsuarioEntity replaceUsuario(Long comentarioId, Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de reemplazar el usuario del comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);

        comentarioEntity.get().setAutor(usuarioEntity.get());
        log.info("Termina proceso de reemplazar el usuario del comentario con id = {0}", comentarioId);

        return usuarioEntity.get();
    }

    @Transactional
    public void removeUsuario(Long comentarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el usuario del comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        comentarioEntity.get().setAutor(null);
        log.info("Termina proceso de borrar el usuario del comentario con id = {0}", comentarioId);
    }

    @Transactional
    public void removeComentario(Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el comentario del usuario con id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);

        usuarioEntity.get().getComentarios().forEach(comentario -> comentario.setAutor(null));
        log.info("Termina proceso de borrar el comentario del usuario con id = {0}", usuarioId);
    }

    @Transactional
    public ComentarioEntity getComentario(Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el comentario del usuario con id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);

        log.info("Termina proceso de consultar el comentario del usuario con id = {0}", usuarioId);
        return usuarioEntity.get().getComentarios().get(0);
    }

}
