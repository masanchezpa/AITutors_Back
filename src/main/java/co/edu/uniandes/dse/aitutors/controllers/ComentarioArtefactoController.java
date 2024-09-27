package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.ArtefactoDTO;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.ComentarioArtefactoService;

@RestController
@RequestMapping("/comentarios/{comentarioId}/artefactos")
public class ComentarioArtefactoController {

    @Autowired
    private ComentarioArtefactoService comentarioArtefactoService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el comentario con id " + id;
    }

    // Agregar un artefacto a un comentario
    @PostMapping("/{artefactoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> addArtefacto(@PathVariable("comentarioId") Long comentarioId, @PathVariable("artefactoId") Long artefactoId) throws EntityNotFoundException, IllegalOperationException {
        ArtefactoEntity entity = comentarioArtefactoService.addArtefacto(comentarioId, artefactoId);
        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    // Reemplazar un artefacto en un comentario
    @PutMapping("/{artefactoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> replaceArtefacto(@PathVariable("comentarioId") Long comentarioId, @PathVariable("artefactoId") Long artefactoId) throws EntityNotFoundException {
        ArtefactoEntity entity = comentarioArtefactoService.replaceArtefacto(comentarioId, artefactoId);
        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    // Obtener el artefacto asociado a un comentario
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> getArtefacto(@PathVariable("comentarioId") Long comentarioId) throws EntityNotFoundException, IllegalOperationException {
        ArtefactoEntity entity = comentarioArtefactoService.getArtefacto(comentarioId);
        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    // Eliminar el artefacto asociado a un comentario
    @DeleteMapping("/{artefactoId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeArtefacto(@PathVariable("comentarioId") Long comentarioId, @PathVariable("artefactoId") Long artefactoId) throws EntityNotFoundException {
        comentarioArtefactoService.removeArtefacto(comentarioId, artefactoId);
    }
}
