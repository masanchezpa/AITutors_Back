package co.edu.uniandes.dse.aitutors.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import co.edu.uniandes.dse.aitutors.entities.InstructorEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.List;
import java.util.ArrayList;

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
		entityManager.getEntityManager().createQuery("delete from CursoEntity");
        entityManager.getEntityManager().createQuery("delete from InstructorEntity");
        entityManager.getEntityManager().createQuery("delete from TemaEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
    private void insertData() {
        instructor=factory.manufacturePojo(InstructorEntity.class);
        entityManager.persist(instructor);
        tema=factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);
        for (int i = 0; i < 3; i++) {
            CursoEntity cursoEntity = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(cursoEntity);
            cursoList.add(cursoEntity);
        }
    }

    @Test
    void testCreaCurso() throws IllegalOperationException{
        CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
        newEntity.setInstructor(instructor);
        CursoEntity result = cursoService.creaCurso(newEntity);
        assertNotNull(result);
        CursoEntity entity = entityManager.find(CursoEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    @Test
    void testCrearCursoNoDescripcion(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            newEntity.setDescripcion("");
            cursoService.creaCurso(newEntity);

        });
    }

    @Test
    void testCrearCursoNoNombre(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            newEntity.setNombre("");
            cursoService.creaCurso(newEntity);

        });
    }

    @Test
    void testCrearCursoNoInstructor(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            cursoService.creaCurso(newEntity);

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
    void testCrearCursoInstructorNoPersisted(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity newEntity = factory.manufacturePojo(CursoEntity.class);
            InstructorEntity newInstructor=factory.manufacturePojo(InstructorEntity.class);
            newInstructor.setId(0L);
            newEntity.setInstructor(newInstructor);
            cursoService.creaCurso(newEntity);
          
    void testAgregarTemaTemaNoExistente() {
        CursoEntity curso = cursoList.get(0);
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.agregarTema(curso.getId(), Long.MAX_VALUE);

        });
    }

    @Test
    void testAgregarTema()throws IllegalOperationException{
        CursoEntity curso=cursoList.get(0);

        CursoEntity answer=cursoService.agregarTema(curso.getId(), tema.getId());

        assertEquals(curso.getId(), answer.getId());
        assertEquals(curso.getNombre(), answer.getNombre());
        assertEquals(curso.getDescripcion(), answer.getDescripcion());
        assertEquals(curso.getTemas().getLast().getTitulo(),tema.getTitulo());
        assertEquals(curso.getTemas().getLast().getDescripcion(),tema.getDescripcion());
    }

    @Test
    void testAgregarTemaNoPersistido(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity curso=cursoList.get(0);
            TemaEntity newTema=factory.manufacturePojo(TemaEntity.class);
            newTema.setId(0L);
            cursoService.agregarTema(newTema.getId(),curso.getId());
        });
    }

    @Test
    void testAgregarTemaCursoInexistente(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity curso=factory.manufacturePojo(CursoEntity.class);
            curso.setId(0l);
            cursoService.agregarTema(tema.getId(),curso.getId());

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

    void testEliminarTema()throws IllegalOperationException{
        CursoEntity curso=cursoList.get(0);

        CursoEntity answer=cursoService.eliminarTema(curso.getId(), tema.getId());
        assertEquals(curso.getId(), answer.getId());
        assertEquals(curso.getNombre(), answer.getNombre());
        assertEquals(curso.getDescripcion(), answer.getDescripcion());
    }

    @Test
    void testEliminarTemaCursoInexistente(){
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity curso=factory.manufacturePojo(CursoEntity.class);
            curso.setId(0l);
            cursoService.eliminarTema(tema.getId(),curso.getId());

        });
    }

    @Test
    void testEliminarTemaNoPersistido() {
        assertThrows(IllegalOperationException.class, ()->{
            CursoEntity curso=cursoList.get(0);
            TemaEntity newTema=factory.manufacturePojo(TemaEntity.class);
            newTema.setId(0L);
            cursoService.eliminarTema(newTema.getId(),curso.getId());
      
    @Test
    void testEliminarTemaTemaNoAsignado() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        TemaEntity tema = temaList.get(1); // Tema no agregado al curso
        assertThrows(IllegalOperationException.class, () -> {
            cursoService.eliminarTema(curso.getId(), tema.getId());

        });
    }
}
