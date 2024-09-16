package co.edu.uniandes.dse.aitutors.services;



import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
import co.edu.uniandes.dse.aitutors.repositories.ComentarioRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;


import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad Prize y Author
 *
 */
@Slf4j
@Service

public class ComentarioArtefactoService {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private ArtefactoRepository artefactoRepository;
	


    @Transactional
    public ArtefactoEntity addArtefacto(Long comentarioId, Long artefactoId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar el artefacto con id = {0} al comentario con id = {1}", artefactoId, comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        if (comentarioEntity.get().getArtefacto() != null)
            throw new IllegalOperationException("El artefacto ya tiene un comentario asociado");

        comentarioEntity.get().setArtefacto(artefactoEntity.get());
        log.info("Termina proceso de asociar el artefacto con id = {0} al comentario con id = {1}", artefactoId, comentarioId);
        return artefactoEntity.get();
    }
    
    @Transactional
    public ArtefactoEntity getArtefacto(Long comentarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el artefacto del comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        ArtefactoEntity artefactoEntity = comentarioEntity.get().getArtefacto();
        if (artefactoEntity == null)
            throw new EntityNotFoundException("El comentario no tiene artefacto");

        log.info("Termina proceso de consultar el artefacto del comentario con id = {0}", comentarioId);
        return artefactoEntity;
    }
	
    @Transactional
    public ArtefactoEntity replaceArtefacto(Long comentarioId, Long artefactoId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el artefacto del comentario con id = {0}", comentarioId);
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        comentarioEntity.get().setArtefacto(artefactoEntity.get());
        log.info("Termina proceso de actualizar el artefacto del comentario con id = {0}", comentarioId);

        return artefactoEntity.get();
    }


	@Transactional
	public void removeArtefacto(Long comentarioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el artefacto del comentario con id = {0}", comentarioId);
        Optional<ComentarioEntity> comentarioEntity = comentarioRepository.findById(comentarioId);
        if (comentarioEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.COMENTARIO_NOT_FOUND);

        comentarioEntity.get().setArtefacto(null);
        log.info("Termina proceso de borrar el artefacto del comentario con id = {0}", comentarioId);
    }

	

    
}

