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

import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(ComentarioService.class)
class ComentarioServiceTest {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ComentarioEntity> comentarioList = new ArrayList<>();
    private UsuarioEntity testUsuario;
    private ArtefactoEntity testArtefacto;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM ArtefactoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM UsuarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM AccionEntity").executeUpdate();
    }

    private void insertData() {
        testUsuario = factory.manufacturePojo(UsuarioEntity.class);
        entityManager.persist(testUsuario);

        AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
        entityManager.persist(accion);

        testArtefacto = factory.manufacturePojo(ArtefactoEntity.class);
        testArtefacto.setAccion(accion);
        entityManager.persist(testArtefacto);

        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            comentario.setAutor(testUsuario);
            comentario.setArtefacto(testArtefacto);
            entityManager.persist(comentario);
            comentarioList.add(comentario);
        }
    }

    @Test
    void testCreateComentario() throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setAutor(testUsuario);
        newEntity.setArtefacto(testArtefacto);

        ComentarioEntity result = comentarioService.createComentario(newEntity);
        assertNotNull(result);

        ComentarioEntity entity = entityManager.find(ComentarioEntity.class, result.getId());
        assertEquals(newEntity.getContenido(), entity.getContenido());
        assertEquals(newEntity.getFecha(), entity.getFecha());
        assertEquals(testUsuario.getId(), entity.getAutor().getId());
        assertEquals(testArtefacto.getId(), entity.getArtefacto().getId());
    }

    @Test
    void testCreateComentarioWithoutAccion() {
        ArtefactoEntity artefactoWithoutAccion = factory.manufacturePojo(ArtefactoEntity.class);
        entityManager.persist(artefactoWithoutAccion);

        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setAutor(testUsuario);
        newEntity.setArtefacto(artefactoWithoutAccion);

        assertThrows(IllegalOperationException.class, () -> comentarioService.createComentario(newEntity));
    }

    @Test
    void testCreateComentarioWithInvalidUser() {
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setArtefacto(testArtefacto);

        UsuarioEntity invalidUsuario = factory.manufacturePojo(UsuarioEntity.class);
        invalidUsuario.setId(999L);
        newEntity.setAutor(invalidUsuario);

        assertThrows(EntityNotFoundException.class, () -> comentarioService.createComentario(newEntity));
    }


    @Test
    void testGetComentario() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(0);
        ComentarioEntity result = comentarioService.getComentario(entity.getId());

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getContenido(), result.getContenido());
        assertEquals(entity.getFecha(), result.getFecha());
    }

    @Test
    void testGetInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> comentarioService.getComentario(0L));
    }

    @Test
    void testUpdateComentario() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(0);
        ComentarioEntity updatedEntity = factory.manufacturePojo(ComentarioEntity.class);
        updatedEntity.setId(entity.getId());

        ComentarioEntity result = comentarioService.updateComentario(entity.getId(), updatedEntity);
        assertEquals(updatedEntity.getContenido(), result.getContenido());
        assertEquals(updatedEntity.getFecha(), result.getFecha());
    }

    @Test
    void testUpdateInvalidComentario() {
        ComentarioEntity updatedEntity = factory.manufacturePojo(ComentarioEntity.class);
        updatedEntity.setId(0L);
        assertThrows(EntityNotFoundException.class, () -> comentarioService.updateComentario(0L, updatedEntity));
    }

    @Test
    void testDeleteComentario() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(1);
        comentarioService.deleteComentario(entity.getId());
        ComentarioEntity deleted = entityManager.find(ComentarioEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> comentarioService.deleteComentario(0L));
    }
}
