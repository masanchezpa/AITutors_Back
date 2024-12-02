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

import co.edu.uniandes.dse.aitutors.dto.InstructorDTO;
import co.edu.uniandes.dse.aitutors.dto.InstructorDetailDTO;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.services.InstructorService;

@RestController
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<InstructorDetailDTO> findAll() {
        List<InstructorEntity> instructores = instructorService.getInstructors();
        return modelMapper.map(instructores, new TypeToken<List<InstructorDetailDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public InstructorDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        InstructorEntity instructorEntity = instructorService.getInstructor(id);
        return modelMapper.map(instructorEntity, InstructorDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public InstructorDTO create(@RequestBody InstructorDTO instructorDTO)  {
        InstructorEntity instructorEntity = instructorService.createInstructor(modelMapper.map(instructorDTO, InstructorEntity.class));
        return modelMapper.map(instructorEntity, InstructorDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public InstructorDTO update(@PathVariable("id") Long id, @RequestBody InstructorDTO instructorDTO)
            throws EntityNotFoundException {
        InstructorEntity instructorEntity = instructorService.updateInstructor(id, modelMapper.map(instructorDTO, InstructorEntity.class));
        return modelMapper.map(instructorEntity, InstructorDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        instructorService.deleteInstructor(id);
    }
}