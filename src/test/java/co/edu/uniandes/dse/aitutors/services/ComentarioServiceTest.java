

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

import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.services.ComentarioService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(ComentarioService.class)
class ComentarioServiceTest {

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<ComentarioEntity> accionList = new ArrayList<>();

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
            ComentarioEntity comentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
            entityManager.persist(comentarioEntity);
            accionList.add(comentarioEntity);
        }
    }

    @Test
    void testCreateComentario() {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity result = comentarioService.createComentario(newEntity);
        assertNotNull(result);
        ComentarioEntity entity = entityManager.find(ComentarioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getContenido(), entity.getContenido());
        assertEquals(newEntity.getFecha(), entity.getFecha());
        assertEquals(newEntity.getAutor(), entity.getAutor());
    }

	
    @Test
    void testGetComentarios() {
        List<ComentarioEntity> list = comentarioService.getComentarios();
        assertEquals(accionList.size(), list.size());
        for (ComentarioEntity entity : list) {
            boolean found = false;
            for (ComentarioEntity storedEntity : accionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    
    @Test
    void testGetComentario() throws EntityNotFoundException {
        ComentarioEntity entity = accionList.get(0);
        ComentarioEntity resultEntity = comentarioService.getComentario(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getContenido(), resultEntity.getContenido());
        assertEquals(entity.getFecha(), resultEntity.getFecha());
        assertEquals(entity.getAutor(), resultEntity.getAutor());
    }


	@Test
    void testGetInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioService.getComentario(0L);
        });
    }

    @Test
    void testUpdateComentario() throws EntityNotFoundException{
        ComentarioEntity entity = accionList.get(0);
        ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);
        pojoEntity.setId(entity.getId());
        comentarioService.updateComentario(entity.getId(), pojoEntity);
        ComentarioEntity resp = entityManager.find(ComentarioEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getContenido(), resp.getContenido());
        assertEquals(pojoEntity.getFecha(), resp.getFecha());
        assertEquals(pojoEntity.getAutor(), resp.getAutor());
    }



	@Test
    void testUpdateInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity pojoEntity = factory.manufacturePojo(ComentarioEntity.class);
            pojoEntity.setId(0L);
            comentarioService.updateComentario(0L, pojoEntity);
        });
    }


	@Test
    void testDeleteComentario() throws EntityNotFoundException{
        ComentarioEntity entity = accionList.get(1);
        comentarioService.deleteComentario(entity.getId());
        ComentarioEntity deleted = entityManager.find(ComentarioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioService.deleteComentario(0L);
        });
    }

}

