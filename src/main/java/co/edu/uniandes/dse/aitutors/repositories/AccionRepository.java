package co.edu.uniandes.dse.aitutors.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;

@Repository
public interface AccionRepository extends JpaRepository<AccionEntity, Long> {
        List<AccionEntity> findByTutorIAId(Long tutorIAId);
}


