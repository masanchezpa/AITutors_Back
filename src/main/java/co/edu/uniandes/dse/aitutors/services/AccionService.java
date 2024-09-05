package co.edu.uniandes.dse.aitutors.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;

@Service

public class AccionService {

    @Autowired
    private AccionRepository accionRepository;
    private ArtefactoRepository repositorioArtefacto;


    @Autowired
    public AccionService(ArtefactoRepository repositorioArtefacto, AccionRepository repositorio) {
        this.accionRepository = repositorio;
        this.repositorioArtefacto = repositorioArtefacto;
    }

    public ArtefactoEntity ejecutarAccion(AccionEntity accion) {

        
        ArtefactoEntity artefacto = new ArtefactoEntity();
        artefacto.setAccion(accion);

        repositorioArtefacto.save(artefacto);


        return artefacto;
        

    }
    

}