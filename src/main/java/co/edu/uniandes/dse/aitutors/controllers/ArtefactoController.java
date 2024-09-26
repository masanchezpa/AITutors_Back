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

@RestController
@RequestMapping("/acciones")

public class ArtefactoController {

    @Autowired
    private ArtefactoService artefactoService;

    @Autowired
    private ArtefactoAccionService artefactoAccionService;

    @Autowired
    private ModelMapper modelMapper;

    public String getErrorMessage(Long id) {
        return "No se encontró la acción con id " + id;
    }

    @GetMapping("/{accionId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArtefactoDTO> getArtefactos(@PathVariable("accionId") Long accionId) throws EntityNotFoundException, IllegalOperationException {

        List<ArtefactoEntity> entities = artefactoService.getArtefactos(accionId);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron artefactos para la accion con id " + accionId);
        }

        return modelMapper.map(entities, new TypeToken<List<ArtefactoDTO>>() {
		}.getType());
    }

    @GetMapping("/{accionId}/artefactos/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> getArtefacto(@PathVariable("accionId") Long accionId,@PathVariable("id")  Long id) throws EntityNotFoundException {
        ArtefactoEntity entity = artefactoService.getArtefacto(accionId, id);


        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    @PutMapping("/{accionId}/artefactos/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> updateArtefacto(@PathVariable("accionId") Long accionId,@PathVariable("id")  Long id, @RequestBody ArtefactoDTO artefacto) throws EntityNotFoundException {
        ArtefactoEntity entity = modelMapper.map(artefacto, ArtefactoEntity.class);
        entity.setId(id);
        entity = artefactoService.updateArtefacto(accionId, entity);
        artefactoAccionService.replaceAccion(id, accionId);

        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    @PostMapping("/{accionId}/artefactos")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ArtefactoDTO> createArtefacto(@PathVariable("accionId") Long accionId, @RequestBody ArtefactoDTO artefacto) throws EntityNotFoundException, IllegalOperationException {
        ArtefactoEntity entity = modelMapper.map(artefacto, ArtefactoEntity.class);
        entity = artefactoService.crearArtefacto(entity);
        System.out.println("llego aca con id "+entity.getId() + " y tipo "+entity.getTipo());
        artefactoAccionService.addAccion(entity.getId(), accionId);

        return ResponseEntity.ok(modelMapper.map(entity, ArtefactoDTO.class));
    }

    @DeleteMapping("/{accionId}/artefactos/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteArtefacto(@PathVariable("accionId") Long accionId,@PathVariable("id")  Long id) throws EntityNotFoundException {
        artefactoAccionService.removeAccion(id);
        artefactoService.deleteArtefacto(accionId, id);
    }


}
