package co.edu.uniandes.dse.aitutors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;

@Repository
public interface TutorIARepository extends JpaRepository<TutorIAEntity, Long> {
        List<TutorIARepository> findById(String id);
}