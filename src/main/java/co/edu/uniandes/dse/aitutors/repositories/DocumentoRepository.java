package co.edu.uniandes.dse.aitutors.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoEntity, Long> {
     
    @Query(value="SELECT * FROM DOCUMENTO_ENTITY WHERE TEMA_ID = :id",nativeQuery = true)
    List<DocumentoEntity> findTemaByDocumento(@Param("id") Long id);

    @Query(value="SELECT * FROM DOCUMENTO_ENTITY WHERE TIPO = :tipo",nativeQuery = true)
    List<DocumentoEntity> findDocumentoByTipo(@Param("tipo") String tipo);
}