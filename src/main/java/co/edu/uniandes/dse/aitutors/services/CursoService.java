package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.InstructorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Transactional
    public CursoEntity createCurso(CursoEntity curso) throws IllegalOperationException {
        log.info("Creating a new course");

        if (curso.getDescripcion().isBlank()) {
            throw new IllegalOperationException("Curso must have a description");
        }

        if (curso.getInstructor() == null) {
            throw new IllegalOperationException("Curso must have an instructor");
        }

        if (curso.getNombre().isBlank()) {
            throw new IllegalOperationException("Curso must have a name");
        }

        Optional<InstructorEntity> instructorEntity = instructorRepository.findById(curso.getInstructor().getId());

        if (instructorEntity.isEmpty()) {
            throw new IllegalOperationException("Instructor does not exist");
        }
        curso.setInstructor(instructorEntity.get());
        log.info("End of creation of curso");
        return cursoRepository.save(curso);
    }

    @Transactional
    public List<CursoEntity> getCursos() {
        log.info("Retrieving all courses");
        return cursoRepository.findAll();
    }

    @Transactional
    public CursoEntity getCurso(Long cursoId) throws EntityNotFoundException {
        log.info("Retrieving the course with id = {0}", cursoId);
        Optional<CursoEntity> cursoEntity = cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        return cursoEntity.get();
    }

    @Transactional
    public CursoEntity updateCurso(Long cursoId, CursoEntity curso) throws EntityNotFoundException, IllegalOperationException {
        log.info("Updating the course with id = {0}", cursoId);
        Optional<CursoEntity> cursoEntity = cursoRepository.findById(cursoId);
        if (!cursoEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }

        if (curso.getDescripcion().isBlank()) {
            throw new IllegalOperationException("Curso must have a description");
        }

        if (curso.getInstructor() == null) {
            throw new IllegalOperationException("Curso must have an instructor");
        }

        if (curso.getNombre().isBlank()) {
            throw new IllegalOperationException("Curso must have a name");
        }

        Optional<InstructorEntity> instructorEntity = instructorRepository.findById(curso.getInstructor().getId());

        if (instructorEntity.isEmpty()) {
            throw new IllegalOperationException("Instructor does not exist");
        }
        curso.setInstructor(instructorEntity.get());
        return cursoRepository.save(curso);
    }

    @Transactional
    public void deleteCurso(Long cursoId) throws EntityNotFoundException {
        log.info("Deleting the course with id = {0}", cursoId);
        Optional<CursoEntity> curso = cursoRepository.findById(cursoId);
        if (!curso.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoRepository.deleteById(cursoId);
    }
}