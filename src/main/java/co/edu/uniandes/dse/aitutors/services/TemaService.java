package co.edu.uniandes.dse.aitutors.services;

import static org.mockito.ArgumentMatchers.refEq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;

@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    public List<DocumentoEntity> getDocumentos(Long temaId) throws IllegalOperationException {
        try {
            TemaEntity tema = temaRepository.findById(temaId).get();
            return tema.getDocumentos();
        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo obtener los documentos del tema");
        }
    }


}
