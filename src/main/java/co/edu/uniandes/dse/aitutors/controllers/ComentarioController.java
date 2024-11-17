package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

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

import co.edu.uniandes.dse.aitutors.dto.ComentarioDTO;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.ComentarioService;
import co.edu.uniandes.dse.aitutors.services.UsuarioService;
import co.edu.uniandes.dse.aitutors.services.ArtefactoService;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ArtefactoService artefactoService;



    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ComentarioDTO> findAll() {
        List<ComentarioEntity> comentarios = comentarioService.getComentarios();
        return modelMapper.map(comentarios, new TypeToken<List<ComentarioDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioService.getComentario(id);
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ComentarioDTO createComentario(@RequestBody ComentarioDTO comentarioDTO) throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity comentarioEntity = modelMapper.map(comentarioDTO, ComentarioEntity.class);
        ComentarioEntity savedEntity = comentarioService.createComentario(comentarioEntity);
        return modelMapper.map(savedEntity, ComentarioDTO.class);
    }
    


    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ComentarioDTO update(@PathVariable("id") Long id, @RequestBody ComentarioDTO comentarioDTO)
            throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity comentarioEntity = comentarioService.updateComentario(id, modelMapper.map(comentarioDTO, ComentarioEntity.class));
        return modelMapper.map(comentarioEntity, ComentarioDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        comentarioService.deleteComentario(id);
    }

    
}
