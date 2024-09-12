package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.ArtefactoRepository;
import co.edu.uniandes.dse.aitutors.repositories.UsuarioRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArtefactoUsuarioService {
    
    @Autowired
    private ArtefactoRepository artefactoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ArtefactoEntity addArtefacto(Long usuarioId, Long artefactoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de asociar un artefacto al usuario con id = {0}",usuarioId);
        
        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(usuarioId);
        Optional<ArtefactoEntity> artefactoEntity=artefactoRepository.findById(artefactoId);

        if (usuarioEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }

        if (artefactoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);
        }
        artefactoEntity.get().setAutor(usuarioEntity.get());
        log.info("Termina el proceso de asociarle un artefacto a un usuario");
        return artefactoEntity.get();
    }

    @Transactional
    public List<ArtefactoEntity> getArtefactos(Long usuarioId) throws EntityNotFoundException{
        log.info("Inicia el proceso de consultar los artefactos del usuario con id = {0}",usuarioId);
        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }
        log.info("Termina el proceso de consultar todos los libros del autor con id = {0}",usuarioId);
        return usuarioEntity.get().getArtefactos();
    }

    @Transactional
    public ArtefactoEntity getArtefacto(Long usuarioId,Long artefactoId) throws EntityNotFoundException, IllegalOperationException{
        log.info("Inicia el proceso de consultar el artefacto con id = {0} del usuario con id = "+usuarioId,artefactoId);
        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(usuarioId);
        Optional<ArtefactoEntity> artefactoEntity=artefactoRepository.findById(artefactoId);

        if (usuarioEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }

        if (artefactoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);
        }
        log.info("Termina proceso de consultar el artefacto con id = {0} del usuario con id = "+usuarioId,artefactoId);

        if (!artefactoEntity.get().getAutor().equals(usuarioEntity.get())){
            throw new IllegalOperationException("The artefacto is not associated to the user");
        }
        return artefactoEntity.get();
    }

    @Transactional
    public List<ArtefactoEntity> addArtefactos(Long usuarioId,List<ArtefactoEntity> artefactos) throws EntityNotFoundException{
        log.info("Inicia el proceso de reemplazar los artefactos asociados al usuario con id = {0}",usuarioId);
        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(usuarioId);
        if (usuarioEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }

        for (ArtefactoEntity artefacto :artefactos){
            Optional<ArtefactoEntity> artefactoEntity=artefactoRepository.findById(artefacto.getId());
            if (artefactoEntity.isEmpty()){
                throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);
            }

            if (!artefactoEntity.get().getAutor().equals(usuarioEntity.get())){
                artefactoEntity.get().setAutor(usuarioEntity.get());
            }
        }
        log.info("Finaliza el proceso de reemplazar los artefactos asociados al usuario con id = {0}",usuarioId);
        usuarioEntity.get().setArtefactos(artefactos);
        return usuarioEntity.get().getArtefactos();
    }

    @Transactional
    public void removeArtefacto(Long usuarioId,Long artefactoId) throws EntityNotFoundException{
        log.info("Inicia el proceso de borrar un artefacto del usuario con id = {0}",usuarioId);
        Optional<UsuarioEntity> usuarioEntity=usuarioRepository.findById(usuarioId);
        Optional<ArtefactoEntity> artefactoEntity=artefactoRepository.findById(artefactoId);

        if (usuarioEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.USUARIO_NOT_FOUND);
        }

        if (artefactoEntity.isEmpty()){
            throw new EntityNotFoundException(ErrorMessage.ARTEFACTO_NOT_FOUND);
        }
        artefactoEntity.get().setAutor(null);
        usuarioEntity.get().getArtefactos().remove(artefactoEntity.get());
        log.info("Finaliza el proceso de borrar un libro del author con id = {0}",usuarioId);
    }
}
