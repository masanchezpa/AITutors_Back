
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
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Prize - Author
 *
 * @author ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(TemaCursoService.class)
class TemaCursoServiceTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Autowired
	private TemaCursoService temaCursoService;

	@Autowired
	private TestEntityManager entityManager;

	private List<TemaEntity> temaList = new ArrayList<>();
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
		entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
    private void insertData() {
        for (int i = 0; i < 2; i++) {
            TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
            entityManager.persist(tema);
            temaList.add(tema);
        }
        for (int i = 0; i < 2; i++) {
            CursoEntity curso = factory.manufacturePojo(CursoEntity.class);
            entityManager.persist(curso);
            cursoList.add(curso);
        }
        temaList.get(0).setCurso(cursoList.get(0));
        entityManager.persist(temaList.get(0));
    }

    @Test
    void testAddCurso() throws EntityNotFoundException, IllegalOperationException {
        TemaEntity tema = temaList.get(1);
        CursoEntity curso = cursoList.get(1);
        CursoEntity response = temaCursoService.addCurso(tema.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }

    @Test
    void testAddInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            TemaEntity tema = temaList.get(1);
            temaCursoService.addCurso(tema.getId(), 0L);
        });
    }

    @Test
    void testAddCursoInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            CursoEntity curso = cursoList.get(0);
            temaCursoService.addCurso(0L, curso.getId());
        });
    }
    
    @Test
    void testGetCurso() throws EntityNotFoundException {
        TemaEntity entity = temaList.get(0);
        CursoEntity response = temaCursoService.getCurso(entity.getId());
        assertNotNull(response);
        assertEquals(entity.getCurso().getId(), response.getId());
    }
	
	@Test
    void testGetCursoInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.getCurso(0L);
        });
    }
	
    @Test
    void testReplaceInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.replaceCurso(0L, cursoList.get(1).getId());
        });
    }

    @Test
    void testReplaceCurso() throws EntityNotFoundException {
        CursoEntity curso = cursoList.get(0);
        TemaEntity tema = temaList.get(1);
        CursoEntity response = temaCursoService.replaceCurso(tema.getId(), curso.getId());
        assertNotNull(response);
        assertEquals(curso.getId(), response.getId());
    }
    
    

    @Test
    void testRemoveInvalidCurso() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.removeCurso(0L);
        });
    }

    @Test

    void testReplaceCursoInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.replaceCurso(0L, cursoList.get(1).getId());
        });
    }

    @Test
    void testRemoveTema() throws EntityNotFoundException {
        temaCursoService.removeCurso(temaList.get(0).getId());
        TemaEntity tema = entityManager.find(TemaEntity.class, temaList.get(0).getId());
        assertNull(tema.getCurso());

    }

	
	@Test
    void testRemoveInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.removeCurso(0L);
        });
    }

    @Test
    void testRemoveCurso() throws EntityNotFoundException {
        // Remover el curso del segundo tema
        temaCursoService.removeCurso(temaList.get(1).getId());
        
        // Verificar que el curso del tema sea nulo
        TemaEntity tema = entityManager.find(TemaEntity.class, temaList.get(1).getId());
        assertNull(tema.getCurso());
        
        // Verificar que el curso no esté asociado a ningún otro tema
        for (TemaEntity t : temaList) {
            if (!t.getId().equals(temaList.get(1).getId())) {
                assertNotEquals(t.getCurso(), tema.getCurso());
            }
        }
    }
   
}





