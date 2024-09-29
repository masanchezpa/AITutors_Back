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

import co.edu.uniandes.dse.aitutors.dto.EstudianteDTO;
import co.edu.uniandes.dse.aitutors.dto.EstudianteDetailDTO;
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.EstudianteService;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EstudianteDetailDTO> findAll() {
        List<EstudianteEntity> estudiantes = estudianteService.getEstudiantes();
        return modelMapper.map(estudiantes, new TypeToken<List<EstudianteDetailDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        EstudianteEntity estudianteEntity = estudianteService.getEstudiante(id);
        return modelMapper.map(estudianteEntity, EstudianteDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EstudianteDTO create(@RequestBody EstudianteDTO estudianteDTO) throws IllegalOperationException, EntityNotFoundException {
        EstudianteEntity estudianteEntity = estudianteService.createEstudiante(modelMapper.map(estudianteDTO, EstudianteEntity.class));
        return modelMapper.map(estudianteEntity, EstudianteDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO update(@PathVariable("id") Long id, @RequestBody EstudianteDTO estudianteDTO)
            throws EntityNotFoundException, IllegalOperationException {
        EstudianteEntity estudianteEntity = estudianteService.updateEstudiante(id, modelMapper.map(estudianteDTO, EstudianteEntity.class));
        return modelMapper.map(estudianteEntity, EstudianteDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        estudianteService.deleteEstudiante(id);
    }
}