package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import({ArtefactoAccionService.class, AccionService.class})
class ArtefactoAccionServiceTest {

    @Autowired
    private ArtefactoAccionService artefactoAccionService;

    @Autowired
    private AccionService accionService;

    @Autowired

    private TestEntityManager entityManager;

    private PodamFactory factory=new PodamFactoryImpl();

    private ArtefactoEntity artefacto;

    private List<AccionEntity> accionList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from ArtefactoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from AccionEntity").executeUpdate();
    }

    private void insertData(){
        artefacto=factory.manufacturePojo(ArtefactoEntity.class);
        entityManager.persist(artefacto);

        for (int i=0;i<3;i++){
            AccionEntity entity=factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(entity);
            accionList.add(entity);
        }

    }


    @Test
    void testAddAccion() throws EntityNotFoundException, IllegalOperationException{
        AccionEntity newAccion=factory.manufacturePojo(AccionEntity.class);
        accionService.createAccion(newAccion);

        AccionEntity accionEntity=artefactoAccionService.addAccion(artefacto.getId(), newAccion.getId());

        assertNotNull(accionEntity);

        assertEquals(newAccion.getDescripcion(), accionEntity.getDescripcion());
        assertEquals(newAccion.getHabilitacion(), accionEntity.getHabilitacion());
        assertEquals(newAccion.getId(), accionEntity.getId());
        assertEquals(newAccion.getNombre(), accionEntity.getNombre());
        assertEquals(newAccion.getObjetivo(), accionEntity.getObjetivo());

    }

    @Test
    void testAddAccionInvalidArtefacto(){
        assertThrows(EntityNotFoundException.class, ()->{

            AccionEntity newAccion=factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(newAccion);
            artefactoAccionService.addAccion(0l, newAccion.getId());
        });
    }

    @Test
    void testAddAccionInvalid(){
        assertThrows(EntityNotFoundException.class, ()->{

            artefactoAccionService.addAccion(artefacto.getId(),0L);
        });
    }

    @Test
    void testAddAccionCreated(){
        assertThrows(IllegalOperationException.class, ()->{
            AccionEntity newAccion=factory.manufacturePojo( AccionEntity.class);
            ArtefactoEntity newArtefacto=factory.manufacturePojo(ArtefactoEntity.class);

            entityManager.persist(newAccion);

            newArtefacto.setAccion(newAccion);

            entityManager.persist(newArtefacto);

            artefactoAccionService.addAccion(newArtefacto.getId(), newAccion.getId());
        });
    }

    @Test
    void testGetAccion() throws EntityNotFoundException{
        ArtefactoEntity artefactoEntity=factory.manufacturePojo( ArtefactoEntity.class);
        artefactoEntity.setAccion(accionList.getFirst());
        entityManager.persist(artefactoEntity);

        AccionEntity answer=artefactoAccionService.getAccion(artefactoEntity.getId());

        assertNotNull(answer);
        assertEquals(accionList.getFirst().getDescripcion(), answer.getDescripcion());
        assertEquals(accionList.getFirst().getHabilitacion(), answer.getHabilitacion());
        assertEquals(accionList.getFirst().getId(), answer.getId());
        assertEquals(accionList.getFirst().getNombre(), answer.getNombre());
        assertEquals(accionList.getFirst().getObjetivo(), answer.getObjetivo());

    }

    @Test
    void testGetAccionInvalidArtefacto(){
        assertThrows(EntityNotFoundException.class, () -> {
			artefactoAccionService.getAccion(0L);
		});
    }

    @Test
    void testGetAccionNull(){
        assertThrows(EntityNotFoundException.class, () -> {
			artefactoAccionService.getAccion(artefacto.getId());
		});
    }

    @Test
    void testReplaceAccion() throws EntityNotFoundException{
        AccionEntity accionEntity=accionList.getFirst();
        AccionEntity newEntity=artefactoAccionService.replaceAccion(artefacto.getId(),accionEntity.getId());

        assertNotNull(newEntity);

        assertEquals(accionEntity.getDescripcion(), newEntity.getDescripcion());
        assertEquals(accionEntity.getHabilitacion(), newEntity.getHabilitacion());
        assertEquals(accionEntity.getId(), newEntity.getId());
        assertEquals(accionEntity.getNombre(), newEntity.getNombre());
        assertEquals(accionEntity.getObjetivo(), newEntity.getObjetivo());
    }

    @Test
    void testReplaceAccionInvalidArtefacto(){
        assertThrows(EntityNotFoundException.class, () -> {
            AccionEntity accionEntity=accionList.getFirst();
            artefactoAccionService.replaceAccion(0L,accionEntity.getId());
        });
    }

    @Test
    void testReplaceAccionInvalid(){
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoAccionService.replaceAccion(artefacto.getId(),0L);
        });
    }

    @Test
    void testRemoveAccion() throws EntityNotFoundException{
        artefactoAccionService.removeAccion(artefacto.getId());
        assertNull(artefacto.getAccion());
    }

    @Test
    void testRemoveAccionInvalidArtefacto(){
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoAccionService.removeAccion(0L);
        });
    }



}
