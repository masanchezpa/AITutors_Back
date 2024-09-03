package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    @Transactional
    public CursoEntity creaCursoEntity(CursoEntity entity) throws IllegalOperationException {

        Optional<CursoEntity> alreadyExist = repository.findById(entity.getId());

        if(!alreadyExist.isPresent()) {
            throw new IllegalOperationException("Ya existe una compañía con ese nombre");
        } else {
            return repository.save(entity);
        }
    }

}
