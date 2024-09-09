package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.EstudianteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class CursoEstudianteService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Transactional
    public CursoEntity addCurso(Long estudianteId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia proceso de asociarle un curso al estudiante con id={0}",estudianteId);
        Optional<EstudianteEntity> estudianteEntity=estudianteRepository.findById(estudianteId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException("ESTUDIANTE NOT FOUND");
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException("CURSO NOT FOUND");
        }

        estudianteEntity.get().getCursos().add(cursoEntity.get());
        log.info("Termina proceso de asociarle un curso a un estudiante");
        return cursoEntity.get();
    }

    @Transactional
    public List<CursoEntity> getCursos(Long estudianteId) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar todos los cursos del estudiante");
        Optional<EstudianteEntity> estudianteEntity= estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException("ESTUDIANTE NOT FOUND");
        }
        log.info("Termina el proceso de consultar los todos los cursos de un estudiante");
        return estudianteEntity.get().getCursos();
    }

    @Transactional
    public CursoEntity getCurso(Long estudianteId,Long cursoId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia el proceso de consultar el curso con id={0} del estudiante con id = "+estudianteId,cursoId);
        Optional<EstudianteEntity> estudianteEntity= estudianteRepository.findById(estudianteId);
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException("ESTUDIANTE NOT FOUND");
        }

        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException("CURSO NOT FOUND");
        }
        log.info("Termina el proceso de consultar el curso con id={0} del estudiante con id = "+estudianteId,cursoId);
        if (!cursoEntity.get().getEstudiantes().contains(estudianteEntity.get())){
            throw new IllegalOperationException("El curso no está asociado con el estudiante");
        }

        return cursoEntity.get();
    }

    @Transactional
    public List<CursoEntity> addCursos(Long estudianteId,List<CursoEntity> cursos) throws EntityNotFoundException{
        log.info("Inicia el proceso de reemplazar los cursos asociados al estudiante con id = {0}",estudianteId);
        Optional<EstudianteEntity> estudianteEntity= estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException("ESTUDIANTE NOT FOUND");
        }
        for (CursoEntity curso:cursos){
            Optional<CursoEntity> cursoEntity=cursoRepository.findById(curso.getId());
            if (cursoEntity.isEmpty()){
                throw new EntityNotFoundException("CURSO NOT FOUND");
            }
            if (!cursoEntity.get().getEstudiantes().contains(estudianteEntity.get())){
                cursoEntity.get().getEstudiantes().add(estudianteEntity.get());
            }
        }
        log.info("Finaliza proceso de reemplazar los cursos asociados al estudiante con id = {0}",estudianteId);
        estudianteEntity.get().setCursos(cursos);
        return estudianteEntity.get().getCursos();
    }

    @Transactional
    public void removeCourse(Long estudianteId,Long cursoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de borrar un curso del estudiante con id = {0}",estudianteId);
        Optional<EstudianteEntity> estudianteEntity=estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty()){
            throw new EntityNotFoundException("ESTUDIANTE NOT FOUND");
        }
        Optional<CursoEntity> cursoEntity=cursoRepository.findById(cursoId);
        if (cursoEntity.isEmpty()){
            throw new EntityNotFoundException("CURSO NOT FOUND");
        }
        cursoEntity.get().getEstudiantes().remove(estudianteEntity.get());
        estudianteEntity.get().getCursos().remove(cursoEntity.get());
        log.info("Finaliza el proceso de borrar un curso del estudiante con id = {0}",estudianteId);
    }
}
