
package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({TemaCursoService.class})
class TemaCursoServiceTest {

    @Autowired
    TemaCursoService temaCursoService;

    @Autowired
    TestEntityManager entityManager;

    PodamFactory factory = new PodamFactoryImpl();

    CursoEntity curso = new CursoEntity();
    List<TemaEntity> temas = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    void clearData() {
        entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from CursoEntity").executeUpdate();
    }

    void insertData() {
        curso = factory.manufacturePojo(CursoEntity.class);
        entityManager.persist(curso);
        for (int i = 0; i < 3; i++) {
            TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
            if (i == 0) {
                tema.setCurso(curso);
            }
            entityManager.persist(tema);
            temas.add(tema);
        }
    }

    @Test
    void testAddCurso() throws EntityNotFoundException, IllegalOperationException {
        TemaEntity tema = temas.get(1);
        CursoEntity result = temaCursoService.addCurso(tema.getId(), curso.getId());
        assertNotNull(result);
        assertEquals(tema.getCurso().getId(), curso.getId());
    }

    @Test
    void testGetCurso() throws EntityNotFoundException {
        TemaEntity tema = temas.get(0);
        CursoEntity result = temaCursoService.getCurso(tema.getId());
        assertNotNull(result);
        assertEquals(result.getId(), curso.getId());
    }

    @Test
    void testReplaceCurso() throws EntityNotFoundException {
        TemaEntity tema = temas.get(0);
        CursoEntity newCurso = factory.manufacturePojo(CursoEntity.class);
        entityManager.persist(newCurso);
        CursoEntity result = temaCursoService.replaceCurso(tema.getId(), newCurso.getId());
        assertNotNull(result);
        assertEquals(result.getId(), newCurso.getId());
    }

    @Test
    void testRemoveCurso() throws EntityNotFoundException {
        TemaEntity tema = temas.get(0);
        CursoEntity result = temaCursoService.getCurso(tema.getId());
        assertNotNull(result);
        temaCursoService.removeCurso(tema.getId());
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.getCurso(tema.getId());
        });
    }

    @Test
    void testAddCursoTemaNotFound() {
        Long invalidTemaId = 999L;
        Long validCursoId = curso.getId();
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.addCurso(invalidTemaId, validCursoId);
        });
    }

    @Test
    void testAddCursoCursoNotFound() {
        Long validTemaId = temas.get(0).getId();
        Long invalidCursoId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.addCurso(validTemaId, invalidCursoId);
        });
    }

    @Test
    void testGetCursoTemaNotFound() {
        Long invalidTemaId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.getCurso(invalidTemaId);
        });
    }

    @Test
    void testReplaceCursoTemaNotFound() {
        Long invalidTemaId = 999L;
        Long validCursoId = curso.getId();
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.replaceCurso(invalidTemaId, validCursoId);
        });
    }

    @Test
    void testReplaceCursoCursoNotFound() {
        Long validTemaId = temas.get(0).getId();
        Long invalidCursoId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.replaceCurso(validTemaId, invalidCursoId);
        });
    }

    @Test
    void testRemoveCursoTemaNotFound() {
        Long invalidTemaId = 999L;
        assertThrows(EntityNotFoundException.class, () -> {
            temaCursoService.removeCurso(invalidTemaId);
        });
    }
}

