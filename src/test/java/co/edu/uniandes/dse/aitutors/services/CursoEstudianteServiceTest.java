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
import co.edu.uniandes.dse.aitutors.entities.EstudianteEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(CursoEstudianteService.class)
class CursoEstudianteServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private CursoEstudianteService cursoEstudianteService;

    @Autowired
    private TestEntityManager entityManager;

    private List<EstudianteEntity> estudianteList = new ArrayList<>();
    private List<CursoEntity> cursoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 2; i++) {
            CursoEntity curso = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(curso);
            cursoList.add(curso);
        }
        for (int i = 0; i < 2; i++) {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(estudiante);
            estudianteList.add(estudiante);
        }
        
        estudianteList.get(0).getCursos().add(cursoList.get(0));
        cursoList.get(0).getEstudiantes().add(estudianteList.get(0));
        entityManager.persist(estudianteList.get(0));
        entityManager.persist(cursoList.get(0));
    }

    @Test
    void testAddCurso() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(1);
        CursoEntity curso = cursoList.get(1);
        CursoEntity response = cursoEstudianteService.addCurso(estudiante.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }

    @Test
    void testAddInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstudianteEntity estudiante = estudianteList.get(1);
            cursoEstudianteService.addCurso(estudiante.getId(), 0L);
        });
    }

    @Test
    void testAddCursoInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            CursoEntity curso = cursoList.get(0);
            cursoEstudianteService.addCurso(0L, curso.getId());
        });
    }

    @Test
    void testGetCursos() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(0);
        List<CursoEntity> response = cursoEstudianteService.getCursos(estudiante.getId());
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(cursoList.get(0).getId(), response.get(0).getId());
    }

    @Test
    void testGetCursosInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.getCursos(0L);
        });
    }

    @Test
    void testGetCurso() throws EntityNotFoundException, IllegalOperationException {
        EstudianteEntity estudiante = estudianteList.get(0);
        CursoEntity curso = cursoList.get(0);
        CursoEntity response = cursoEstudianteService.getCurso(estudiante.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }

    @Test
    void testGetCursoInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.getCurso(0L, cursoList.get(0).getId());
        });
    }

    @Test
    void testGetCursoInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.getCurso(estudianteList.get(0).getId(), 0L);
        });
    }

    @Test
    void testGetCursoNotAssociated() {
        assertThrows(IllegalOperationException.class, () -> {
            cursoEstudianteService.getCurso(estudianteList.get(1).getId(), cursoList.get(0).getId());
        });
    }

    @Test
    void testAddCursos() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(1);
        List<CursoEntity> cursos = new ArrayList<>();
        cursos.add(cursoList.get(0));
        List<CursoEntity> response = cursoEstudianteService.addCursos(estudiante.getId(), cursos);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(cursoList.get(0).getId(), response.get(0).getId());
    }

    @Test
    void testAddCursosInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.addCursos(0L, cursoList);
        });
    }

    @Test
    void testRemoveCourse() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(0);
        CursoEntity curso = cursoList.get(0);
        cursoEstudianteService.removeCourse(estudiante.getId(), curso.getId());
        EstudianteEntity updatedEstudiante = entityManager.find(EstudianteEntity.class, estudiante.getId());
        assertFalse(updatedEstudiante.getCursos().contains(curso));
        CursoEntity updatedCurso = entityManager.find(CursoEntity.class, curso.getId());
        assertFalse(updatedCurso.getEstudiantes().contains(estudiante));
    }

    @Test
    void testRemoveInvalidCourse() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.removeCourse(0L, cursoList.get(0).getId());
        });
    }

    @Test
    void testRemoveCourseInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            cursoEstudianteService.removeCourse(estudianteList.get(0).getId(), 0L);
        });
    }
}
