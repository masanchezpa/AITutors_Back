package co.edu.uniandes.dse.aitutors.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
      
}