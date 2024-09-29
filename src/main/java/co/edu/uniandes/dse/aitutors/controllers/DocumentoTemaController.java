package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.TemaDTO;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.DocumentoTemaService;

@RestController
@RequestMapping("/documentos")
public class DocumentoTemaController {

    @Autowired
    private DocumentoTemaService documentoTemaService;  

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping(value="/{documentoId}/tema/{temaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public TemaDTO addTema(@PathVariable("documentoId") Long documentoId, @PathVariable("temaId") Long temaId) throws EntityNotFoundException, IllegalOperationException {
        TemaEntity entity = documentoTemaService.addTema(documentoId, temaId);
        return modelMapper.map(entity, TemaDTO.class);
    }

    @GetMapping(value="/{documentoId}/tema")
    @ResponseStatus(code = HttpStatus.OK)
    public TemaDTO getTema(@PathVariable("documentoId") Long documentoId) throws EntityNotFoundException {
        TemaEntity entity = documentoTemaService.getTema(documentoId);
        return modelMapper.map(entity, TemaDTO.class);
    }

    @PutMapping(value="/{documentoId}/tema/{temaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public TemaDTO updateTema(@PathVariable("documentoId") Long documentoId, @PathVariable("temaId") Long temaId) throws EntityNotFoundException, IllegalOperationException {
        TemaEntity entity = documentoTemaService.replaceTema(documentoId, temaId);
        return modelMapper.map(entity, TemaDTO.class);
    }

    @DeleteMapping(value="/{documentoId}/tema")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeTema(@PathVariable("documentoId") Long documentoId) throws EntityNotFoundException, IllegalOperationException {
        documentoTemaService.removeTema(documentoId);
    }   
}
