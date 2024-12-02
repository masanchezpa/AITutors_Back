package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

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

import co.edu.uniandes.dse.aitutors.dto.DocumentoDTO;
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

    // Agregar un documento a un tema
    @PostMapping("/{documentoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<DocumentoDTO> agregarDocumento(@PathVariable("temaId") Long temaId, @PathVariable("documentoId") Long documentoId) throws EntityNotFoundException, IllegalOperationException {
        DocumentoEntity entity = temaDocumentoService.agregarDocumento(temaId, documentoId);
        return ResponseEntity.ok(modelMapper.map(entity, DocumentoDTO.class));
    }

    // Eliminar un documento de un tema
    @DeleteMapping("/{documentoId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarDocumento(@PathVariable("temaId") Long temaId, @PathVariable("documentoId") Long documentoId) throws EntityNotFoundException, IllegalOperationException {
        temaDocumentoService.eliminarDocumento(temaId, documentoId);
    }

    //Obtener elementos de un tema
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DocumentoDTO> getDocumentos(@PathVariable("temaId") Long temaId) throws IllegalOperationException {
        List<DocumentoEntity> entities = temaDocumentoService.getDocumentosByTema(temaId);
        return modelMapper.map(entities, new org.modelmapper.TypeToken<List<DocumentoDTO>>() {
        }.getType());
    }

    @GetMapping(value = "/categoria/{tipo}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DocumentoDTO> getDocumentosByCategoria(@PathVariable("tipo") String tipo) {
        List<DocumentoEntity> entities = temaDocumentoService.getDocumentosByCategoria(tipo);
        return modelMapper.map(entities, new org.modelmapper.TypeToken<List<DocumentoDTO>>() {
        }.getType());
    }
}
