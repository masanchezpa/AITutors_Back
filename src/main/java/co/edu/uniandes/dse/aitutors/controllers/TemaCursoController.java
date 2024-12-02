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

import co.edu.uniandes.dse.aitutors.dto.CursoDTO;
import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.TemaCursoService;

@RestController
@RequestMapping("/temas")
public class TemaCursoController {

    @Autowired
    private TemaCursoService temaCursoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value="/{temaId}/curso/{cursoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CursoDTO addCurso(@PathVariable("temaId") Long temaId, @PathVariable("cursoId") Long cursoId) throws EntityNotFoundException, IllegalOperationException {
        CursoEntity entity = temaCursoService.addCurso(temaId, cursoId);
        return modelMapper.map(entity, CursoDTO.class);
    }

    @GetMapping(value="/{temaId}/curso")
    @ResponseStatus(code = HttpStatus.OK)
    public CursoDTO getCurso(@PathVariable("temaId") Long temaId) throws EntityNotFoundException {
        CursoEntity entity = temaCursoService.getCurso(temaId);
        return modelMapper.map(entity, CursoDTO.class);
    }

    @PutMapping(value="/{temaId}/curso/{cursoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public CursoDTO replaceCurso(@PathVariable("temaId") Long temaId, @PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        CursoEntity entity = temaCursoService.replaceCurso(temaId, cursoId);
        return modelMapper.map(entity, CursoDTO.class);
    }

    @DeleteMapping(value="/{temaId}/curso")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCurso(@PathVariable("temaId") Long temaId) throws EntityNotFoundException {
        temaCursoService.removeCurso(temaId);
    }
}
