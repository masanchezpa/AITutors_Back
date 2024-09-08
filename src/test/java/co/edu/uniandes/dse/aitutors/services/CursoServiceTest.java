package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(CursoService.class)
public class CursoServiceTest {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TemaRepository temaRepository;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<CursoEntity> cursoList = new ArrayList<>();
    private List<TemaEntity> temaList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            CursoEntity cursoEntity = factory.manufacturePojo(CursoEntity.class);
            cursoEntity.setId(null); // Asegúrate de que el ID sea null antes de persistir
            entityManager.persist(cursoEntity);
            entityManager.flush(); // Asegúrate de que la entidad esté persistida
            cursoList.add(cursoEntity);
            assertNotNull(cursoEntity.getId(), "El ID del curso debe ser asignado");
    
            TemaEntity temaEntity = factory.manufacturePojo(TemaEntity.class);
            temaEntity.setId(null); // Asegúrate de que el ID sea null antes de persistir
            entityManager.persist(temaEntity);
            entityManager.flush(); // Asegúrate de que la entidad esté persistida
            temaList.add(temaEntity);
            assertNotNull(temaEntity.getId(), "El ID del tema debe ser asignado");
        }
    }
    

    @Test
    void testCrearCurso() throws IllegalOperationException {
        CursoEntity newCurso = factory.manufacturePojo(CursoEntity.class);
        // Asegúrate de que el ID sea null antes de crear el curso
        newCurso.setId(null);
        CursoEntity result = cursoService.creaCurso(newCurso);
        
        // Verifica que el curso no sea null y que tenga un ID asignado
        assertNotNull(result, "El curso creado no debe ser null");
        assertNotNull(result.getId(), "El ID del curso creado no debe ser null");
        
        CursoEntity entity = entityManager.find(CursoEntity.class, result.getId());
        assertNotNull(entity, "El curso debería estar en la base de datos");
        assertEquals(newCurso.getId(), entity.getId(), "El ID del curso creado no coincide");
    }

    @Test
    void testCrearCursoYaExistente() {
        CursoEntity existingCurso = cursoList.get(0);
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.creaCurso(existingCurso);
        });
    }

    @Test
    void testAgregarTema() throws IllegalOperationException {
        CursoEntity curso = cursoList.get(0);
        TemaEntity tema = temaList.get(0);
        
        // Verifica que el ID del curso y tema no sean null
        assertNotNull(curso.getId(), "El ID del curso no debe ser null");
        assertNotNull(tema.getId(), "El ID del tema no debe ser null");
        
        // Asegúrate de que la lista de temas esté inicializada
        if (curso.getTemas() == null) {
            curso.setTemas(new ArrayList<>());
        }
        
        cursoService.agregarTema(curso.getId(), tema.getId());
        CursoEntity resultCurso = entityManager.find(CursoEntity.class, curso.getId());
        
        // Verifica que el curso no sea null y que el tema esté en la lista
        assertNotNull(resultCurso, "El curso resultado no debe ser null");
        assertTrue(resultCurso.getTemas().contains(tema), "El tema debería estar en la lista de temas del curso");
    }

    @Test
    void testAgregarTemaCursoNoExistente() {
        TemaEntity tema = temaList.get(0);
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.agregarTema(Long.MAX_VALUE, tema.getId());
        });
    }

    @Test
    void testAgregarTemaTemaNoExistente() {
        CursoEntity curso = cursoList.get(0);
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.agregarTema(curso.getId(), Long.MAX_VALUE);
        });
    }

    @Test
    void testEliminarTema() throws EntityNotFoundException, IllegalOperationException {
        CursoEntity curso = cursoList.get(0);
        TemaEntity tema = temaList.get(0);
        cursoService.agregarTema(curso.getId(), tema.getId());
        cursoService.eliminarTema(curso.getId(), tema.getId());
        CursoEntity resultCurso = entityManager.find(CursoEntity.class, curso.getId());
        assertFalse(resultCurso.getTemas().contains(tema));
    }

    @Test
    void testEliminarTemaCursoNoExistente() {
        TemaEntity tema = temaList.get(0);
        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.eliminarTema(Long.MAX_VALUE, tema.getId());
        });
    }

    @Test
    void testEliminarTemaTemaNoExistente() {
        CursoEntity curso = cursoList.get(0);
        assertThrows(EntityNotFoundException.class, () -> {
            cursoService.eliminarTema(curso.getId(), Long.MAX_VALUE);
        });
    }

    @Test
    void testEliminarTemaTemaNoAsignado() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        TemaEntity tema = temaList.get(1); // Tema no agregado al curso
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.eliminarTema(curso.getId(), tema.getId());
        });
    }
}
