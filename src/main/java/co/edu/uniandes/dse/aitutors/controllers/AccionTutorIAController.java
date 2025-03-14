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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.AccionDTO;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.AccionTutorIAService;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/tutorias/")
public class AccionTutorIAController {

    @Autowired
    private AccionTutorIAService service;

    @Autowired
    private ModelMapper modelMapper;


    private String getErrorMessage(Long id) {
        return "No se el tutorIA con id " + id;
    }

    @GetMapping("{idTutor}/acciones/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AccionDTO getAccionesTutorIA(@PathVariable("idTutor") Long idTutor,@PathVariable("id") Long id) throws EntityNotFoundException {

        AccionEntity entity = service.getAccion(id);

        if (entity == null) {
            throw new EntityNotFoundException("No se encontró la acción con id " + id);
        }

        return modelMapper.map(entity, AccionDTO.class);

    }

    @GetMapping("{idTutor}/acciones")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AccionDTO> getAccionesTutorIA(@PathVariable("idTutor") Long idTutor) throws EntityNotFoundException {
        List<AccionEntity> entities = service.getAcciones(idTutor);

        if (entities.isEmpty()) {
            throw new EntityNotFoundException(getErrorMessage(idTutor));
        }

        return modelMapper.map(entities, new TypeToken<List<AccionDTO>>() {
		}.getType());
    }

    @PostMapping("{idTutor}/acciones")
    @ResponseStatus(code = HttpStatus.OK)
    public AccionDTO createAccion(@RequestBody AccionDTO accionDTO, @PathVariable("idTutor") Long idTutor) throws EntityNotFoundException, IllegalOperationException {

        if (!service.tutorIAExists(idTutor)) {
            throw new EntityNotFoundException(getErrorMessage(idTutor));
        }

        AccionEntity accionEntity = modelMapper.map(accionDTO, AccionEntity.class);
        AccionEntity newAccion = service.createAccion(accionEntity);
        service.addTutorIA(newAccion.getId(), idTutor);
        return modelMapper.map(newAccion, AccionDTO.class);
    }

    @PutMapping("{idTutor}/acciones/{id}")
    public AccionDTO updateAccion(@PathVariable("id") Long id, @PathVariable("idTutor") Long idTutor) throws EntityNotFoundException {

        if (!service.tutorIAExists(idTutor)) {
            throw new EntityNotFoundException(getErrorMessage(idTutor));
        }

        AccionEntity entity = service.updateAccion(id, idTutor);

        return modelMapper.map(entity, AccionDTO.class);
    }

    @DeleteMapping("{idTutor}/acciones/{id}")
    public ResponseEntity<String> deleteAccion(@PathVariable("id") Long id, @PathVariable("idTutor") Long idTutor) throws EntityNotFoundException {

        if (!service.tutorIAExists(idTutor)) {
            throw new EntityNotFoundException(getErrorMessage(idTutor));
        }

        service.removeTutorIA(id);
        return new ResponseEntity<>("Accion eliminada", HttpStatus.OK);
    }

}
