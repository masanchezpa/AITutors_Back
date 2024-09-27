package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.ComentarioDTO;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.UsuarioComentarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/comentarios")
public class UsuarioComentarioController {
    
    @Autowired
    private UsuarioComentarioService usuarioComentarioService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el comentario con id " + id;
    }

    // Agregar comentario a usuario
    @PostMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ComentarioDTO> addComentario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity entity = usuarioComentarioService.addComentario(comentarioId, usuarioId);
        return ResponseEntity.ok(modelMapper.map(entity, ComentarioDTO.class));
    }

    // Obtener todos los comentarios de un usuario
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ComentarioDTO> getComentarios(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        List<ComentarioEntity> entities = usuarioComentarioService.getComentarios(usuarioId);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron comentarios para el usuario con id " + usuarioId);
        }

        return modelMapper.map(entities, new TypeToken<List<ComentarioDTO>>() {}.getType());
    }

    // Obtener un comentario específico de un usuario
    @GetMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity entity = usuarioComentarioService.getComentario(comentarioId, usuarioId);
        return ResponseEntity.ok(modelMapper.map(entity, ComentarioDTO.class));
    }

    // Reemplazar todos los comentarios de un usuario
    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<ComentarioDTO>> replaceComentarios(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<ComentarioDTO> comentarios) throws EntityNotFoundException {
        List<ComentarioEntity> comentarioEntities = modelMapper.map(comentarios, new TypeToken<List<ComentarioEntity>>() {}.getType());
        List<ComentarioEntity> updatedEntities = usuarioComentarioService.replaceComentarios(usuarioId, comentarioEntities);
        return ResponseEntity.ok(modelMapper.map(updatedEntities, new TypeToken<List<ComentarioDTO>>() {}.getType()));
    }
}
