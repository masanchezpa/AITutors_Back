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
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(CursoInstructorService.class)
class CursoInstructorServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private CursoInstructorService cursoInstructorService;

    @Autowired
    private TestEntityManager entityManager;

    private List<CursoEntity> cursoList = new ArrayList<>();
    private List<InstructorEntity> instructorList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from InstructorEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 2; i++) {
            CursoEntity curso = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(curso);
            cursoList.add(curso);
        }
        for (int i = 0; i < 2; i++) {
            InstructorEntity instructor = factory.manufacturePojo(InstructorEntity.class);
            entityManager.persist(instructor);
            instructorList.add(instructor);
        }
        cursoList.get(0).setInstructor(instructorList.get(0));
        entityManager.persist(cursoList.get(0));
    }

    @Test
    void testAddCurso() throws EntityNotFoundException {
        InstructorEntity instructor = instructorList.get(1);
        CursoEntity curso = cursoList.get(1);
        CursoEntity response = cursoInstructorService.addCurso(instructor.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }

    @Test
    void testAddInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.addCurso(0L, cursoList.get(0).getId());
        });
    }

    @Test
    void testAddInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.addCurso(instructorList.get(0).getId(), 0L);
        });
    }


    @Test
    void testGetCursosInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.getCursos(0L);
        });
    }

    @Test
    void testGetCurso() throws EntityNotFoundException, IllegalOperationException {
        InstructorEntity instructor = instructorList.get(0);
        CursoEntity curso = cursoList.get(0);
        CursoEntity response = cursoInstructorService.getCurso(instructor.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }

    @Test
    void testGetCursoInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.getCurso(0L, cursoList.get(0).getId());
        });
    }

    @Test
    void testGetCursoInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.getCurso(instructorList.get(0).getId(), 0L);
        });
    }



    @Test
    void testAddCursosInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.addCursos(0L, new ArrayList<>());
        });
    }

    @Test
    void testRemoveCurso() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        cursoInstructorService.removeCourse(instructorList.get(0).getId(), curso.getId());
        assertNull(curso.getInstructor());
    }

    @Test
    void testRemoveCursoInvalidInstructor() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.removeCourse(0L, cursoList.get(0).getId());
        });
    }

    @Test
    void testRemoveCursoInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoInstructorService.removeCourse(instructorList.get(0).getId(), 0L);
        });
    }
}
