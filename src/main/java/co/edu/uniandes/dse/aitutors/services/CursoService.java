package co.edu.uniandes.dse.aitutors.services;

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
    @Transactional
    public CursoEntity creaCurso(CursoEntity entity) throws IllegalOperationException {

        Optional<CursoEntity> alreadyExist = cursoRepository.findById(entity.getId());

        if(!alreadyExist.isPresent()) {
            throw new IllegalOperationException("Ya existe una compañía con ese nombre");
        } else {
            return cursoRepository.save(entity);
        }
    }
    /*
     * Obtiene la lista de cursos
     * @param cursoId
     * @param temaId
     * @return List<CursoEntity>
     */
    @Transactional
    public void agregarTema(long cursoId, long temaId) throws IllegalOperationException {
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
            }
        }
    }

    /*
     * Obtiene la lista de cursos
     * @param cursoId
     * @param temaId
     * @return List<CursoEntity>
     */
    @Transactional
    public void eliminarTema(long cursoId, long temaId) throws IllegalOperationException {
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
            }
        }
    }
}
