package co.edu.uniandes.dse.aitutors.services;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.repositories.ComentarioRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class ComentarioService {
    
    @Autowired
    ComentarioRepository comentarioRepository;

    @Transactional
    public ComentarioEntity createComentario(ComentarioEntity comentario) {
        log.info("Creating a new comentario");
        return comentarioRepository.save(comentario);
    }


    @Transactional
    public List<ComentarioEntity> getComentarios() {
        log.info("Retrieving all comentarios");
        return comentarioRepository.findAll();
    }

    @Transactional
    public ComentarioEntity getComentario(Long comentarioId) throws EntityNotFoundException {
        log.info("Retrieving the comentario with id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);
        }
        return comentarioEntity.get();
    }

    @Transactional
    public ComentarioEntity updateComentario(Long comentarioId, ComentarioEntity comentario) throws EntityNotFoundException {
        log.info("Updating the comentario with id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (!comentarioEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);
        }
        return comentarioRepository.save(comentario);
    }

    @Transactional
    public void deleteComentario(Long comentarioId) throws EntityNotFoundException {
        log.info("Deleting the comentario with id = {0}", comentarioId);
        Optional<ComentarioEntity> comentario = comentarioRepository.findById(comentarioId);
        if (!comentario.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);
        }
        comentarioRepository.deleteById(comentarioId);
    }





}
