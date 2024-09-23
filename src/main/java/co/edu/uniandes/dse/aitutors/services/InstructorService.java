package co.edu.uniandes.dse.aitutors.services;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.repositories.InstructorRepository;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class InstructorService {
    
    @Autowired
    InstructorRepository instructorRepository;

    @Transactional
    public InstructorEntity createInstructor(InstructorEntity instructor) {
        log.info("Creating a new instructor");
        return instructorRepository.save(instructor);
    }

    @Transactional
    public InstructorEntity getInstructor(Long instructorId) throws EntityNotFoundException {
        log.info("Retrieving the instructor with id = {0}", instructorId);
        Optional<InstructorEntity> instructorEntity = instructorRepository.findById(instructorId);
        if (instructorEntity.isEmpty()) {
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        return instructorEntity.get();
    }

    @Transactional
    public List<InstructorEntity> getInstructors() {
        log.info("Retrieving all instructors");
        return instructorRepository.findAll();
    }

    @Transactional
    public InstructorEntity updateInstructor(Long instructorId, InstructorEntity instructor) throws EntityNotFoundException {
        log.info("Updating the instructor with id = {0}", instructorId);
        Optional<InstructorEntity> instructorEntity = instructorRepository.findById(instructorId);
        if (!instructorEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        return instructorRepository.save(instructor);
    }

    @Transactional
    public void deleteInstructor(Long instructorId) throws EntityNotFoundException {
        log.info("Deleting the instructor with id = {0}", instructorId);
        Optional<InstructorEntity> instructorEntity = instructorRepository.findById(instructorId);
        if (!instructorEntity.isPresent()) {
            throw new EntityNotFoundException(ErrorMessage.INSTRUCTOR_NOT_FOUND);
        }
        instructorRepository.deleteById(instructorId);
    }

}
