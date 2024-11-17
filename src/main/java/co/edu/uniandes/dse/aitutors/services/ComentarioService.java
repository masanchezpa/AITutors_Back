package co.edu.uniandes.dse.aitutors.services;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.repositories.ComentarioRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class ComentarioService {
    
    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ArtefactoRepository artefactoRepository;

    @Transactional
    public ComentarioEntity createComentario(ComentarioEntity comentarioEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Creating a new comentario");


        if (comentarioEntity.getAutor() == null || comentarioEntity.getAutor().getId() == null) {
            throw new IllegalOperationException("Author is not valid");
        }
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(comentarioEntity.getAutor().getId());
        if (usuarioEntity.isEmpty()) {
            throw new EntityNotFoundException("Author not found");
        }
        comentarioEntity.setAutor(usuarioEntity.get());


        if (comentarioEntity.getArtefacto() == null || comentarioEntity.getArtefacto().getId() == null) {
            throw new IllegalOperationException("Artefacto is not valid");
        }
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(comentarioEntity.getArtefacto().getId());
        if (artefactoEntity.isEmpty()) {
            throw new EntityNotFoundException("Artefacto not found");
        }

        ArtefactoEntity existingArtefacto = artefactoEntity.get();
        if (existingArtefacto.getAccion() == null) {
            throw new IllegalOperationException("Artefacto is not associated with any Accion");
        }
        comentarioEntity.setArtefacto(existingArtefacto);

        log.info("Comentario creation complete");
        return comentarioRepository.save(comentarioEntity);
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
        comentario.setId(comentarioId);
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
