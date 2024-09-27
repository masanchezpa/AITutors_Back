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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.ArtefactoDTO;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.ArtefactoAccionService;
import co.edu.uniandes.dse.aitutors.services.ArtefactoService;
import co.edu.uniandes.dse.aitutors.services.ArtefactoUsuarioService;

@RestController
@RequestMapping("/usuario")
public class ArtefactoUsuarioController {

    @Autowired
    private ArtefactoUsuarioService artefactoUsuarioService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró el usuario con id " + id;
    }

    @GetMapping("/{usuarioId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArtefactoDTO> getArtefactos(@PathVariable("usuarioId") Long usuarioId) throws EntityNotFoundException {

        List<ArtefactoEntity> entities = artefactoUsuarioService.getArtefactos(usuarioId);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron artefactos para el usuario con id " + usuarioId);
        }

        return modelMapper.map(entities, new TypeToken<List<ArtefactoDTO>>() {
		}.getType());
    }

    @GetMapping("/{usuarioId}/artefactos/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> getArtefacto(@PathVariable("usuarioId") Long usuarioId,@PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException {
        ArtefactoEntity entity = artefactoUsuarioService.getArtefacto(usuarioId, id);


        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }
    
    @PostMapping("/{usuarioId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> addArtefacto(@PathVariable("usuarioId") Long usuarioId,@PathVariable("id")  Long id) throws EntityNotFoundException, IllegalOperationException{
        ArtefactoEntity entity = artefactoUsuarioService.addArtefacto(usuarioId, id);
        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    @PostMapping("/{usuarioId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> addArtefactos(@PathVariable("usuarioId") Long usuarioId,@RequestBody List<ArtefactoEntity> artefactos) throws EntityNotFoundException, IllegalOperationException{
        List<ArtefactoEntity> entity = artefactoUsuarioService.addArtefactos(usuarioId,artefactos);
        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    @DeleteMapping("/{usuarioId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeArtefacto(@PathVariable("usuarioId") Long usuarioId,@PathVariable("id")  Long id) throws EntityNotFoundException {
        artefactoUsuarioService.removeArtefacto(usuarioId, id);
    }
}
