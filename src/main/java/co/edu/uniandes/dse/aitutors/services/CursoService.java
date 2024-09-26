package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
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

    /*
     * Crea un curso
     * @param entity
     * @return CursoEntity
     */

    @Transactional
    public CursoEntity creaCurso(CursoEntity curso) throws IllegalOperationException {
        log.info("Creating a new course");

        if (curso.getDescripcion().isBlank()){
            throw new IllegalOperationException("Curso must have a description");
        }

        if (curso.getInstructor()==null){
            throw new IllegalOperationException("Curso must have an instructor");
        }

        if (curso.getNombre().isBlank()){
            throw new IllegalOperationException("Curso must have a name");
        }

        Optional<InstructorEntity> instructorEntity=instructorRepository.findById(curso.getInstructor().getId());

        if (instructorEntity.isEmpty()){
            throw new IllegalOperationException("Instructor does not exist");
        }
        curso.setInstructor(instructorEntity.get());
        log.info("End of creation of curso");
        return cursoRepository.save(curso);
    }



}
