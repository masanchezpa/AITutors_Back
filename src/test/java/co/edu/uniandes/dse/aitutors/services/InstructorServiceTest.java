

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


import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(InstructorService.class)
class InstructorServiceTest {

	@Autowired
	private InstructorService instructorService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<InstructorEntity> accionList = new ArrayList<>();

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
            InstructorEntity instructorEntity = factory.manufacturePojo(InstructorEntity.class);
            entityManager.persist(instructorEntity);
            accionList.add(instructorEntity);
        }
    }

    @Test
    void testCreateInstructor() {
        InstructorEntity newEntity = factory.manufacturePojo(InstructorEntity.class);
        InstructorEntity result = instructorService.createInstructor(newEntity);
        assertNotNull(result);
        InstructorEntity entity = entityManager.find(InstructorEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getEmail(), entity.getEmail());
        assertEquals(newEntity.getCursos(), entity.getCursos());
        assertEquals(newEntity.getComentarios(), entity.getComentarios());
        assertEquals(newEntity.getArtefactos(), entity.getArtefactos());
    }


    @Test
    void testGetInstructores() {
        List<InstructorEntity> list = instructorService.getInstructors();
        assertEquals(accionList.size(), list.size());
        for (InstructorEntity entity : list) {
            boolean found = false;
            for (InstructorEntity storedEntity : accionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    
    
    @Test
    void testGetInstructor() throws EntityNotFoundException {
        InstructorEntity entity = accionList.get(0);
        InstructorEntity resultEntity = instructorService.getInstructor(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getEmail(), resultEntity.getEmail());
        assertEquals(entity.getCursos(), resultEntity.getCursos());
        assertEquals(entity.getComentarios(), entity.getComentarios());
        assertEquals(entity.getArtefactos(), entity.getArtefactos());
    }
	
    @Test
    void testGetInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            instructorService.getInstructor(0L);
        });
    }

	@Test
    void testUpdateInstructor() throws EntityNotFoundException{
        InstructorEntity entity = accionList.get(0);
        InstructorEntity pojoEntity = factory.manufacturePojo(InstructorEntity.class);
        pojoEntity.setId(entity.getId());
        instructorService.updateInstructor(entity.getId(), pojoEntity);
        InstructorEntity resp = entityManager.find(InstructorEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getEmail(), resp.getEmail());
        assertEquals(pojoEntity.getCursos(), resp.getCursos());
        assertEquals(pojoEntity.getComentarios(), entity.getComentarios());
        assertEquals(pojoEntity.getArtefactos(), entity.getArtefactos());
    }

    @Test
    void testUpdateInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            InstructorEntity pojoEntity = factory.manufacturePojo(InstructorEntity.class);  
            pojoEntity.setId(0L);
            instructorService.updateInstructor(0L, pojoEntity);
        });
    }
	
   @Test
    void testDeleteInstructor() throws EntityNotFoundException{
        InstructorEntity entity = accionList.get(1);
        instructorService.deleteInstructor(entity.getId());
        InstructorEntity deleted = entityManager.find(InstructorEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            instructorService.deleteInstructor(0L);
        });
    }



}
