package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.services.DocumentoService;

import co.edu.uniandes.dse.aitutors.dto.DocumentoDTO;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;

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

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DocumentoDTO createDocumento(@RequestBody DocumentoDTO documento) throws IllegalOperationException {
        DocumentoEntity entity = modelMapper.map(documento, DocumentoEntity.class);
        DocumentoEntity newEntity = documentoService.createDocumento(entity);
        return modelMapper.map(newEntity, DocumentoDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DocumentoDTO> getDocumentos() {
        List<DocumentoEntity> entities = documentoService.getDocumentos();
        return modelMapper.map(entities, new TypeToken<List<DocumentoDTO>>() {
        }.getType());
    }

    @GetMapping(value="/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DocumentoDTO getDocumento(@PathVariable("id") Long id) throws EntityNotFoundException {
        DocumentoEntity entity = documentoService.getDocumento(id);
        return modelMapper.map(entity, DocumentoDTO.class);
    }

    @PutMapping (value="/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DocumentoDTO update(@PathVariable("id") Long id,@RequestBody DocumentoDTO documento) throws IllegalOperationException {
        DocumentoEntity entity = modelMapper.map(documento, DocumentoEntity.class);
        entity = documentoService.updateDocumento(id, entity);
        return modelMapper.map(entity, DocumentoDTO.class);
    }

    @DeleteMapping(value="/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws IllegalOperationException {
        documentoService.deleteDocumento(id);
    }
    


    
}
