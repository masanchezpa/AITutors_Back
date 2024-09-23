package co.edu.uniandes.dse.aitutors.services;


import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;


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
    private List<TemaEntity> temaList = new ArrayList<>();
    

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
        });
    }
   

}
