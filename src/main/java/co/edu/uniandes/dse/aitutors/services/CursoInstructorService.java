package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.repositories.InstructorRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CursoInstructorService {
    
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Transactional
    public CursoEntity addCurso(Long instructorId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia proceso de asociarle un curso al tutor con id={0}",instructorId);
        Optional<InstructorEntity> instructorEntity= instructorRepository.findById(instructorId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }

        instructorEntity.get().getCursos().add(cursoEntity.get());
        log.info("Termina proceso de asociarle un curso a un instructor");
        return cursoEntity.get();
    }

    @Transactional
    public List<CursoEntity> getCursos(Long instructorId) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar todos los cursos del instructor");
        Optional<InstructorEntity> instructorEntity= instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar los todos los cursos de un instructor");
        return instructorEntity.get().getCursos();
    }

    @Transactional
    public CursoEntity getCurso(Long instructorId,Long cursoId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia el proceso de consultar el curso con id={0} del instructor con id = "+instructorId,cursoId);
        Optional<InstructorEntity> instructorEntity= instructorRepository.findById(instructorId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar el curso con id={0} del instructor con id = "+instructorId,cursoId);
        if (!cursoEntity.get().getInstructor().equals(instructorEntity.get())){
            throw new IllegalOperationException("El curso no está asociado con el instructor");
        }

        return cursoEntity.get();
    }

    @Transactional
    public List<CursoEntity> addCursos(Long instructorId,List<CursoEntity> cursos) throws EntityNotFoundException{
        log.info("Inicia el proceso de reemplazar los libros asociados al instructor con id = {0}",instructorId);
        Optional<InstructorEntity> instructorEntity= instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        for (CursoEntity curso:cursos){
            Optional<CursoEntity> cursoEntity=cursoRepository.findById(curso.getId());
            if (cursoEntity.isEmpty()){
                throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
            }
            if (!cursoEntity.get().getInstructor().equals(instructorEntity.get())){
                cursoEntity.get().setInstructor(instructorEntity.get());
            }
        }
        log.info("Finaliza proceso de reemplazar los cursos asociados al instructor con id = {0}",instructorId);
        instructorEntity.get().setCursos(cursos);
        return instructorEntity.get().getCursos();
    }

    @Transactional
    public void removeCourse(Long instructorId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de borrar un curso del instructor con id = {0}",instructorId);
        Optional<InstructorEntity> instructorEntity=instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoEntity.get().setInstructor(null);
        instructorEntity.get().getCursos().remove(cursoEntity.get());
        log.info("Finaliza el proceso de borrar un curso del instructor con id = {0}",instructorId);
    }
}
