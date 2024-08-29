package co.edu.uniandes.dse.aitutors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;

@Repository
public interface AretefactoRepository extends JpaRepository<ArtefactoEntity, Long> {
        List<ArtefactoEntity> findById(String id);
}