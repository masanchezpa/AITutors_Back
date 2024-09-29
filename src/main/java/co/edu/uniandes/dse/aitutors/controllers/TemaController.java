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

import co.edu.uniandes.dse.aitutors.dto.TemaDTO;
import co.edu.uniandes.dse.aitutors.dto.TemaDetailDTO;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.TemaService;

@RestController
@RequestMapping("/temas")
public class TemaController {

    @Autowired
    private TemaService temaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TemaDetailDTO> findAll() {
        List<TemaEntity> temas = temaService.getTemas();
        return modelMapper.map(temas, new TypeToken<List<TemaDetailDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TemaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        TemaEntity temaEntity = temaService.getTema(id);
        return modelMapper.map(temaEntity, TemaDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TemaDTO create(@RequestBody TemaDTO temaDTO) throws IllegalOperationException, EntityNotFoundException {
        TemaEntity temaEntity = temaService.createTema(modelMapper.map(temaDTO, TemaEntity.class));
        return modelMapper.map(temaEntity, TemaDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TemaDTO update(@PathVariable("id") Long id, @RequestBody TemaDTO temaDTO)
            throws EntityNotFoundException, IllegalOperationException {
        TemaEntity temaEntity = temaService.updateTema(id, modelMapper.map(temaDTO, TemaEntity.class));
        return modelMapper.map(temaEntity, TemaDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        temaService.deleteTema(id);
    }
}