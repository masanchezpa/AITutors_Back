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

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(CursoService.class)
class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<CursoEntity> cursoList = new ArrayList<>();
    private InstructorEntity instructor;
    private TemaEntity tema;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from InstructorEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
    }

    private void insertData() {
        instructor = factory.manufacturePojo(InstructorEntity.class);
        entityManager.persist(instructor);
        tema = factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);
        for (int i = 0; i < 3; i++) {
            CursoEntity cursoEntity = factory.manufacturePojo(CursoEntity.class);
            cursoEntity.setInstructor(instructor);
            entityManager.persist(cursoEntity);
            cursoList.add(cursoEntity);
        }
    }

    @Test
    void testCreateCurso() throws IllegalOperationException {
        CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
        newEntity.setInstructor(instructor);
        CursoEntity result = cursoService.createCurso(newEntity);
        assertNotNull(result);
        CursoEntity entity = entityManager.find(CursoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    @Test
    void testCrearCursoNoDescripcion() {
        assertThrows(IllegalOperationException.class, () -> {
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            newEntity.setDescripcion("");
            cursoService.createCurso(newEntity);
        });
    }

    @Test
    void testCrearCursoNoNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            newEntity.setNombre("");
            cursoService.createCurso(newEntity);
        });
    }

    @Test
    void testCrearCursoNoInstructor() {
        assertThrows(IllegalOperationException.class, () -> {
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            cursoService.createCurso(newEntity);
        });
    }

    @Test
    void testCrearCursoInstructorNoPersisted() {
        assertThrows(IllegalOperationException.class, () -> {
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            InstructorEntity newInstructor = factory.manufacturePojo(InstructorEntity.class);
            newInstructor.setId(0L);
            newEntity.setInstructor(newInstructor);
            cursoService.createCurso(newEntity);
        });
    }

    @Test
    void testGetCursos() {
        List<CursoEntity> list = cursoService.getCursos();
        assertEquals(cursoList.size(), list.size());
        for (CursoEntity entity : list) {
            boolean found = false;
            for (CursoEntity storedEntity : cursoList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetCurso() throws EntityNotFoundException {
        CursoEntity entity = cursoList.get(0);
        CursoEntity resultEntity = cursoService.getCurso(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
    }

    @Test
    void testGetInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.getCurso(0L);
        });
    }

    @Test
    void testUpdateCurso() throws EntityNotFoundException, IllegalOperationException {
        CursoEntity entity = cursoList.get(0);
        CursoEntity pojoEntity = factory.manufacturePojo(CursoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setInstructor(entity.getInstructor()); // Ensure the instructor is set
        cursoService.updateCurso(entity.getId(), pojoEntity);
        CursoEntity resp = entityManager.find(CursoEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }

    @Test
    void testUpdateInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            CursoEntity pojoEntity = factory.manufacturePojo(CursoEntity.class);
            pojoEntity.setId(0L);
            cursoService.updateCurso(0L, pojoEntity);
        });
    }

    @Test
    void testDeleteCurso() throws EntityNotFoundException {
        CursoEntity entity = cursoList.get(1);
        cursoService.deleteCurso(entity.getId());
        CursoEntity deleted = entityManager.find(CursoEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.deleteCurso(0L);
        });
    }
}