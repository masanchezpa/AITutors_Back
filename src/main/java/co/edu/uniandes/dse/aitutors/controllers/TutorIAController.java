package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
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

import co.edu.uniandes.dse.aitutors.dto.TutorIADTO;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.services.TutorIAService;

import org.modelmapper.TypeToken;
import java.util.List;

@RestController
@RequestMapping("/tutorias")
public class TutorIAController {

    @Autowired
    private TutorIAService service;

    @Autowired
    private ModelMapper modelMapper;

    private String getErrorMessage(Long id) {
        return "No se encontro el tutorIA con id " + id;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<TutorIADTO> createTutorIA(@RequestBody TutorIADTO tutorIADTO) {
        TutorIAEntity entity = modelMapper.map(tutorIADTO, TutorIAEntity.class);
        TutorIAEntity newEntity = service.createTutor(entity);
        return new ResponseEntity<>(modelMapper.map(newEntity, TutorIADTO.class), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<TutorIADTO> updateTutorIA(@RequestBody TutorIADTO tutorIADTO, @PathVariable("id") Long id) throws EntityNotFoundException {
        TutorIAEntity entity = modelMapper.map(tutorIADTO, TutorIAEntity.class);
        TutorIAEntity newEntity = service.updateTutor(id, entity);
        return new ResponseEntity<>(modelMapper.map(newEntity, TutorIADTO.class), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TutorIADTO getTutorIA(@PathVariable("id") Long id) throws EntityNotFoundException {
        TutorIAEntity entity = service.getTutor(id);
        if (entity == null) {
            throw new EntityNotFoundException(getErrorMessage(id));
        }
        return modelMapper.map(entity, TutorIADTO.class);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TutorIADTO> getTutorIAS() {
        List<TutorIAEntity> entities = service.getTutores();
        return modelMapper.map(entities, new TypeToken<List<TutorIADTO>>() {
        }.getType());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteTutorIA(@PathVariable("id") Long id) throws EntityNotFoundException {
        service.deleteTutor(id);
    }

}
