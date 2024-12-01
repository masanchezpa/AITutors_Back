package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;


import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad Artefacto y Accion
 *
 */
@Slf4j
@Service

public class TemaCursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TemaRepository temaRepository;
	
    @Transactional
    public CursoEntity addCurso(Long temaId, Long cursoId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar el curso con id = {0} al tema con id = {1}", cursoId, temaId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        
        Optional<CursoEntity> cursoEntity = cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        
        if (temaEntity.get().getCurso() != null)
            throw new IllegalOperationException("El tema ya tiene un curso asociado");
        
        temaEntity.get().setCurso(cursoEntity.get());
        log.info("Termina proceso de asociar el curso con id = {0} al tema con id = {1}", cursoId, temaId);
        return cursoEntity.get();
    }



    @Transactional
    public CursoEntity getCurso(Long temaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el curso del tema con id = {0}", temaId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        
        CursoEntity cursoEntity = temaEntity.get().getCurso();
        if (cursoEntity == null)
            throw new EntityNotFoundException("El tema no tiene curso");

        log.info("Termina proceso de consultar el curso del tema con id = {0}", temaId);
        return cursoEntity;
    }

    @Transactional
    public List<TemaEntity> getTemas(Long cursoId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el curso del tema con id = {0}", cursoId);
        Optional<CursoEntity> cursoEntity = cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        
        
        log.info("Termina el proceso de consultar todos los temas de un curso");
        
        return cursoEntity.get().getTemas();
    }



    @Transactional
    public CursoEntity replaceCurso(Long temaId, Long cursoId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el curso del tema con id = {0}", temaId);
        Optional<CursoEntity> cursoEntity = cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);

        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        
        temaEntity.get().setCurso(cursoEntity.get());
        log.info("Termina proceso de actualizar el curso del tema con id = {0}", temaId);

        return cursoEntity.get();

    }


    @Transactional
    public void removeCurso(Long temaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el curso del tema con id = {0}", temaId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        
        temaEntity.get().setCurso(null);
        log.info("Termina proceso de borrar el curso del tema con id = {0}", temaId);
    }
    	

    
}

