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
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(ComentarioUsuarioService.class)
class ComentarioUsuarioServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private ComentarioUsuarioService comentarioUsuarioService;

    @Autowired
    private TestEntityManager entityManager;

    private List<ComentarioEntity> comentarioList = new ArrayList<>();
    private List<UsuarioEntity> usuarioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 2; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            entityManager.persist(comentario);
            comentarioList.add(comentario);
        }
        for (int i = 0; i < 2; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(usuario);
            usuarioList.add(usuario);
        }
        comentarioList.get(0).setAutor(usuarioList.get(0));
        entityManager.persist(comentarioList.get(0));
    }

    @Test
    void testAddUsuario() throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioList.get(1);
        UsuarioEntity usuario = usuarioList.get(1);
        UsuarioEntity response = comentarioUsuarioService.addUsuario(comentario.getId(), usuario.getId());
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    void testAddInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentario = comentarioList.get(1);
            comentarioUsuarioService.addUsuario(comentario.getId(), 0L);
        });
    }

    @Test
    void testAddUsuarioInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            UsuarioEntity usuario = usuarioList.get(0);
            comentarioUsuarioService.addUsuario(0L, usuario.getId());
        });
    }

    @Test
    void testGetUsuario() throws EntityNotFoundException {
        ComentarioEntity comentario = comentarioList.get(0);
        UsuarioEntity response = comentarioUsuarioService.getUsuario(comentario.getId());
        assertNotNull(response);
        assertEquals(comentario.getAutor().getId(), response.getId());
    }

    @Test
    void testGetUsuarioInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.getUsuario(0L);
        });
    }

    @Test
    void testReplaceUsuario() throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioList.get(0);
        ComentarioEntity comentario = comentarioList.get(1);
        UsuarioEntity response = comentarioUsuarioService.replaceUsuario(comentario.getId(), usuario.getId());
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
    }

    @Test
    void testReplaceInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.replaceUsuario(0L, usuarioList.get(1).getId());
        });
    }

    @Test
    void testRemoveUsuario() throws EntityNotFoundException {
        comentarioUsuarioService.removeUsuario(comentarioList.get(0).getId());
        ComentarioEntity comentario = entityManager.find(ComentarioEntity.class, comentarioList.get(0).getId());
        assertNull(comentario.getAutor());
    }

    @Test
    void testRemoveInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.removeUsuario(0L);
        });
    }
}