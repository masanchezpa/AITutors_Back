

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

import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(EstudianteService.class)
class EstudianteServiceTest {

	@Autowired
	private EstudianteService accionService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EstudianteEntity> accionList = new ArrayList<>();

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
            EstudianteEntity estudianteEntity = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(estudianteEntity);
            accionList.add(estudianteEntity);
        }
    }


    @Test
    void testCreateEstudiante() {
        EstudianteEntity newEntity = factory.manufacturePojo(EstudianteEntity.class);
        EstudianteEntity result = accionService.createEstudiante(newEntity);
        assertNotNull(result);
        EstudianteEntity entity = entityManager.find(EstudianteEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getEmail(), entity.getEmail());
        assertEquals(newEntity.getCursos(), entity.getCursos());
        assertEquals(newEntity.getComentarios(), entity.getComentarios());
        assertEquals(newEntity.getArtefactos(), entity.getArtefactos());
    }
    
    @Test
    void testGetEstudiantes() {
        List<EstudianteEntity> list = accionService.getEstudiantes();
        assertEquals(accionList.size(), list.size());
        for (EstudianteEntity entity : list) {
            boolean found = false;
            for (EstudianteEntity storedEntity : accionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
	
    @Test
    void testGetEstudiante() throws EntityNotFoundException {
        EstudianteEntity entity = accionList.get(0);
        EstudianteEntity resultEntity = accionService.getEstudiante(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getEmail(), resultEntity.getEmail());
        assertEquals(entity.getCursos(), resultEntity.getCursos());
        assertEquals(entity.getComentarios(), entity.getComentarios());
        assertEquals(entity.getArtefactos(), entity.getArtefactos());
    }

	@Test
    void testGetInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionService.getEstudiante(0L);
        });
    }

    @Test
    void testUpdateEstudiante() throws EntityNotFoundException{
        EstudianteEntity entity = accionList.get(0);
        EstudianteEntity pojoEntity = factory.manufacturePojo(EstudianteEntity.class);
        pojoEntity.setId(entity.getId());
        accionService.updateEstudiante(entity.getId(), pojoEntity);
        EstudianteEntity resp = entityManager.find(EstudianteEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getEmail(), resp.getEmail());
        assertEquals(pojoEntity.getCursos(), resp.getCursos());
        assertEquals(pojoEntity.getComentarios(), entity.getComentarios());
        assertEquals(pojoEntity.getArtefactos(), entity.getArtefactos());
    }
	
   @Test
    void testUpdateInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity pojoEntity = factory.manufacturePojo(EstudianteEntity.class);
            pojoEntity.setId(0L);
            accionService.updateEstudiante(0L, pojoEntity);
        });
    }


	@Test
    void testDeleteEstudiante() throws EntityNotFoundException{
        EstudianteEntity entity = accionList.get(1);
        accionService.deleteEstudiante(entity.getId());
        EstudianteEntity deleted = entityManager.find(EstudianteEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionService.deleteEstudiante(0L);
        });
    }
	

}
