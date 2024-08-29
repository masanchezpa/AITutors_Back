package co.edu.uniandes.dse.aitutors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoEntity, Long> {
        List<DocumentoEntity> findById(String id);
}