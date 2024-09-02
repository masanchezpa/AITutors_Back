package co.edu.uniandes.dse.aitutors.services;


import static org.mockito.ArgumentMatchers.anyChar;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class AccionService {
    
    @Autowired
     AccionRepository accionRepository;


    @Transactional
    public AccionEntity createAccion(AccionEntity accion) {
        log.info("Creating a new accion");
        return accionRepository.save(accion);
    }

    @Transactional
    public List<AccionEntity> getAcciones() {
        log.info("Retrieving all acciones");
        return accionRepository.findAll();
    }


    @Transactional
    public AccionEntity getAccion(Long accionId) throws EntityNotFoundException {
        log.info("Retrieving the accion with id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty()) {
            throw new EntityNotFoundException("There is no accion with id = " + accionId);
        }
        return accionEntity.get();
    }

    @Transactional
    public AccionEntity updateAccion(Long accionId, AccionEntity accion) throws EntityNotFoundException {
        log.info("Updating the accion with id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (!accionEntity.isPresent()) {
            throw new EntityNotFoundException("There is no accion with id = " + accionId);
        }
        return accionRepository.save(accion);
    }

    @Transactional
    public void deleteAccion(Long accionId) throws EntityNotFoundException {
        log.info("Deleting the accion with id = {0}", accionId);
        Optional<AccionEntity> accion = accionRepository.findById(accionId);
        if (!accion.isPresent()) {
            throw new EntityNotFoundException("There is no accion with id = " + accionId);
        }
        accionRepository.deleteById(accionId);
    }




}
