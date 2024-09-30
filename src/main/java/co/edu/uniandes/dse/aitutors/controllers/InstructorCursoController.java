package co.edu.uniandes.dse.aitutors.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.InstructorDTO;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.services.InstructorCursoService;

@RestController
@RequestMapping("/cursos")
public class InstructorCursoController {

    @Autowired
    private InstructorCursoService instructorCursoService;
    
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value="/{cursoId}/instructor/{instructorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public InstructorDTO addInstructor(@PathVariable("cursoId") Long cursoId, @PathVariable("instructorId") Long instructorId) throws Exception {
        InstructorEntity entity=instructorCursoService.addInstructor(instructorId, cursoId);
        return modelMapper.map(entity, InstructorDTO.class);
    }

    @GetMapping(value="/{cursoId}/instructor")
    @ResponseStatus(code = HttpStatus.OK)
    public InstructorDTO getInstructor(@PathVariable("cursoId") Long cursoId) throws Exception {
        InstructorEntity entity=instructorCursoService.getInstructor(cursoId);
        return modelMapper.map(entity, InstructorDTO.class);
    }

    @PutMapping(value="/{cursoId}/instructor/{instructorId}")
    @ResponseStatus(code = HttpStatus.OK)
    public InstructorDTO replaceInstructor(@PathVariable("cursoId") Long cursoId, @PathVariable("instructorId") Long instructorId) throws Exception {
        InstructorEntity entity=instructorCursoService.replaceInstructor(instructorId, cursoId);
        return modelMapper.map(entity, InstructorDTO.class);
    }

    @DeleteMapping(value="/{cursoId}/instructor/{instructorId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteInstructor(@PathVariable("cursoId") Long cursoId,@PathVariable("instructorId") Long instructorId) throws Exception {
        instructorCursoService.removeInstructor(instructorId,cursoId);
    }

}
