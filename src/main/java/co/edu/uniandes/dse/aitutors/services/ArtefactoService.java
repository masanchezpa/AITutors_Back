package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArtefactoService {

    @Autowired
    ArtefactoRepository artefactoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional
    public ArtefactoEntity crearArtefacto(ArtefactoEntity artefactoEntity) throws IllegalOperationException{
        log.info("Creating a new artefact");
        
        if (artefactoEntity.getTipo().isBlank()){
            throw new IllegalOperationException("Artefact must have a type");
        }

        if (artefactoEntity.getContenido().isBlank()){
            throw new IllegalOperationException("Artefact must have a type");
        }

        if (artefactoEntity.getAutor()==null){
            throw new IllegalOperationException("Author is not valid");
        }

        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(artefactoEntity.getAutor().getId());
        if (usuarioEntity.isEmpty()){
                throw new IllegalOperationException("Author is not valid");
        }
        
        artefactoEntity.setAutor(usuarioEntity.get());
        log.info("End of creation of artefact");
        return artefactoRepository.save(artefactoEntity);
    }

    @Transactional
    public ArtefactoEntity modificarContenido(String contenido,ArtefactoEntity artefactoEntity) throws IllegalOperationException{
        log.info("Modifying artefact");

        if (contenido.isBlank()){
            throw new IllegalOperationException("Blank content not valid");
        }

        artefactoEntity.setContenido(contenido);
        log.info("End of modification of artefact");
        return artefactoRepository.save(artefactoEntity);
    }
}   

