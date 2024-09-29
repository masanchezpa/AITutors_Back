package co.edu.uniandes.dse.aitutors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TemaService {

    @Autowired
    private TemaRepository temaRepository;

    @Transactional
    public TemaEntity createTema(TemaEntity tema) {
        log.info("Creating a new tema");
        return temaRepository.save(tema);
    }

    @Transactional
    public List<TemaEntity> getTemas() {
        log.info("Retrieving all temas");
        return temaRepository.findAll();
    }

    @Transactional
    public TemaEntity getTema(Long temaId) throws EntityNotFoundException {
        log.info("Retrieving the tema with id = {0}", temaId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (temaEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        }
        return temaEntity.get();
    }

    @Transactional
    public TemaEntity updateTema(Long temaId, TemaEntity tema) throws EntityNotFoundException {
        log.info("Updating the tema with id = {0}", temaId);
        Optional<TemaEntity> temaEntity = temaRepository.findById(temaId);
        if (!temaEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        }
        return temaRepository.save(tema);
    }

    @Transactional
    public void deleteTema(Long temaId) throws EntityNotFoundException {
        log.info("Deleting the tema with id = {0}", temaId);
        Optional<TemaEntity> tema = temaRepository.findById(temaId);
        if (!tema.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.TEMA_NOT_FOUND);
        }
        temaRepository.deleteById(temaId);
    }

    @Transactional
    public List<DocumentoEntity> getDocumentos(Long temaId) throws IllegalOperationException {
        try {
            TemaEntity tema = temaRepository.findById(temaId).get();
            return tema.getDocumentos();
        } catch (Exception e) {
            throw new IllegalOperationException("No se pudo obtener los documentos del tema");
        }
    }
}