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

import co.edu.uniandes.dse.aitutors.dto.CursoDTO;
import co.edu.uniandes.dse.aitutors.dto.CursoDetailDTO;
import co.edu.uniandes.dse.aitutors.dto.TemaDetailDTO;
import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.CursoService;
import co.edu.uniandes.dse.aitutors.services.TemaCursoService;


@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private TemaCursoService temaCursoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CursoDetailDTO> findAll() {
        List<CursoEntity> cursos = cursoService.getCursos();
        return modelMapper.map(cursos, new TypeToken<List<CursoDetailDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CursoDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        CursoEntity cursoEntity = cursoService.getCurso(id);
        return modelMapper.map(cursoEntity, CursoDetailDTO.class);
    }

    @GetMapping(value = "/{cursoId}/temas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TemaDetailDTO> allCursos(@PathVariable("cursoId") Long cursoId) throws EntityNotFoundException {
        List<TemaEntity> temas = temaCursoService.getTemas(cursoId);
        return modelMapper.map(temas, new TypeToken<List<TemaDetailDTO>>() {
        }.getType());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CursoDTO create(@RequestBody CursoDTO cursoDTO) throws IllegalOperationException, EntityNotFoundException {
        CursoEntity cursoEntity = cursoService.createCurso(modelMapper.map(cursoDTO, CursoEntity.class));
        return modelMapper.map(cursoEntity, CursoDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public CursoDTO update(@PathVariable("id") Long id, @RequestBody CursoDTO cursoDTO)
            throws EntityNotFoundException, IllegalOperationException {
        CursoEntity cursoEntity = cursoService.updateCurso(id, modelMapper.map(cursoDTO, CursoEntity.class));
        return modelMapper.map(cursoEntity, CursoDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        cursoService.deleteCurso(id);
    }
}