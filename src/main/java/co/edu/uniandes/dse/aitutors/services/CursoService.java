package co.edu.uniandes.dse.aitutors.services;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TemaRepository temaRepository;

    /*
     * Crea un curso
     * @param entity
     * @return CursoEntity
     */

    //@Transactional
    //public CursoEntity creaCurso(CursoEntity entity) throws IllegalOperationException {

    //    Optional<CursoEntity> alreadyExist = cursoRepository.findById(entity.getId());

    //    if(!alreadyExist.isPresent()) {
    //        throw new IllegalOperationException("Ya existe una compañía con ese nombre");
    //    } else {
    //        return cursoRepository.save(entity);
    //    }
    //}

    @Transactional
    public CursoEntity creaCurso(CursoEntity curso) throws IllegalOperationException {
        if (curso.getId() != null) {
            throw new IllegalOperationException("El curso ya existe");
        }
        return cursoRepository.save(curso);
    }

    /*
     * Obtiene la lista de cursos
     * @param cursoId
     * @param temaId
     * @return List<CursoEntity>
     */

    
    //@Transactional
    //public void agregarTema(long cursoId, long temaId) throws IllegalOperationException {
    //    Optional<CursoEntity> curso = cursoRepository.findById(cursoId);
    //    Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);

    //    if(!curso.isPresent()) {
    //        throw new IllegalOperationException("No existe un curso con ese id");
    //    } else {
    //        if(!temaEntity.isPresent()) {
    //            throw new IllegalOperationException("No existe un tema con ese id");
    //        } else {
    //            curso.get().getTemas().add(temaEntity.get());
    //            cursoRepository.save(curso.get());
    //        }
    //    }
    //}

    @Transactional
    public void agregarTema(Long cursoId, Long temaId) throws IllegalOperationException {
        if (cursoId == null || temaId == null) {
            throw new IllegalOperationException("Curso ID o Tema ID no puede ser null");
        }
        Optional<CursoEntity> cursoOpt = cursoRepository.findById(cursoId);
        Optional<TemaEntity> temaOpt = temaRepository.findById(temaId);
        
        if (!cursoOpt.isPresent()) {
            throw new IllegalOperationException("Curso no encontrado");
        }
        if (!temaOpt.isPresent()) {
            throw new IllegalOperationException("Tema no encontrado");
        }
        
        CursoEntity curso = cursoOpt.get();
        TemaEntity tema = temaOpt.get();
        
        if (curso.getTemas() == null) {
            curso.setTemas(new ArrayList<>());
        }
        
        curso.getTemas().add(tema);
        cursoRepository.save(curso);
    }
    

    /*
     * Obtiene la lista de cursos
     * @param cursoId
     * @param temaId
     * @return List<CursoEntity>
     */
    
    //@Transactional
    //public void eliminarTema(long cursoId, long temaId) throws IllegalOperationException {
    //    Optional<CursoEntity> curso = cursoRepository.findById(cursoId);
    //    Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);

    //    if(!curso.isPresent()) {
    //        throw new IllegalOperationException("No existe un curso con ese id");
    //    } else {
    //        if(!temaEntity.isPresent()) {
    //            throw new IllegalOperationException("No existe un tema con ese id");
    //        } else {
    //            curso.get().getTemas().remove(temaEntity.get());
    //            cursoRepository.save(curso.get());
    //        }
    //    }
    //}

    @Transactional
    public void eliminarTema(Long cursoId, Long temaId) throws EntityNotFoundException, IllegalOperationException {
        Optional<CursoEntity> cursoOpt = cursoRepository.findById(cursoId);
        if (!cursoOpt.isPresent()) {
            throw new EntityNotFoundException("El curso no existe");
        }
    
        CursoEntity curso = cursoOpt.get();
        Optional<TemaEntity> temaOpt = temaRepository.findById(temaId);
        if (!temaOpt.isPresent()) {
            throw new EntityNotFoundException("El tema no existe");
        }
    
        TemaEntity tema = temaOpt.get();
        if (!curso.getTemas().contains(tema)) {
            throw new IllegalOperationException("El curso no contiene el tema a eliminar");
        }
    
        curso.getTemas().remove(tema);
        cursoRepository.save(curso);
    }
}
