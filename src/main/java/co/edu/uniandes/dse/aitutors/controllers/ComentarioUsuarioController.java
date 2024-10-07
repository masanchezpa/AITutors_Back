package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.aitutors.dto.ComentarioDTO;
import co.edu.uniandes.dse.aitutors.dto.UsuarioDTO;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.services.ComentarioUsuarioService;

@RestController
@RequestMapping("/comentarios/{comentarioId}/usuarios")
public class ComentarioUsuarioController {

    @Autowired
    private ComentarioUsuarioService comentarioUsuarioService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/{usuarioId}")
    public UsuarioDTO addUsario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        UsuarioEntity usuario = comentarioUsuarioService.addUsuario(comentarioId, usuarioId);
        return modelMapper.map(usuario, UsuarioDTO.class);}


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public UsuarioDTO getUsuario(@PathVariable("comentarioId") Long comentarioId) throws EntityNotFoundException {
        UsuarioEntity usuario = comentarioUsuarioService.getUsuario(comentarioId);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO replaceUsuario(@PathVariable("comentarioId") Long comentarioId, @PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        UsuarioEntity usuario = comentarioUsuarioService.replaceUsuario(comentarioId, usuarioId);
        return modelMapper.map(usuario, UsuarioDTO.class);
        
    }

    @DeleteMapping
    public void removeUsuario(@PathVariable("comentarioId") Long comentarioId) throws EntityNotFoundException {
        comentarioUsuarioService.removeUsuario(comentarioId);
    }

    @DeleteMapping("/{usuarioId}")
    public void removeComentario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        comentarioUsuarioService.removeComentario(usuarioId);
    }

    @GetMapping("/{usuarioId}/comentario")
    public ComentarioDTO getComentario(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioUsuarioService.getComentario(usuarioId);
        return modelMapper.map(comentario, ComentarioDTO.class);
    }






  
}
