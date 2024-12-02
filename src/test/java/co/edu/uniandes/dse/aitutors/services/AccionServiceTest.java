

package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(AccionService.class)
class AccionServiceTest {

	@Autowired
	private AccionService accionService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<AccionEntity> accionList = new ArrayList<>();

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
            AccionEntity accionEntity = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accionEntity);
            accionList.add(accionEntity);
        }
    }

    @Test
    void testCreateAccion() {
        AccionEntity newEntity = factory.manufacturePojo(AccionEntity.class);
        AccionEntity result = accionService.createAccion(newEntity);
        assertNotNull(result);
        AccionEntity entity = entityManager.find(AccionEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getObjetivo(), entity.getObjetivo());
    }

	

    @Test
    void testGetAccions() {
        List<AccionEntity> list = accionService.getAcciones();
        assertEquals(accionList.size(), list.size());
        for (AccionEntity entity : list) {
            boolean found = false;
            for (AccionEntity storedEntity : accionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

	@Test
    void testGetAccion() throws EntityNotFoundException {
        AccionEntity entity = accionList.get(0);
        AccionEntity resultEntity = accionService.getAccion(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getObjetivo(), resultEntity.getObjetivo());
    }

    @Test
    void testGetInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionService.getAccion(0L);
        });
    }
	
    @Test
    void testUpdateAccion() throws EntityNotFoundException{
        AccionEntity entity = accionList.get(0);
        AccionEntity pojoEntity = factory.manufacturePojo(AccionEntity.class);
        pojoEntity.setId(entity.getId());
        accionService.updateAccion(entity.getId(), pojoEntity);
        AccionEntity resp = entityManager.find(AccionEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getObjetivo(), resp.getObjetivo());
    }


	@Test
    void testUpdateInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            AccionEntity pojoEntity = factory.manufacturePojo(AccionEntity.class);
            pojoEntity.setId(0L);
            accionService.updateAccion(0L, pojoEntity);
        });
    }

    @Test
    void testDeleteAccion() throws EntityNotFoundException{
        AccionEntity entity = accionList.get(1);
        accionService.deleteAccion(entity.getId());
        AccionEntity deleted = entityManager.find(AccionEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionService.deleteAccion(0L);
        });
    }
	

}
