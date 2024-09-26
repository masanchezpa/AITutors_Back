package co.edu.uniandes.dse.aitutors.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.repositories.TutorIARepository;
import jakarta.transaction.Transactional;

@Service

public class TutorIAService {

    @Autowired
    private TutorIARepository repositorioTutor;

    @Transactional
    public void agregarAccion(AccionEntity accion, TutorIAEntity tutor) {
        tutor.getAcciones().add(accion);
        repositorioTutor.save(tutor);
    }

    @Transactional
    public void eliminarAccion(AccionEntity accion, TutorIAEntity tutor) {
        tutor.getAcciones().remove(accion);
        repositorioTutor.save(tutor);
    }


    @Transactional
    public TutorIAEntity createTutor(TutorIAEntity tutor) {
        return repositorioTutor.save(tutor);
    }

        @Transactional
    public List<TutorIAEntity> getTutores() {
        return repositorioTutor.findAll();
    }

    @Transactional
    public TutorIAEntity getTutor(Long tutorid) throws EntityNotFoundException {
        Optional<TutorIAEntity> tutorEntidad = repositorioTutor.findById(tutorid);
        if (tutorEntidad.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);
        }
        return tutorEntidad.get();
    }

    @Transactional
    public TutorIAEntity updateTutor(Long tutorid, TutorIAEntity tutor) throws EntityNotFoundException {
        Optional<TutorIAEntity> tutorEntidad = repositorioTutor.findById(tutorid);
        if (!tutorEntidad.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);
        }

        tutor.setId(tutorid);

        return repositorioTutor.save(tutor);
    }

    @Transactional
    public void deleteTutor(Long tutorid) throws EntityNotFoundException {
        Optional<TutorIAEntity> tutorEntidad = repositorioTutor.findById(tutorid);
        if (!tutorEntidad.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.TUTORIA_NOT_FOUND);
        }
        tutorEntidad.get().getAcciones().clear();
        repositorioTutor.save(tutorEntidad.get());
        repositorioTutor.deleteById(tutorid);
    }


}
