package co.edu.uniandes.dse.aitutors.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Long> {
    public List<ComentarioEntity> findAllByOrderByFechaAsc();

    public List<ComentarioEntity> findAllByOrderByFechaDesc();
        
}