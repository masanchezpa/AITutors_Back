package co.edu.uniandes.dse.aitutors.services;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.InstructorRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TemaRepository temaRepository;

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



    @Transactional
    public CursoEntity agregarTema(long cursoId, long temaId) throws IllegalOperationException {
        Optional<CursoEntity> curso = cursoRepository.findById(cursoId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);

        if(!curso.isPresent()) {
            throw new IllegalOperationException("No existe un curso con ese id");
        } else {
            if(!temaEntity.isPresent()) {
                throw new IllegalOperationException("No existe un tema con ese id");
            } else {
                curso.get().getTemas().add(temaEntity.get());
                cursoRepository.save(curso.get());
                return curso.get();
            }
        }
        
        curso.getTemas().add(tema);
        cursoRepository.save(curso);
    }
    

  

    @Transactionals
    public CursoEntity eliminarTema(long cursoId, long temaId) throws IllegalOperationException {
        Optional<CursoEntity> curso = cursoRepository.findById(cursoId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);

        if(!curso.isPresent()) {
            throw new IllegalOperationException("No existe un curso con ese id");
        } else {
            if(!temaEntity.isPresent()) {
                throw new IllegalOperationException("No existe un tema con ese id");
            } else {
                curso.get().getTemas().remove(temaEntity.get());
                cursoRepository.save(curso.get());
                return curso.get();
            }
        }
    
        curso.getTemas().remove(tema);
        cursoRepository.save(curso);
    }
}
