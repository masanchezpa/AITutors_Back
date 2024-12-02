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


import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de la relación Instructor - Curso
 *
 */

@DataJpaTest
@Transactional
@Import(InstructorCursoService.class)
class InstructorCursoServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private InstructorCursoService instructorCursoService;

    @Autowired
    private TestEntityManager entityManager;

    private List<InstructorEntity> instructorList = new ArrayList<>();
    private List<CursoEntity> cursoList = new ArrayList<>();

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
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from InstructorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 2; i++) {
            InstructorEntity instructor = factory.manufacturePojo(InstructorEntity.class);
            entityManager.persist(instructor);
            instructorList.add(instructor);
        }
        for (int i = 0; i < 2; i++) {
            CursoEntity curso = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(curso);
            cursoList.add(curso);
        }
        cursoList.get(0).setInstructor(instructorList.get(0));
        entityManager.persist(cursoList.get(0));
    }

    @Test
    void testAddInstructor() throws EntityNotFoundException {
        InstructorEntity instructor = instructorList.get(1);
        CursoEntity curso = cursoList.get(1);
        InstructorEntity response = instructorCursoService.addInstructor(instructor.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(instructor.getId(), response.getId());
    }

    @Test
    void testAddInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            InstructorEntity instructor = instructorList.get(1);
            instructorCursoService.addInstructor(instructor.getId(), 0L);
        });
    }

    @Test
    void testAddInstructorInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            CursoEntity curso = cursoList.get(0);
            instructorCursoService.addInstructor(0L, curso.getId());
        });
    }
    
    @Test
    void testGetInstructor() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        InstructorEntity response = instructorCursoService.getInstructor(curso.getId());
        assertNotNull(response);
        assertEquals(curso.getInstructor().getId(), response.getId());
    }
    
    @Test
    void testGetInstructorInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            instructorCursoService.getInstructor(0L);
        });
    }
    
    @Test
    void testReplaceInstructor() throws EntityNotFoundException {
        InstructorEntity instructor = instructorList.get(1);
        CursoEntity curso = cursoList.get(0);
        InstructorEntity response = instructorCursoService.replaceInstructor(instructor.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(instructor.getId(), response.getId());
    }

    @Test
    void testReplaceInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            instructorCursoService.replaceInstructor(0L, cursoList.get(1).getId());
        });
    }

    @Test
    void testRemoveInstructor() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        instructorCursoService.removeInstructor(instructorList.get(0).getId(), curso.getId());
        CursoEntity updatedCurso = entityManager.find(CursoEntity.class, curso.getId());
        assertNull(updatedCurso.getInstructor());
    }

    @Test
    void testRemoveInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            instructorCursoService.removeInstructor(0L, cursoList.get(1).getId());
        });
    }

    @Test
    void testRemoveCurso() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(1);
        instructorCursoService.removeInstructor(instructorList.get(1).getId(), curso.getId());
        CursoEntity updatedCurso = entityManager.find(CursoEntity.class, curso.getId());
        assertNull(updatedCurso.getInstructor());
    }
}
