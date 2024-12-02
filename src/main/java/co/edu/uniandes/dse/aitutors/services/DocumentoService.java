package co.edu.uniandes.dse.aitutors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;
    private static final String DOCUMENTO_NO_ENCONTRADO = "El documento con el id dado no existe";

    @Transactional
    public DocumentoEntity createDocumento(DocumentoEntity documentoEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación del documento");
        if(documentoEntity.getTipo().isBlank()){
            throw new IllegalOperationException("El tipo del documento no puede ser vacío");
        }

        if(documentoEntity.getContenido().isBlank()){
            throw new IllegalOperationException("El contenido del documento no puede ser vacío");
        }

        log.info("Termina proceso de creación del documento");
        return documentoRepository.save(documentoEntity);
    }


    @Transactional
    public List<DocumentoEntity> getDocumentos() {
        log.info("Inicia proceso de consultar todos los documentos");
        return documentoRepository.findAll();
    }

    @Transactional
    public DocumentoEntity getDocumento(Long documentoId) throws EntityNotFoundException{
        log.info("Inicia proceso de consultar un documento con id = {0}", documentoId);

        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if(documentoEntity.isEmpty()){
            throw new EntityNotFoundException(DOCUMENTO_NO_ENCONTRADO);
        }
        return documentoEntity.get();
    }

    @Transactional 
    public DocumentoEntity updateDocumento(Long documentoId,DocumentoEntity documento) throws IllegalOperationException {
        log.info("Inicia proceso de actualizar un documento con id = {0}", documentoId);
        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if(documentoEntity.isEmpty()){
            throw new IllegalOperationException(DOCUMENTO_NO_ENCONTRADO);
        }

        documento.setId(documentoId);
        log.info("Termina proceso de actualización del documento");
        return documentoRepository.save(documento);
    }

    @Transactional
    public void deleteDocumento(Long documentoId) throws IllegalOperationException {
        log.info("Inicia proceso de eliminación del documento con id = {0}", documentoId);
        Optional<DocumentoEntity> documentoEntity = documentoRepository.findById(documentoId);
        if(documentoEntity.isEmpty()){
            throw new IllegalOperationException(DOCUMENTO_NO_ENCONTRADO);
        }

        documentoRepository.deleteById(documentoId);
        log.info("Termina proceso de eliminación del documento con id = {0}", documentoId);
    }
}
