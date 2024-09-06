package co.edu.uniandes.dse.aitutors.services;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.repositories.EstudianteRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class EstudianteService {
    
    @Autowired
    EstudianteRepository estudianteRepository;

    @Transactional
    public EstudianteEntity createEstudiante(EstudianteEntity estudiante) {
        log.info("Creating a new estudiante");
        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public EstudianteEntity getEstudiante(Long estudianteId) throws EntityNotFoundException {
        log.info("Retrieving the estudiante with id = {0}", estudianteId);
        Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
        if (estudianteEntity.isEmpty()) {
            throw new EntityNotFoundException("There is no estudiante with id = " + estudianteId);
        }
        return estudianteEntity.get();
    }

    @Transactional
    public List<EstudianteEntity> getEstudiantes() {
        log.info("Retrieving all estudiantes");
        return estudianteRepository.findAll();
    }

    @Transactional
    public EstudianteEntity updateEstudiante(Long estudianteId, EstudianteEntity estudiante) throws EntityNotFoundException {
        log.info("Updating the estudiante with id = {0}", estudianteId);
        Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
        if (!estudianteEntity.isPresent()) {
            throw new EntityNotFoundException("There is no estudiante with id = " + estudianteId);
        }
        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public void deleteEstudiante(Long estudianteId) throws EntityNotFoundException {
        log.info("Deleting the estudiante with id = {0}", estudianteId);
        Optional<EstudianteEntity> estudianteEntity = estudianteRepository.findById(estudianteId);
        if (!estudianteEntity.isPresent()) {
            throw new EntityNotFoundException("There is no estudiante with id = " + estudianteId);
        }
        estudianteRepository.deleteById(estudianteId);
    }


}
