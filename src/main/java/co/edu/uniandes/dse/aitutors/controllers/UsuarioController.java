package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.UsuarioDTO;
import co.edu.uniandes.dse.aitutors.dto.UsuarioResponseDTO;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) throws IllegalOperationException {
        UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);
        UsuarioEntity newUsuario = usuarioService.createUsuario(usuario);
        return modelMapper.map(newUsuario, UsuarioDTO.class);

    }

    @PutMapping("/{id}")
    public UsuarioDTO updateUsuario(@RequestBody UsuarioDTO usuarioDTO,@PathVariable("id") Long id) throws EntityNotFoundException {
        UsuarioEntity usuario = modelMapper.map(usuarioDTO, UsuarioEntity.class);
        UsuarioEntity newUsuario = usuarioService.updateUsuario(id, usuario);
        return modelMapper.map(newUsuario, UsuarioDTO.class);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuario(@PathVariable("id") Long id) throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioService.getUsuario(id);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @GetMapping("/login/{email}")
    public UsuarioResponseDTO getUserByEmail(@PathVariable("email") String email) throws EntityNotFoundException {
        UsuarioResponseDTO userResponse = usuarioService.getUserWithDtypeByEmail(email);
        return modelMapper.map(userResponse, UsuarioResponseDTO.class);
    }
    

    @GetMapping("")
    public List<UsuarioDTO> getUsuarios() {
        List<UsuarioEntity> usuarios = usuarioService.getUsuarios();
        return modelMapper.map(usuarios, new TypeToken<List<UsuarioDTO>>() {
        }.getType());
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable("id") Long id) throws EntityNotFoundException {
        usuarioService.deleteUsuario(id);
    }

}
