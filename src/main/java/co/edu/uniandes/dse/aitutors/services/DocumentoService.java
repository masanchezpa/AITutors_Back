package co.edu.uniandes.dse.aitutors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;

@Service
public class DocumentoService {
    @Autowired
    private DocumentoRepository documentoRepository;

    public String mostrarContenido (Long id) throws IllegalOperationException {
        try {

            String contenido = documentoRepository.findById(id).get().getContenido();

            if (!contenido.isEmpty()) {
                return contenido;
            }
            else {
                return "";
            }

        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo mostrar el contenido del documento");
        }
    }
}
