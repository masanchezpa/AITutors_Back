package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
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

    private static final String ARTEFACTO_NOT_FOUND = "Artefacto not found";

    @Transactional
    public ArtefactoEntity crearArtefacto(ArtefactoEntity artefactoEntity) throws IllegalOperationException,AssertionFailedError{
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

    @Transactional
    public List<ArtefactoEntity> getArtefactos(Long accionId) {

        return artefactoRepository.findByAccionId(accionId);
    }

    @Transactional
    public ArtefactoEntity getArtefacto(Long accionId, Long id) throws EntityNotFoundException {

        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(id);

        if (artefactoEntity.isEmpty()) {
            throw new EntityNotFoundException(ARTEFACTO_NOT_FOUND);
        }

        return artefactoEntity.get();
    }

    @Transactional
    public ArtefactoEntity updateArtefacto(Long accionId, ArtefactoEntity artefacto) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el artefacto con id = {0}", artefacto.getId());
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(artefacto.getId());
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ARTEFACTO_NOT_FOUND);

        artefactoEntity.get().setContenido(artefacto.getContenido());
        log.info("Termina proceso de actualizar el artefacto con id = {0}", artefacto.getId());
        return artefactoEntity.get();
    }

    @Transactional
    public void deleteArtefacto(Long accionId, Long id) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el artefacto con id = {0}", id);
        Optional<ArtefactoEntity> artefactoEntity = artefactoRepository.findById(id);
        if (artefactoEntity.isEmpty())
            throw new EntityNotFoundException(ARTEFACTO_NOT_FOUND);

        artefactoRepository.delete(artefactoEntity.get());
        log.info("Termina proceso de borrar el artefacto con id = {0}", id);
    }




}

