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


import co.edu.uniandes.dse.aitutors.entities.ComentarioEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
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
@Import(ComentarioUsuarioService.class)
class ComentarioUsuarioServiceTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Autowired
	private ComentarioUsuarioService comentarioUsuarioService;

	@Autowired
	private TestEntityManager entityManager;

	private List<UsuarioEntity> usuarioList = new ArrayList<>();
	private List<ComentarioEntity> comentarioList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */

    private void insertData(){
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(entity);
            usuarioList.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            entityManager.persist(entity);
            comentarioList.add(entity);
            if (i == 0) {
                entity.setAutor(usuarioList.get(0));
            }
        }
    }
    
    @Test
    void testAddUsuario() throws EntityNotFoundException {
        UsuarioEntity entity = usuarioList.get(0);
        ComentarioEntity comentarioEntity = comentarioList.get(1);
        UsuarioEntity response = comentarioUsuarioService.addUsuario( comentarioEntity.getId(), entity.getId());
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
    }
	
	@Test
    void testAddInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ComentarioEntity comentarioEntity = comentarioList.get(1);
            comentarioUsuarioService.addUsuario(0L, comentarioEntity.getId());
        });
    }
	
    @Test
    void testAddUsuarioInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            UsuarioEntity entity = usuarioList.get(0);
            comentarioUsuarioService.addUsuario(entity.getId(), 0L);
        });
    }

	@Test
    void testGetUsuario() throws EntityNotFoundException {
        ComentarioEntity entity = comentarioList.get(0);
        UsuarioEntity response = comentarioUsuarioService.getUsuario(entity.getId());
        assertNotNull(response);
        assertEquals(entity.getAutor().getId(), response.getId());
    }
	
    @Test
    void testGetUsuarioInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.getUsuario(0L);
        });
    }

    

	@Test
    void testReplaceUsuario() throws EntityNotFoundException {
        UsuarioEntity entity = usuarioList.get(0);
        ComentarioEntity comentarioEntity = comentarioList.get(1);
        UsuarioEntity response = comentarioUsuarioService.replaceUsuario(comentarioEntity.getId(), entity.getId() );
        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
    }

	
	@Test
	void testReplaceInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.replaceUsuario(0L, comentarioList.get(1).getId());
        });
    }


	@Test
	void testReplaceComentarioInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.replaceUsuario(usuarioList.get(0).getId(), 0L);
        });
    }
	
    @Test
    void testRemoveComentario() throws EntityNotFoundException {
        comentarioUsuarioService.removeComentario(usuarioList.get(0).getId());
        ComentarioEntity response = comentarioUsuarioService.getComentario(usuarioList.get(0).getId());
        assertNull(response);
    }

    @Test
    void testRemoveInvalidComentario() {
        assertThrows(EntityNotFoundException.class, () -> {
            comentarioUsuarioService.removeUsuario(0L);
        });
    }

	@Test
    void testRemoveUsuario() throws EntityNotFoundException {
        comentarioUsuarioService.removeUsuario(comentarioList.get(0).getId());
        UsuarioEntity response = comentarioUsuarioService.getUsuario(comentarioList.get(0).getId());
        assertNull(response);
    }

}
