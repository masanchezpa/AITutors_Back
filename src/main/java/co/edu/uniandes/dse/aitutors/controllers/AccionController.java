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

import co.edu.uniandes.dse.aitutors.dto.AccionDTO;
import co.edu.uniandes.dse.aitutors.dto.AccionDetailDTO;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.AccionService;

@RestController
@RequestMapping("/acciones")
public class AccionController {

    @Autowired
    private AccionService accionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AccionDetailDTO> findAll() {
        List<AccionEntity> acciones = accionService.getAcciones();
        return modelMapper.map(acciones, new TypeToken<List<AccionDetailDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AccionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        AccionEntity accionEntity = accionService.getAccion(id);
        return modelMapper.map(accionEntity, AccionDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AccionDTO create(@RequestBody AccionDTO accionDTO) throws IllegalOperationException, EntityNotFoundException {
        AccionEntity accionEntity = accionService.createAccion(modelMapper.map(accionDTO, AccionEntity.class));
        return modelMapper.map(accionEntity, AccionDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AccionDTO update(@PathVariable("id") Long id, @RequestBody AccionDTO accionDTO)
            throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accionEntity = accionService.updateAccion(id, modelMapper.map(accionDTO, AccionEntity.class));
        return modelMapper.map(accionEntity, AccionDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        accionService.deleteAccion(id);
    }
}