package co.edu.uniandes.dse.aitutors.controllers;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.TypeToken;
import co.edu.uniandes.dse.aitutors.dto.CursoDTO;
import co.edu.uniandes.dse.aitutors.dto.EstudianteDTO;
import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.ComentarioService;
import co.edu.uniandes.dse.aitutors.services.CursoEstudianteService;

@RestController("/estudiantes/{estudianteId}/cursos")
public class CursoEstudianteController {

    @Autowired
    private CursoEstudianteService cursoEstudianteService ;


    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/{cursoId}")
    public CursoDTO updateCurso(@PathVariable("cursoId") Long cursoId, @PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException {
        CursoEntity curso = cursoEstudianteService.addCurso(estudianteId, cursoId);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @GetMapping
    public List<CursoDTO> allCursos(@PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException {
        List<CursoEntity> cursos = cursoEstudianteService.getCursos(estudianteId);
        return modelMapper.map(cursos, new TypeToken<List<CursoDTO>>() {
        }.getType());
    }

    @GetMapping("/{cursoId}")
    public CursoDTO getCurso(@PathVariable("cursoId") Long cursoId, @PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException, IllegalOperationException {
        CursoEntity curso = cursoEstudianteService.getCurso(estudianteId, cursoId);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @PutMapping
    public List<CursoDTO> addCursos(@PathVariable("estudianteId") Long estudianteId,@RequestBody List<CursoDTO> ListascursosDTO) throws EntityNotFoundException {
        List<CursoEntity> cursosEntities = modelMapper.map(ListascursosDTO, new TypeToken<List<CursoEntity>>() {}.getType());
        List<CursoEntity> CursosNuevos = cursoEstudianteService.addCursos(estudianteId, cursosEntities);
        return modelMapper.map(CursosNuevos, new TypeToken<List<CursoDTO>>() {}.getType());
    }

    @DeleteMapping("/{cursoId}")
    public void removeCursos(@PathVariable("estudianteId") Long estudianteId, @PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        cursoEstudianteService.removeCourse(estudianteId, cursoId);
    }




    
}
