package co.edu.uniandes.dse.aitutors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;

@Repository
public interface AccionRepository extends JpaRepository<AccionEntity, Long> {
        List<AccionEntity> findByName(String nombre);
}