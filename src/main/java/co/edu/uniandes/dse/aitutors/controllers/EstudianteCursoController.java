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
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.services.EstudianteCursoService;

@RestController
@RequestMapping("/cursos")
public class EstudianteCursoController {

    @Autowired
    private EstudianteCursoService estudianteCursoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value="/{cursoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO addEstudiante(@PathVariable("cursoId") Long cursoId, @PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException {
        EstudianteEntity entity = estudianteCursoService.addEstudiante(estudianteId, cursoId);
        return modelMapper.map(entity, EstudianteDTO.class);
    }

    @GetMapping(value="/{cursoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EstudianteDTO getEstudiante(@PathVariable("cursoId") Long cursoId,@PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException {
        EstudianteEntity entity = estudianteCursoService.getEstudiante(cursoId,estudianteId);
        return modelMapper.map(entity, EstudianteDTO.class);
    }

    @GetMapping(value="/{cursoId}/estudiantes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EstudianteDTO> getEstudiantes(@PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        List<EstudianteEntity> entities = estudianteCursoService.getEstudiantes(cursoId);
        return modelMapper.map(entities, new TypeToken<List<EstudianteDTO>>() {
        }.getType());
    }

    @PutMapping(value="/{cursoId}/estudiantes")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EstudianteDTO> addEstudiantes(@PathVariable("cursoId") Long cursoId, @RequestBody List<EstudianteDTO> estudiantes) throws EntityNotFoundException {
        List<EstudianteEntity> entities = modelMapper.map(estudiantes, new TypeToken<List<EstudianteEntity>>() {
        }.getType());
        List<EstudianteEntity> newEntities = estudianteCursoService.addEstudiantes(cursoId, entities);
        return modelMapper.map(newEntities, new TypeToken<List<EstudianteDTO>>() {
        }.getType());
    }

    @DeleteMapping(value="/{cursoId}/estudiantes/{estudianteId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removeEstudiante(@PathVariable("cursoId") Long cursoId,@PathVariable("estudianteId") Long estudianteId) throws EntityNotFoundException {
        estudianteCursoService.removeStudent(cursoId,estudianteId);
    }
}
