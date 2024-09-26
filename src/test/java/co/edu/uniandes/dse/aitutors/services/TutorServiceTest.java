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

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.TutorIAEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(TutorIAService.class)
class TutorServiceTest {

    @Autowired
    private TutorIAService tutorIAService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<TutorIAEntity> tutorList = new ArrayList<>();
    private List<AccionEntity> accionList = new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from TutorIAEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from AccionEntity").executeUpdate();
    }

    private void insertData(){
        for(int i=0;i<3;i++){
            AccionEntity accionEntity = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accionEntity);
            accionList.add(accionEntity);
        }
        for (int i=0;i<3;i++){
            TutorIAEntity tutorIAEntity = factory.manufacturePojo(TutorIAEntity.class);
            entityManager.persist(tutorIAEntity);
            tutorList.add(tutorIAEntity);
        }
    }

    @Test
    void testAgregarAccion(){
        TutorIAEntity tutor = tutorList.get(0);
        AccionEntity accion = accionList.get(0);
        tutorIAService.agregarAccion(accion, tutor);
        TutorIAEntity resultTutor = entityManager.find(TutorIAEntity.class, tutor.getId());
        assertTrue(resultTutor.getAcciones().contains(accion));
    }

    @Test
    void testEliminarAccion(){
        TutorIAEntity tutor = tutorList.get(0);
        AccionEntity accion = accionList.get(0);
        tutorIAService.agregarAccion(accion, tutor);
        tutorIAService.eliminarAccion(accion, tutor);
        TutorIAEntity resultTutor = entityManager.find(TutorIAEntity.class, tutor.getId());
        assertFalse(resultTutor.getAcciones().contains(accion));
    }

    @Test
    void testCreateTutor(){
        TutorIAEntity newTutor = factory.manufacturePojo(TutorIAEntity.class);
        TutorIAEntity result = tutorIAService.createTutor(newTutor);
        assertNotNull(result);
        TutorIAEntity entity = entityManager.find(TutorIAEntity.class,result.getId());
        assertEquals(newTutor.getId(), entity.getId());
    }

    @Test
    void testGetTutores(){
         List<TutorIAEntity> list = tutorIAService.getTutores();
         assertEquals(tutorList.size(), list.size());
         for(TutorIAEntity entity: list){
            boolean found = false;
            for(TutorIAEntity storedEntity:tutorList){
                if(entity.getId().equals(storedEntity.getId())){
                    found = true;
                }
            }
            assertTrue(found);
         }
    }

    @Test
    void testGetTutor() throws EntityNotFoundException{
        TutorIAEntity entity = tutorList.get(0);
        TutorIAEntity resultEntity = tutorIAService.getTutor(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
    }

    @Test
    void testGetInvalidTutor(){
        assertThrows(EntityNotFoundException.class, () -> {
            tutorIAService.getTutor(0L);
        });
    }

    @Test
    void testUpdateTutor() throws EntityNotFoundException{
        TutorIAEntity entity = tutorList.get(0);
        TutorIAEntity updatedEntity = factory.manufacturePojo(TutorIAEntity.class);
        updatedEntity.setId(entity.getId());
        tutorIAService.updateTutor(entity.getId(), updatedEntity);
        TutorIAEntity resp = entityManager.find(TutorIAEntity.class, entity.getId());
        assertEquals(updatedEntity.getId(), resp.getId());
    }

    @Test
    void testUpdateInvalidTutor() {
        assertThrows(EntityNotFoundException.class, () -> {
            TutorIAEntity updatedEntity = factory.manufacturePojo(TutorIAEntity.class);
            updatedEntity.setId(0L);
            tutorIAService.updateTutor(0L, updatedEntity);
        });
    }

    @Test
    void testDeleteTutor() throws EntityNotFoundException {
        TutorIAEntity entity = tutorList.get(1);
        tutorIAService.deleteTutor(entity.getId());
        TutorIAEntity deleted = entityManager.find(TutorIAEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidTutor() {
        assertThrows(EntityNotFoundException.class, () -> {
            tutorIAService.deleteTutor(0L);
        });
    }


}
