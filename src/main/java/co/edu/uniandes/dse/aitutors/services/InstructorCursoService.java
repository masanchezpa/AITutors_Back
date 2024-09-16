package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.repositories.InstructorRepository;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


//instructor

@Slf4j
@Service
public class InstructorCursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstructorRepository instructorRepository;
    
    @Transactional
    public InstructorEntity addInstructor(Long instructorId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia proceso de agregar un instructor al curso con id={0}",cursoId);
        Optional<InstructorEntity> instructorEntity=instructorRepository.findById(instructorId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoEntity.get().setInstructor(instructorEntity.get());
        log.info("Termina el proceso de agregar un instructor a un curso");
        return instructorEntity.get();
    }
     
    @Transactional
    public InstructorEntity getInstructor(Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar el instructor del curso con id = {0}",cursoId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar el instructor del curso con id = {0}",cursoId);
        return cursoEntity.get().getInstructor();
    }

    @Transactional
    public InstructorEntity replaceInstructor(Long instructorId, Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de reemplazar el instructor del curso con id = {0}",cursoId);
        Optional<InstructorEntity> instructorEntity=instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoEntity.get().setInstructor(instructorEntity.get());
        log.info("Finaliza el proceso de reemplazar el instructor del curso con id = {0}",cursoId);
        return instructorEntity.get();
    }

    @Transactional
    public void removeInstructor(Long instructorId, Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de borrar un instructor del curso con id = {0}",cursoId);
        Optional<InstructorEntity> instructorEntity=instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoEntity.get().setInstructor(null);
        log.info("Finaliza el proceso de borrar un instructor del curso con id = {0}",cursoId);
    }


}
