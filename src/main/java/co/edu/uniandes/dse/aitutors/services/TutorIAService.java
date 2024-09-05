package co.edu.uniandes.dse.aitutors.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
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
    public TutorIAEntity crateTutor(TutorIAEntity tutor) {
        return repositorioTutor.save(tutor);
    }

        @Transactional
    public List<TutorIAEntity> getTutores() {
        return repositorioTutor.findAll();
    }

    @Transactional
    public TutorIAEntity getTutor(Long tutorid) throws EntityNotFoundException {
        Optional<TutorIAEntity> TutorEntidad = repositorioTutor.findById(tutorid);
        if (TutorEntidad.isEmpty()) {
            throw new EntityNotFoundException("No se encontro tutor con el id: " + tutorid);
        }
        return TutorEntidad.get();
    }

    @Transactional
    public TutorIAEntity updateTutor(Long tutorid, TutorIAEntity tutor) throws EntityNotFoundException {
        Optional<TutorIAEntity> TutorEntidad = repositorioTutor.findById(tutorid);
        if (!TutorEntidad.isPresent()) {
            throw new EntityNotFoundException("No se encontro tutor con el id: " + tutorid);
        }
        return repositorioTutor.save(tutor);
    }

    @Transactional
    public void deleteTutor(Long tutorid) throws EntityNotFoundException {
        Optional<TutorIAEntity> TutorEntidad = repositorioTutor.findById(tutorid);
        if (!TutorEntidad.isPresent()) {
            throw new EntityNotFoundException("No se encontro tutor con el id: " + tutorid);
        }
        repositorioTutor.deleteById(tutorid);
    }


}   