package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.ComentarioDTO;
import co.edu.uniandes.dse.aitutors.dto.DocumentoDTO;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.TemaDocumentoService;

@RestController
@RequestMapping("/temas/{temaId}/documentos")
public class TemaDocumentoController {
    @Autowired
    private TemaDocumentoService temaDocumentoService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el documento con id " + id;
    }

    @PostMapping("/{documentoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<DocumentoDTO> agregarDocumento(@PathVariable("documentoId") Long documentoId, @PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException {
        DocumentoEntity entity = temaDocumentoService.agregarDocumento(documentoId,id);
        return ResponseEntity.ok(modelMapper.map(entity, DocumentoDTO.class));
    }

    @DeleteMapping("/{documentoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void eliminarDocumento(@PathVariable("documentoId") Long documentoId,@PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException {
        temaDocumentoService.eliminarDocumento(id,documentoId);
    }
}
