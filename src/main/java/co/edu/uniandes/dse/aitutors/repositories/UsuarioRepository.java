package co.edu.uniandes.dse.aitutors.repositories;



import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    @Query(value = "SELECT id, nombre, email, DTYPE FROM usuario_entity WHERE email = :email", nativeQuery = true)
    Map<String, Object> findUserWithDtypeByEmail(@Param("email") String email);

    List<UsuarioEntity> findByEmail(String email);
        
}