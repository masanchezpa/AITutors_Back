package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.AccionDTO;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.services.AccionTutorIAService;


@RestController
@RequestMapping("/tutorias/")
public class AccionTutorIAController {

    @Autowired
    private AccionTutorIAService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("{idTutor}/acciones/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AccionDTO getAccionesTutorIA(@PathVariable("idTutor") Long idTutor,@PathVariable("id") Long id) throws EntityNotFoundException {

        AccionEntity entity = service.getAccion(id);

        return modelMapper.map(entity, AccionDTO.class);

    }

    @GetMapping("{idTutor}/acciones")
    @ResponseStatus(code = HttpStatus.OK)
    public List<AccionDTO> getAccionesTutorIA(@PathVariable("idTutor") Long idTutor) throws EntityNotFoundException {

        List<AccionEntity> entities = service.getAcciones(idTutor);

        return modelMapper.map(entities, new TypeToken<List<AccionDTO>>() {
		}.getType());
    }

    





}
