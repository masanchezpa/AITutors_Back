package co.edu.uniandes.dse.aitutors.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;


import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad Artefacto y Accion
 *
 */
@Slf4j
@Service

public class DocumentoTemaService {

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private TemaRepository temaRepository;
	

    @Transactional
    public TemaEntity addTema(Long documentoId, Long temaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asociar el tema con id = {0} al documento con id = {1}", temaId, documentoId);
        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if (documentoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.DOCUMENTO_NOT_FOUND);
        
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        
        if (documentoEntity.get().getTema() != null)
            throw new IllegalOperationException("El documento ya tiene un tema asociado");
        
        documentoEntity.get().setTema(temaEntity.get());
        log.info("Termina proceso de asociar el tema con id = {0} al documento con id = {1}", temaId, documentoId);
        return temaEntity.get();
    }


    @Transactional
    public TemaEntity getTema(Long documentoId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el tema del documento con id = {0}", documentoId);
        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if (documentoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.DOCUMENTO_NOT_FOUND);
        
        TemaEntity temaEntity = documentoEntity.get().getTema();
        if (temaEntity == null)
            throw new EntityNotFoundException("El documento no tiene tema");

        log.info("Termina proceso de consultar el tema del documento con id = {0}", documentoId);
        return temaEntity;
    }


    @Transactional
    public TemaEntity replaceTema(Long documentoId, Long temaId) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el tema del documento con id = {0}", documentoId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);

        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if (documentoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.DOCUMENTO_NOT_FOUND);
        
        documentoEntity.get().setTema(temaEntity.get());
        log.info("Termina proceso de actualizar el tema del documento con id = {0}", documentoId);

        return temaEntity.get();

    }
    
    @Transactional
    public void removeTema(Long documentoId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el tema del documento con id = {0}", documentoId);
        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if (documentoEntity.isEmpty())
            throw new EntityNotFoundException(ErrorMessage.DOCUMENTO_NOT_FOUND);
        
        documentoEntity.get().setTema(null);
        log.info("Termina proceso de borrar el tema del documento con id = {0}", documentoId);
    }
	


    
}

