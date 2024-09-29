package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.services.DocumentoService;
import co.edu.uniandes.dse.aitutors.dto.DocumentoDTO;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {
    
    @Autowired
    private DocumentoService documentoService;
    
    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el documento con id " + id;
    }

}
