package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import({UsuarioComentarioService.class, ComentarioService.class})
class UsuarioComentarioServiceTest {

    @Autowired
    private UsuarioComentarioService usuarioComentarioService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private UsuarioEntity usuario = new UsuarioEntity();

    private List<ComentarioEntity> comentarioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ArtefactoEntity").executeUpdate();  // Limpiar artefactos
        entityManager.getEntityManager().createQuery("delete from AccionEntity").executeUpdate();  // Limpiar acciones
    }

    private void insertData() {
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        entityManager.persist(usuario);

        // Crear y persistir una acción válida
        AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
        entityManager.persist(accion);

        // Crear y persistir un artefacto válido con la acción asociada
        ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
        artefacto.setAccion(accion);  // Asociar la acción al artefacto
        entityManager.persist(artefacto);

        // Crear comentarios y asociarlos al usuario y artefacto
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entity.setAutor(usuario);
            entity.setArtefacto(artefacto);  // Asociar el artefacto al comentario
            entityManager.persist(entity);
            comentarioList.add(entity);
            usuario.getComentarios().add(entity);
        }
    }

    @Test
    void testAddComentario() throws EntityNotFoundException, IllegalOperationException {
        // Crear un nuevo comentario
        ComentarioEntity newComentario = factory.manufacturePojo(ComentarioEntity.class);
        
        // Crear una acción válida y un artefacto válido
        AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
        entityManager.persist(accion);
        
        ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
        artefacto.setAccion(accion);  // Asociar la acción al artefacto
        entityManager.persist(artefacto);
        
        newComentario.setAutor(usuario);  // Asociar autor
        newComentario.setArtefacto(artefacto);  // Asociar artefacto

        comentarioService.createComentario(newComentario);

        // Asociar el comentario al usuario
        ComentarioEntity comentarioEntity = usuarioComentarioService.addComentario(newComentario.getId(), usuario.getId());

        assertNotNull(comentarioEntity);
        assertEquals(newComentario.getId(), comentarioEntity.getId());
        assertEquals(newComentario.getFecha(), comentarioEntity.getFecha());
        assertEquals(newComentario.getContenido(), comentarioEntity.getContenido());

        // Verificar que el comentario se ha asociado correctamente al usuario
        ComentarioEntity lastComentario = usuarioComentarioService.getComentario(usuario.getId(), newComentario.getId());
        assertEquals(newComentario.getId(), lastComentario.getId());
        assertEquals(newComentario.getFecha(), lastComentario.getFecha());
        assertEquals(newComentario.getContenido(), lastComentario.getContenido());
    }

    @Test
    void testAddComentarioInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity newComentario = factory.manufacturePojo(ComentarioEntity.class);
            
            // Crear e insertar una acción válida y un artefacto válido
            AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accion);
            
            ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
            artefacto.setAccion(accion);
            entityManager.persist(artefacto);
            
            newComentario.setAutor(usuario);
            newComentario.setArtefacto(artefacto);
            entityManager.persist(newComentario);
            
            usuarioComentarioService.addComentario(0L, newComentario.getId());
        });
    }

    @Test
    void testAddInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioComentarioService.addComentario(usuario.getId(), 0L);
        });
    }

    @Test
    void testGetComentarios() throws EntityNotFoundException {
        List<ComentarioEntity> comentarioEntities = usuarioComentarioService.getComentarios(usuario.getId());

        assertEquals(comentarioEntities.size(), comentarioList.size());

        for (int i = 0; i < comentarioList.size(); i++) {
            assertTrue(comentarioEntities.contains(comentarioList.get(i)));
        }
    }

    @Test
    void testGetComentariosInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioComentarioService.getComentarios(0L);
        });
    }

    @Test
    void testGetComentario() throws EntityNotFoundException, IllegalOperationException {
        ComentarioEntity comentarioEntity = comentarioList.get(0);  // Obtener el primer comentario
        ComentarioEntity newComentario = usuarioComentarioService.getComentario(usuario.getId(), comentarioEntity.getId());

        assertNotNull(newComentario);
        assertEquals(comentarioEntity.getId(), newComentario.getId());
        assertEquals(comentarioEntity.getFecha(), newComentario.getFecha());
        assertEquals(comentarioEntity.getContenido(), newComentario.getContenido());
    }

    @Test
    void testGetComentarioInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentarioEntity = comentarioList.get(0);
            usuarioComentarioService.getComentario(0L, comentarioEntity.getId());
        });
    }

    @Test
    void testGetInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioComentarioService.getComentario(usuario.getId(), 0L);
        });
    }

    @Test
    void testGetComentarioNotAssociatedAuthor() {
        assertThrows(IllegalOperationException.class, () -> {
            UsuarioEntity usuarioEntity = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(usuarioEntity);

            ComentarioEntity comentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
            AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accion);

            ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
            artefacto.setAccion(accion);
            entityManager.persist(artefacto);

            comentarioEntity.setArtefacto(artefacto);
            entityManager.persist(comentarioEntity);

            usuarioComentarioService.getComentario(usuarioEntity.getId(), comentarioEntity.getId());
        });
    }

    @Test
    void testReplaceComentarios() throws EntityNotFoundException {
        List<ComentarioEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entity.setAutor(usuario);
            
            AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
            entityManager.persist(accion);
            
            ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
            artefacto.setAccion(accion);  // Asociar la acción al artefacto
            entityManager.persist(artefacto);
            
            entity.setArtefacto(artefacto);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }

        List<ComentarioEntity> comentarioEntities = usuarioComentarioService.replaceComentario(usuario.getId(), nuevaLista);

        for (ComentarioEntity item : nuevaLista) {
            assertTrue(comentarioEntities.contains(item));
        }
    }

    @Test
    void testReplaceComentarioInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ComentarioEntity> nuevaLista = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
                entity.setAutor(usuario);
                
                AccionEntity accion = factory.manufacturePojo(AccionEntity.class);
                entityManager.persist(accion);
                
                ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
                artefacto.setAccion(accion);
                entityManager.persist(artefacto);
                
                entity.setArtefacto(artefacto);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            usuarioComentarioService.replaceComentario(0L, nuevaLista);
        });
    }

    @Test
    void testReplaceInvalidComentarios() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ComentarioEntity> nuevaLista = new ArrayList<>();
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entity.setId(0L);
            nuevaLista.add(entity);
            usuarioComentarioService.replaceComentario(usuario.getId(), nuevaLista);
        });
    }
}
