package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ComentarioArtefactoService.class,ArtefactoService.class})
public class ComentarioArtefactoServiceTest {

    @Autowired
    private ComentarioArtefactoService comentarioArtefactoService;

    @Autowired
    private ArtefactoService artefactoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory=new PodamFactoryImpl();

    private ComentarioEntity comentario=new ComentarioEntity();

    private UsuarioEntity autor;

    private List<ArtefactoEntity> artefactoList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from ArtefactoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
    }

    private void insertData(){
        comentario=factory.manufacturePojo(ComentarioEntity.class);
        entityManager.persist(comentario);
        autor=factory.manufacturePojo(UsuarioEntity.class);
        entityManager.persist(autor);
        for (int i=0;i<3;i++){
            ArtefactoEntity entity=factory.manufacturePojo(ArtefactoEntity.class);
            entityManager.persist(entity);
            artefactoList.add(entity);
        }
    }

    @Test
    void testAddArtefacto() throws AssertionFailedError, IllegalOperationException, EntityNotFoundException{

        ArtefactoEntity newArtefacto=factory.manufacturePojo(ArtefactoEntity.class);
        
        newArtefacto.setAutor(autor);
        artefactoService.crearArtefacto(newArtefacto);

        ArtefactoEntity artefactoEntity=comentarioArtefactoService.addArtefacto(comentario.getId(), newArtefacto.getId());

        assertNotNull(artefactoEntity);

        assertEquals(artefactoEntity.getId(), newArtefacto.getId());
        assertEquals(artefactoEntity.getTipo(), newArtefacto.getTipo());
        assertEquals(artefactoEntity.getContenido(), newArtefacto.getContenido());

        ArtefactoEntity lastArtefacto=comentarioArtefactoService.getArtefacto(comentario.getId());

        assertEquals(lastArtefacto.getId(), newArtefacto.getId());
        assertEquals(lastArtefacto.getTipo(), newArtefacto.getTipo());
        assertEquals(lastArtefacto.getContenido(), newArtefacto.getContenido());
    }

    @Test
    void testAddArtefactoInvalidComentario(){
        assertThrows(EntityNotFoundException.class, ()->{

            ArtefactoEntity newArtefacto=factory.manufacturePojo(ArtefactoEntity.class);
            entityManager.persist(newArtefacto);
            comentarioArtefactoService.addArtefacto(0L, newArtefacto.getId());
        });
    }

    @Test
    void testAddInvalidArtefacto(){

        assertThrows(EntityNotFoundException.class, ()->{

            comentarioArtefactoService.addArtefacto(comentario.getId(),0L);
        });
    }

    @Test
    void testAddArtefactoCreated(){

        assertThrows(IllegalOperationException.class, ()->{
            ComentarioEntity newComentario=factory.manufacturePojo( ComentarioEntity.class);
            ArtefactoEntity newArtefacto=factory.manufacturePojo(ArtefactoEntity.class);

            entityManager.persist(newArtefacto);

            newComentario.setArtefacto(newArtefacto);
            newArtefacto.setAutor(autor);

            entityManager.persist(newComentario);

            comentarioArtefactoService.addArtefacto(newComentario.getId(), newArtefacto.getId());
        });
    }

    @Test
    void testGetArtefacto() throws EntityNotFoundException{
        ComentarioEntity comentarioEntity=factory.manufacturePojo(ComentarioEntity.class);
        comentarioEntity.setArtefacto(artefactoList.getFirst());
        entityManager.persist(comentarioEntity);

        ArtefactoEntity answer=comentarioArtefactoService.getArtefacto(comentarioEntity.getId());
        
        assertNotNull(answer);
        assertEquals(artefactoList.getFirst().getContenido(), answer.getContenido());
        assertEquals(artefactoList.getFirst().getId(), answer.getId());
        assertEquals(artefactoList.getFirst().getTipo(), answer.getTipo());

    }

    @Test
    void testGetArtefactoInvalidComentario(){

    }

    @Test
    void testGetArtefactoNull(){

    }

    @Test 
    void testReplaceArtefacto(){

    }

    @Test
    void testReplaceArtefactoInvalidComentario(){

    }

    @Test
    void testReplaceArtefactoInvalid(){

    }

    @Test
    void testRemoveArtefacto(){

    }

    @Test 
    void testRemoveArtefactoInvalidComentario(){
        
    }
}
