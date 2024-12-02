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


import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relación Estudiante-Curso
 *
 * @autor ISIS2603
 */

@DataJpaTest
@Transactional
@Import(EstudianteCursoService.class)
class EstudianteCursoServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private EstudianteCursoService estudianteCursoService;

    @Autowired
    private TestEntityManager entityManager;

    private List<EstudianteEntity> estudianteList = new ArrayList<>();
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
        entityManager.getEntityManager().createQuery("delete from EstudianteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 2; i++) {
            EstudianteEntity estudiante = factory.manufacturePojo(EstudianteEntity.class);
            entityManager.persist(estudiante);
            estudianteList.add(estudiante);
        }
        for (int i = 0; i < 2; i++) {
            CursoEntity curso = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(curso);
            cursoList.add(curso);
        }
        estudianteList.get(0).getCursos().add(cursoList.get(0));
        entityManager.persist(estudianteList.get(0));
    }



    @Test
    void testAddInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.addEstudiante(0L, cursoList.get(1).getId());
        });
    }

    @Test
    void testAddEstudianteInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.addEstudiante(estudianteList.get(0).getId(), 0L);
        });
    }

    @Test
    void testAddEstudiantes() throws EntityNotFoundException {
        
        List<EstudianteEntity> estudiantes = new ArrayList<>();
        estudiantes.add(estudianteList.get(1));  
        CursoEntity curso = cursoList.get(1);    
    
        List<EstudianteEntity> response = estudianteCursoService.addEstudiantes(curso.getId(), estudiantes);
        
        assertNotNull(response);
        assertEquals(1, response.size(), "El número de estudiantes no es el esperado.");
    }
    
    @Test
    void testGetEstudiantes() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        List<EstudianteEntity> response = estudianteCursoService.getEstudiantes(curso.getId());
    
        assertNotNull(response);
    
        assertEquals(0, response.size(), "El número de estudiantes no es el esperado.");
    }

    @Test
    void testGetEstudiantesInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.getEstudiantes(0L);
        });
    }

    @Test
    void testGetEstudiante() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(0);
        CursoEntity curso = cursoList.get(0);
        EstudianteEntity response = estudianteCursoService.getEstudiante(curso.getId(), estudiante.getId());
        assertNotNull(response);
        assertEquals(estudiante.getId(), response.getId());
    }

    @Test
    void testGetEstudianteInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.getEstudiante(0L, estudianteList.get(0).getId());
        });
    }

    @Test
    void testGetEstudianteInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.getEstudiante(cursoList.get(0).getId(), 0L);
        });
    }


    @Test
    void testAddEstudiantesInvalidCurso() {
        List<EstudianteEntity> estudiantes = new ArrayList<>();
        estudiantes.add(estudianteList.get(0));
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.addEstudiantes(0L, estudiantes);
        });
    }

    @Test
    void testRemoveStudent() throws EntityNotFoundException {
        EstudianteEntity estudiante = estudianteList.get(1);
        CursoEntity curso = cursoList.get(1);
        estudianteCursoService.removeStudent(estudiante.getId(), curso.getId());
        CursoEntity cursoEntity = entityManager.find(CursoEntity.class, curso.getId());
        EstudianteEntity estudianteEntity = entityManager.find(EstudianteEntity.class, estudiante.getId());
        assertFalse(cursoEntity.getEstudiantes().contains(estudianteEntity));
        assertFalse(estudianteEntity.getCursos().contains(cursoEntity));
    }

    @Test
    void testRemoveStudentInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.removeStudent(estudianteList.get(0).getId(), 0L);
        });
    }

    @Test
    void testRemoveStudentInvalidEstudiante() {
        assertThrows(EntityNotFoundException.class, () -> {
            estudianteCursoService.removeStudent(0L, cursoList.get(0).getId());
        });
    }
}
