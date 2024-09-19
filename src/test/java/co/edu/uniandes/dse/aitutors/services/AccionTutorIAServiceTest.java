package co.edu.uniandes.dse.aitutors.services;

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

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({AccionTutorIAService.class})
public class AccionTutorIAServiceTest {

    @Autowired
    private AccionTutorIAService accionTutorIAService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private TutorIAEntity tutorIA = new TutorIAEntity();
    private List<AccionEntity> acciones = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    void clearData() {
        entityManager.getEntityManager().createQuery("delete from AccionEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TutorIAEntity").executeUpdate();
    }

    void insertData() {
        tutorIA = factory.manufacturePojo(TutorIAEntity.class);
        TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);
        tutorIA.setTema(tema);

        entityManager.persist(tutorIA);

        for (int i = 0; i < 3; i++) {
            AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accion);
            acciones.add(accion);
        }
    }

    @Test
    void testAddTutorIA() throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accion = acciones.get(0);
        TutorIAEntity result = accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        assertNotNull(result);
        assertEquals(tutorIA.getId(), result.getId());
    }

    @Test
    void testAddTutorIAWithNonExistentAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.addTutorIA(999L, tutorIA.getId());
        });
    }

    @Test
    void testAddTutorIAWithNonExistentTutorIA() {
        AccionEntity accion = acciones.get(0);
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.addTutorIA(accion.getId(), 999L);
        });
    }

    @Test
    void testAddTutorIAWithExistingTutorIA() throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accion = acciones.get(0);
        accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        assertThrows(IllegalOperationException.class, () -> {
            accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        });
    }

    @Test
    void testGetTutorIA() throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accion = acciones.get(0);
        accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        TutorIAEntity result = accionTutorIAService.getTutorIA(accion.getId());
        assertNotNull(result);
        assertEquals(tutorIA.getId(), result.getId());
    }

    @Test
    void testGetTutorIAWithNonExistentAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.getTutorIA(999L);
        });
    }

    @Test
    void testGetTutorIAWithNoTutorIA() {
        AccionEntity accion = acciones.get(0);
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.getTutorIA(accion.getId());
        });
    }

    @Test
    void testRemoveTutorIA() throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accion = acciones.get(0);
        accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        accionTutorIAService.removeTutorIA(accion.getId());
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.getTutorIA(accion.getId());
        });
    }

    @Test
    void testRemoveTutorIAWithNonExistentAccion() {
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.removeTutorIA(999L);
        });
    }

    @Test
    void testReplaceTutorIA() throws EntityNotFoundException, IllegalOperationException {
        AccionEntity accion = acciones.get(0);
        TutorIAEntity newTutorIA = factory.manufacturePojo(TutorIAEntity.class);
        TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);
        newTutorIA.setTema(tema);
        entityManager.persist(newTutorIA);

        accionTutorIAService.addTutorIA(accion.getId(), tutorIA.getId());
        TutorIAEntity result = accionTutorIAService.replaceTutorIA(accion.getId(), newTutorIA.getId());
        assertNotNull(result);
        assertEquals(newTutorIA.getId(), result.getId());
    }

    @Test
    void testReplaceTutorIAWithNonExistentAccion() {
        TutorIAEntity newTutorIA = factory.manufacturePojo(TutorIAEntity.class);
        TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);
        newTutorIA.setTema(tema);
        entityManager.persist(newTutorIA);

        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.replaceTutorIA(999L, newTutorIA.getId());
        });
    }

    @Test
    void testReplaceTutorIAWithNonExistentTutorIA() {
        AccionEntity accion = acciones.get(0);
        assertThrows(EntityNotFoundException.class, () -> {
            accionTutorIAService.replaceTutorIA(accion.getId(), 999L);
        });
    }
}
