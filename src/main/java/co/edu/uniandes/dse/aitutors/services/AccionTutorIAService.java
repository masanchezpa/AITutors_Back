package co.edu.uniandes.dse.aitutors.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import co.edu.uniandes.dse.aitutors.repositories.TutorIARepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service

public class AccionTutorIAService {

	@Autowired
	private AccionRepository accionRepository;

	@Autowired
	private TutorIARepository tutorIARepository;



    @Transactional
    public TutorIAEntity addTutorIA(Long accionId, Long tutorIAId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar el tutorIA con id = {0} al accion con id = {1}", tutorIAId, accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        Optional<TutorIAEntity> tutorIAEntity = tutorIARepository.findById(tutorIAId);//No entiendo porque no funciona el findByID cuando extiende de BaseEntity
        if (tutorIAEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);

        if (accionEntity.get().getTutorIA() != null)
            throw new IllegalOperationException("El accion ya tiene un tutorIA asociado");

        accionEntity.get().setTutorIA(tutorIAEntity.get());
        log.info("Termina proceso de asociar el tutorIA con id = {0} al accion con id = {1}", tutorIAId, accionId);
        return tutorIAEntity.get();
    }

    @Transactional
    @Modifying
    public AccionEntity createAccion(AccionEntity accion) {
        log.info("Inicia proceso de creación de accion");
        AccionEntity newAccion = accionRepository.save(accion);
        log.info("Termina proceso de creación de accion");
        return newAccion;
    }

    @Transactional
    public AccionEntity getAccion(Long accionId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el accion con id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        log.info("Termina proceso de consultar el accion con id = {0}", accionId);
        return accionEntity.get();
    }

    @Transactional
    public List<AccionEntity> getAcciones(Long idTutor) {
        log.info("Inicia proceso de consultar todos los accions de un tutor");
        List<AccionEntity> accions = accionRepository.findByTutorIAId(idTutor);
        log.info("Termina proceso de consultar todos los accions de un tutor");
        return accions;

    }

    @Transactional
    public AccionEntity updateAccion(Long accionId, Long tutorIAId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el accion con id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        Optional<TutorIAEntity> tutorIAEntity = tutorIARepository.findById(tutorIAId);
        if (tutorIAEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);

        accionEntity.get().setTutorIA(tutorIAEntity.get());
        log.info("Termina proceso de actualizar el accion con id = {0}", accionId);
        return accionEntity.get();
    }

    @Transactional
    public TutorIAEntity getTutorIA(Long accionId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el tutorIA del accion con id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        TutorIAEntity tutorIAEntity = accionEntity.get().getTutorIA();
        if (tutorIAEntity == null)
            throw new EntityNotFoundException("El accion no tiene tutorIA");

        log.info("Termina proceso de consultar el tutorIA del accion con id = {0}", accionId);
        return tutorIAEntity;
    }


    @Transactional
    public TutorIAEntity replaceTutorIA(Long accionId, Long tutorIAId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el tutorIA del accion con id = {0}", accionId);
        Optional<TutorIAEntity> tutorIAEntity = tutorIARepository.findById(tutorIAId);
        if (tutorIAEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);

        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        accionEntity.get().setTutorIA(tutorIAEntity.get());
        log.info("Termina proceso de actualizar el tutorIA del accion con id = {0}", accionId);

        return tutorIAEntity.get();
    }


    @Transactional
    public void removeTutorIA(Long accionId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el tutorIA del accion con id = {0}", accionId);
        Optional<AccionEntity> accionEntity = accionRepository.findById(accionId);
        if (accionEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.ACCION_NOT_FOUND);

        accionEntity.get().setTutorIA(null);
        log.info("Termina proceso de borrar el tutorIA del accion con id = {0}", accionId);
    }

    @Transactional
    public boolean tutorIAExists(Long tutorIAId) {
        return tutorIARepository.existsById(tutorIAId);
    }

}

