package co.edu.uniandes.dse.aitutors.services;

import static org.mockito.ArgumentMatchers.refEq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;
    private DocumentoRepository documentoRepository;

    @Transactional
    public DocumentoEntity agregarDocumento(Long temaId, DocumentoEntity documento) throws IllegalOperationException {
        try {
            TemaEntity tema = temaRepository.findById(temaId).get();
            documento.setTema(tema);
            return documentoRepository.save(documento);
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
