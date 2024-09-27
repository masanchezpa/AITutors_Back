package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.ArtefactoDTO;
import co.edu.uniandes.dse.aitutors.dto.ComentarioDTO;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.UsuarioComentarioService;

@RestController
@RequestMapping("/comentario")
public class UsuarioComentarioController {
    
    @Autowired
    private UsuarioComentarioService usuarioComentarioService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el comentario con id " + id;
    }

    @GetMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ComentarioDTO> addComentario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity entity = usuarioComentarioService.addComentario(comentarioId,id);
        return ResponseEntity.ok(modelMapper.map(entity, ComentarioDTO.class));
    }

    @GetMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ComentarioEntity> getComentarios(@PathVariable("comentarioId") Long comnetarioId) throws EntityNotFoundException {

        List<ComentarioEntity> entities = usuarioComentarioService.getComentarios(comnetarioId);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron comentarios para el usuario con id " + comnetarioId);
        }

        return modelMapper.map(entities, new TypeToken<List<ComentarioDTO>>() {
		}.getType());
    }

    @GetMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ComentarioDTO> getComentario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity entity = usuarioComentarioService.getComentario(comentarioId,id);
        return ResponseEntity.ok(modelMapper.map(entity, ComentarioDTO.class));
    }

    @PutMapping("/{comentarioId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ComentarioDTO> replaceComentarios(@PathVariable Long usuarioId, @RequestBody List<ComentarioEntity> comentarios) throws EntityNotFoundException{
        List<ComentarioEntity> entity = usuarioComentarioService.replaceComentario(usuarioId, comentarios);
        return ResponseEntity.ok(modelMapper.map(entity, ComentarioDTO.class));
    }
}
