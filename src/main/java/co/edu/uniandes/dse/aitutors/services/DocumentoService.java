package co.edu.uniandes.dse.aitutors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;

    public String mostrarContenido () throws IllegalOperationException {
        try {
            return documentoRepository.findById(1L).get().getContenido();
        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo mostrar el contenido del documento");
        }
    }
}
