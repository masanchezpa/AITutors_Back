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
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Prize - Author
 *
 * @author ISIS2603
 */

@DataJpaTest
@Transactional
@Import(AccionTutorIAService.class)
class AccionTutorIAServiceTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Autowired
	private AccionTutorIAService accionTutorIAService;

	@Autowired
	private TestEntityManager entityManager;

	private List<TutorIAEntity> tutorIAList = new ArrayList<>();
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
		entityManager.getEntityManager().createQuery("delete from AccionEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from TutorIAEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TutorIAEntity tutorIA = factory.manufacturePojo(TutorIAEntity.class);
            entityManager.persist(tutorIA);
            tutorIAList.add(tutorIA);
        }
        for (int i = 0; i < 3; i++) {
            AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accion);
            accionList.add(accion);
            if (i == 0) {
                accion.setTutorIA(tutorIAList.get(0));
            }
        }
    }

    @Test
    void testAddTutorIA() throws EntityNotFoundException, IllegalOperationException  {
        TutorIAEntity tutorIA = tutorIAList.get(0);
        AccionEntity accion = accionList.get(1);
        TutorIAEntity response = accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        assertNotNull(response);
        assertEquals(tutorIA.getId(), response.getId());
    }

    @Test
    void testAddInvalidTutorIA() {
        assertThrows(EntityNotFoundException.class, () -> {
            AccionEntity accion = accionList.get(1);
            accionTutorIAService.addTutorIA(accion.getId(), 0L);
        });
    }

	@Test
    void testAddTutorIAInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            TutorIAEntity tutorIA = tutorIAList.get(0);
            accionTutorIAService.addTutorIA(0L, tutorIA.getId());
        });
    }

    @Test
    void testGetTutorIA() throws EntityNotFoundException {
        AccionEntity entity = accionList.get(0);
        TutorIAEntity response = accionTutorIAService.getTutorIA(entity.getId());
        assertNotNull(response);
        assertEquals(entity.getTutorIA().getId(), response.getId());
    }

	@Test
    void testGetTutorIAInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.getTutorIA(0L);
        });
    }


    @Test
    void testReplaceTutorIA() throws EntityNotFoundException {
        TutorIAEntity tutorIA = tutorIAList.get(0);
        AccionEntity accion = accionList.get(1);
        TutorIAEntity response = accionTutorIAService.replaceTutorIA(accion.getId(), tutorIA.getId());
        assertNotNull(response);
        assertEquals(tutorIA.getId(), response.getId());
    }

	@Test
    void testReplaceInvalidTutorIA() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.replaceTutorIA(0L, accionList.get(1).getId());
        });
    }


	@Test
	void testReplaceTutorIAInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.replaceTutorIA(accionList.get(0).getId(), 0L);
        });
    }

    @Test
    void testRemoveAccion() throws EntityNotFoundException {
        accionTutorIAService.removeTutorIA(accionList.get(0).getId());
        AccionEntity accion = entityManager.find(AccionEntity.class, accionList.get(0).getId());
        assertNull(accion.getTutorIA());
    }

    @Test
    void testRemoveInvalidTutorIA() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.removeTutorIA(0L);
        });
    }

    @Test
    void testRemoveTutorIA () {
        Long nonExistentId = 9999L;
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.removeTutorIA(nonExistentId);
        });
    }

    @Test
    void testCreateAccion() {
        AccionEntity newAccion = factory.manufacturePojo(AccionEntity.class);
        AccionEntity result = accionTutorIAService.createAccion(newAccion);
        assertNotNull(result);
        AccionEntity entity = entityManager.find(AccionEntity.class, result.getId());
        assertEquals(newAccion.getId(), entity.getId());
    }

    @Test
    void testGetAccion() throws EntityNotFoundException {
        AccionEntity entity = accionList.get(0);
        AccionEntity result = accionTutorIAService.getAccion(entity.getId());
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    void testGetInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.getAccion(0L);
        });
    }

    @Test
    void testGetAcciones() {
        List<AccionEntity> result = accionTutorIAService.getAcciones(tutorIAList.get(0).getId());
        assertEquals(1, result.size());
        assertEquals(tutorIAList.get(0).getId(), result.get(0).getTutorIA().getId());
    }

    @Test
    void testUpdateAccion() throws EntityNotFoundException {
        AccionEntity accion = accionList.get(1);
        TutorIAEntity tutorIA = tutorIAList.get(1);
        AccionEntity result = accionTutorIAService.updateAccion(accion.getId(), tutorIA.getId());
        assertNotNull(result);
        assertEquals(tutorIA.getId(), result.getTutorIA().getId());
    }

    @Test
    void testUpdateInvalidAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.updateAccion(0L, tutorIAList.get(0).getId());
        });
    }

    @Test
    void testUpdateAccionInvalidTutorIA() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.updateAccion(accionList.get(0).getId(), 0L);
        });
    }

    @Test
    void testTutorIAExists() {
        boolean exists = accionTutorIAService.tutorIAExists(tutorIAList.get(0).getId());
        assertTrue(exists);
    }

    @Test
    void testTutorIANotExists() {
        boolean exists = accionTutorIAService.tutorIAExists(0L);
        assertFalse(exists);
    }

}
