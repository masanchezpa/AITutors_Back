package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;


import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad Artefacto y Accion
 *
 */
@Slf4j
@Service

public class ArtefactoAccionService {

	@Autowired
	private AccionRepository accionRepository;

	@Autowired
	private ArtefactoRepository artefactoRepository;
	


    @Transactional
    public AccionEntity addAccion(Long artefactoId, Long accionId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar la accion con id = {0} al artefacto con id = {1}", accionId, artefactoId);
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        if (artefactoEntity.get().getAccion() != null)
            throw new IllegalOperationException("El artefacto ya tiene una accion asociada");

        artefactoEntity.get().setAccion(accionEntity.get());
        log.info("Termina proceso de asociar la accion con id = {0} al artefacto con id = {1}", accionId, artefactoId);
        return accionEntity.get();
    }


    @Transactional
    public AccionEntity getAccion(Long artefactoId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la accion del artefacto con id = {0}", artefactoId);
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        AccionEntity accionEntity = artefactoEntity.get().getAccion();
        if (accionEntity == null)
            throw new EntityNotFoundException("El artefacto no tiene accion");

        log.info("Termina proceso de consultar la accion del artefacto con id = {0}", artefactoId);
        return accionEntity;
    }
    
    @Transactional
    public AccionEntity replaceAccion(Long artefactoId, Long accionId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar la accion del artefacto con id = {0}", artefactoId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        artefactoEntity.get().setAccion(accionEntity.get());
        log.info("Termina proceso de actualizar la accion del artefacto con id = {0}", artefactoId);

        return accionEntity.get();
    }
	
    @Transactional
    public void removeAccion(Long artefactoId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar la accion del artefacto con id = {0}", artefactoId);
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefactoId);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);

        artefactoEntity.get().setAccion(null);
        log.info("Termina proceso de borrar la accion del artefacto con id = {0}", artefactoId);
    }

	

    
}

