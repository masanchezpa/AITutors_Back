package co.edu.uniandes.dse.aitutors.services;


import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.ComentarioRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexión con la persistencia para la relación entre
 * la entidad Editorial y Book.
 *
 * @author ISIS2603
 */
@Slf4j
@Service
public class UsuarioComentarioService  {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
    @Transactional
    public ComentarioEntity addComentario(Long comentarioId, Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregarle un comentario a un usuario con id = {0}", usuarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);

        comentarioEntity.get().setAutor(usuarioEntity.get());
        log.info("Termina proceso de agregarle un comentario a un usuario con id = {0}", usuarioId);

        return comentarioEntity.get();
    }

	
    @Transactional
    public List<ComentarioEntity> getComentarios(Long usuarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los comentarios asociados a un usuario con id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if(usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        
        return usuarioEntity.get().getComentarios();
    }

	@Transactional
    public ComentarioEntity getComentario(Long usuarioId, Long comentarioId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar el comentario con id = {0} del usuario con id = " + usuarioId, comentarioId);
        
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if(usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
              
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if(comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);
        
            
        if(comentarioEntity.get().getAutor()==null){
            throw new IllegalOperationException(ErrorMessage.COMENTARIO_NOT_FOUND);        
        }
        log.info("Termina proceso de consultar el comentario con id = {0} del usuario con id = " + usuarioId, comentarioId);
        if(!comentarioEntity.get().getAutor().equals(usuarioEntity.get())){
            throw new IllegalOperationException("The comment is not associated to the user");
        }
        return comentarioEntity.get();
    }


	
    @Transactional
    public List<ComentarioEntity> replaceComentario(Long usuarioId, List<ComentarioEntity> comentarios) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el usuario con id = {0}", usuarioId);
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(usuarioId);
        if(usuarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        
        for(ComentarioEntity comentario : comentarios) {
            Optional<ComentarioEntity> c = comentarioRepository.findById(comentario.getId());
            if(c.isEmpty())
                throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

            c.get().setAutor(usuarioEntity.get());
            usuarioEntity.get().getComentarios().add(comentario);
        }        
        usuarioEntity.get().setComentarios(comentarios);
        return usuarioEntity.get().getComentarios();
    }

}