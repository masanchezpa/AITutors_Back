package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.repositories.EstudianteRepository;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


//instructor

@Slf4j
@Service
public class EstudianteCursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Transactional
    public EstudianteEntity addEstudiante(Long estudianteId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia proceso de agregar un estudiante al curso con id={0}",cursoId);
        Optional<EstudianteEntity> estudianteEntity=estudianteRepository.findById(estudianteId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        cursoEntity.get().getEstudiantes().add(estudianteEntity.get());
        log.info("Termina el proceso de agregar un estudiante a un curso");
        return estudianteEntity.get();
    }

    @Transactional
    public List<EstudianteEntity> getEstudiantes(Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar todos los estudiantes del curso");
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar todos los estudiantes de un curso");
        return cursoEntity.get().getEstudiantes();
    }

    @Transactional
    public EstudianteEntity getCurso(Long estudianteId,Long cursoId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia el proceso de consultar el estudiante con id={0} del curso con id = "+cursoId,estudianteId);
        Optional<EstudianteEntity> estudianteEntity= estudianteRepository.findById(estudianteId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar el estudiante con id={0} del curso con id = "+cursoId,estudianteId);
        if (!estudianteEntity.get().getCursos().contains(cursoEntity.get())){
            throw new IllegalOperationException("El curso no está asociado con el estudiante");
        }

        return estudianteEntity.get();
    }

    @Transactional
    public List<EstudianteEntity> addEstudiantes(Long cursoId,List<EstudianteEntity> estudiantes) throws EntityNotFoundException{
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        for (EstudianteEntity estudiante : estudiantes){
            Optional<EstudianteEntity> estudianteEntity=estudianteRepository.findById(estudiante.getId());
            if (estudianteEntity.isEmpty()){
                throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
            }
            if (!estudianteEntity.get().getCursos().contains(cursoEntity.get())){
                estudianteEntity.get().getCursos().add(cursoEntity.get());
            }
        }
        log.info("Finaliza proceso de reemplazar los estudiante asociados al curso con id = {0}",cursoId);
        cursoEntity.get().setEstudiantes(estudiantes);
        return cursoEntity.get().getEstudiantes();
    }

    @Transactional
    public void removeStudent(Long estudianteId, Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de borrar un estudiante del curso con id = {0}",cursoId);
        Optional<EstudianteEntity> estudianteEntity=estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ESTUDIANTE_NOT_FOUND);
        }
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.CURSO_NOT_FOUND);
        }
        estudianteEntity.get().getCursos().remove(cursoEntity.get());
        cursoEntity.get().getEstudiantes().remove(estudianteEntity.get());
        log.info("Finaliza el proceso de borrar un estudiante del curso con id = {0}",cursoId);
    }
}
