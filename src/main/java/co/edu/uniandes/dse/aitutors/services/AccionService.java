package co.edu.uniandes.dse.aitutors.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class AccionService {
    
    @Autowired
     AccionRepository accionRepository;


    @Transactional
    public AccionEntity createAccion(AccionEntity accion) {
        log.info("Creating a new accion");
        return accionRepository.save(accion);
    }


}
