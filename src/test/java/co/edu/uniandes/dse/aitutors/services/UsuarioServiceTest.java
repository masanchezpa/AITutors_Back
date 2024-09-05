

package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.UsuarioService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(UsuarioService.class)
class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<UsuarioEntity> accionList = new ArrayList<>();

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from AccionEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            UsuarioEntity accionEntity = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(accionEntity);
            accionList.add(accionEntity);
        }
    }


    @Test
    void testCreateUsuario() {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioService.createUsuario(newEntity);
        assertNotNull(result);
        UsuarioEntity entity = entityManager.find(UsuarioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getEmail(), entity.getEmail());
        assertEquals(newEntity.getComentarios(), entity.getComentarios());
        assertEquals(newEntity.getArtefactos(), entity.getArtefactos());
    
        
    }


    @Test
    void testGetUsuarios() {
        List<UsuarioEntity> list = usuarioService.getUsuarios();
        assertEquals(accionList.size(), list.size());
        for (UsuarioEntity entity : list) {
            boolean found = false;
            for (UsuarioEntity storedEntity : accionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    
    
    @Test
    void testGetUsuario() throws EntityNotFoundException {
        UsuarioEntity entity = accionList.get(0);
        UsuarioEntity resultEntity = usuarioService.getUsuario(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getEmail(), resultEntity.getEmail());
        assertEquals(entity.getComentarios(), entity.getComentarios());
        assertEquals(entity.getArtefactos(), entity.getArtefactos());
    }
	
    @Test
    void testGetInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.getUsuario(0L);
        });
    }

	@Test
    void testUpdateUsuario() throws EntityNotFoundException{
        UsuarioEntity entity = accionList.get(0);
        UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);
        pojoEntity.setId(entity.getId());
        usuarioService.updateUsuario(entity.getId(), pojoEntity);
        UsuarioEntity resp = entityManager.find(UsuarioEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getEmail(), resp.getEmail());
        assertEquals(pojoEntity.getComentarios(), entity.getComentarios());
        assertEquals(pojoEntity.getArtefactos(), entity.getArtefactos());
    }

    @Test
    void testUpdateInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            UsuarioEntity pojoEntity = factory.manufacturePojo(UsuarioEntity.class);  
            pojoEntity.setId(0L);
            usuarioService.updateUsuario(0L, pojoEntity);
        });
    }
	
   @Test
    void testDeleteUsuario() throws EntityNotFoundException{
        UsuarioEntity entity = accionList.get(1);
        usuarioService.deleteUsuario(entity.getId());
        UsuarioEntity deleted = entityManager.find(UsuarioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.deleteUsuario(0L);
        });
    }



}
