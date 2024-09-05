package co.edu.uniandes.dse.aitutors.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;

@Service
public class TemaDocumentoService {

    private TemaRepository temaRepository;
    private DocumentoRepository documentoRepository;

    public TemaDocumentoService(TemaRepository temaRepository, DocumentoRepository documentoRepository) {
        this.temaRepository = temaRepository;
        this.documentoRepository = documentoRepository;
    }

    @Transactional
    public DocumentoEntity agregarDocumento(Long temaId, Long documentoId) throws IllegalOperationException {
        try {
            TemaEntity tema = temaRepository.findById(temaId).get();
            DocumentoEntity documento = documentoRepository.findById(temaId).get();
            tema.getDocumentos().add(documento);
            return documento;
        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo agregar el documento al tema");
        }
    }

    @Transactional
    public void eliminarDocumento(Long temaId, Long documentoId) throws IllegalOperationException {
        try {
            TemaEntity tema = temaRepository.findById(temaId).get();
            DocumentoEntity documento = documentoRepository.findById(documentoId).get();
            if (documento.getTema().equals(tema)) {
                documentoRepository.delete(documento);
            } else {
                throw new IllegalOperationException("El documento no pertenece al tema");
            }
        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo eliminar el documento del tema");
        }
    }
}
