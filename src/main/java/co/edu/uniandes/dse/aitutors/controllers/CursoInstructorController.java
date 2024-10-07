package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.aitutors.dto.CursoDTO;
import co.edu.uniandes.dse.aitutors.dto.InstructorDTO;
import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.CursoEstudianteService;
import co.edu.uniandes.dse.aitutors.services.CursoInstructorService;

@RestController
@RequestMapping("instructores/{instructorId}/cursos")
public class CursoInstructorController {
    

    @Autowired
    private CursoInstructorService cursoInstructorService ;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("{cursoId}")
    public InstructorDTO addCurso(@PathVariable("instructorId") Long instructorId, @PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        CursoEntity curso = cursoInstructorService.addCurso(cursoId, instructorId);
        return modelMapper.map(curso, InstructorDTO.class);

    }

    @GetMapping
    public List<CursoDTO> getCursos(@PathVariable("instructorId") Long instructorId) throws EntityNotFoundException {
        List<CursoEntity> cursos = cursoInstructorService.getCursos(instructorId);
        return modelMapper.map(cursos, new TypeToken<List<CursoDTO>>() {
        }.getType());
    }

    @GetMapping("{cursoId}")
    public CursoDTO getCursos(@PathVariable("cursoId") Long cursoId, @PathVariable("instructorId") Long instructorId) throws EntityNotFoundException, IllegalOperationException {
        CursoEntity curso = cursoInstructorService.getCurso(instructorId, cursoId);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @PutMapping
    public List<CursoDTO> addCursos(@PathVariable("instructorId") Long instructorId, @RequestBody List<CursoDTO> listaCursos) throws EntityNotFoundException {
        List<CursoEntity> listacursoentidades = modelMapper.map(listaCursos, new TypeToken<List<CursoEntity>>() {}.getType());
        List<CursoEntity> cursosnuevos = cursoInstructorService.addCursos(instructorId, listacursoentidades);
        return modelMapper.map(cursosnuevos, new TypeToken<List<CursoDTO>>() {}.getType());

    }

    @DeleteMapping("{cursoId}")
    public void removeCourse(@PathVariable("instructorId") Long instructorId, @PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        cursoInstructorService.removeCourse(instructorId, cursoId);
    }


}
