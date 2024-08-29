package co.edu.uniandes.dse.aitutors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {
        List<InstructorEntity> findById(String id);
}