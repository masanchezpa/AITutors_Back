package co.edu.uniandes.dse.aitutors.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
@Import({UsuarioComentarioService.class,ComentarioService.class})
class UsuarioComentarioServiceTest {

    @Autowired
    private UsuarioComentarioService usuarioComentarioService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory=new PodamFactoryImpl();

    private UsuarioEntity usuario=new UsuarioEntity();

    private List<ComentarioEntity> comentarioList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        clearData();
        insertData();
    }

    private void clearData(){
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ComentarioEntity").executeUpdate();
    }

    private void insertData(){
        usuario=factory.manufacturePojo(UsuarioEntity.class);
        entityManager.persist(usuario);
        for (int i=0;i<3;i++){
            ComentarioEntity entity=factory.manufacturePojo(ComentarioEntity.class);
            entity.setAutor(usuario);
            entityManager.persist(entity);
            comentarioList.add(entity);
            usuario.getComentarios().add(entity);
        }
    }

    @Test
    void testAddComentario() throws EntityNotFoundException, IllegalOperationException{
        ComentarioEntity newComentario=factory.manufacturePojo(ComentarioEntity.class);
        comentarioService.createComentario(newComentario);

        ComentarioEntity comentarioEntity=usuarioComentarioService.addComentario(newComentario.getId(),usuario.getId());
        
        assertNotNull(comentarioEntity);

        assertEquals(newComentario.getId(), comentarioEntity.getId());
        assertEquals(newComentario.getFecha(), comentarioEntity.getFecha());
        assertEquals(newComentario.getContenido(), comentarioEntity.getContenido());
        
        ComentarioEntity lastComentario=usuarioComentarioService.getComentario(usuario.getId(),newComentario.getId());
        
        assertEquals(newComentario.getId(), lastComentario.getId());
        assertEquals(newComentario.getFecha(), lastComentario.getFecha());
        assertEquals(newComentario.getContenido(), lastComentario.getContenido());

    }
    
    @Test
    void testAddComentarioInvalidUsuario(){

        assertThrows(EntityNotFoundException.class, ()->{

            ComentarioEntity newComentario=factory.manufacturePojo(ComentarioEntity.class);
            entityManager.persist(newComentario);
            usuarioComentarioService.addComentario(0L, newComentario.getId());
        });
    }

    @Test
    void testAddInvalidComentario(){

        assertThrows(EntityNotFoundException.class, ()->{

            usuarioComentarioService.addComentario(usuario.getId(),0L);
        });
    }

    @Test
    void testGetComentarios() throws EntityNotFoundException{
        List<ComentarioEntity> comentarioEntities=usuarioComentarioService.getComentarios(usuario.getId());

        assertEquals(comentarioEntities.size(), comentarioList.size());

        for (int i=0;i<comentarioList.size();i++){
            assertTrue(comentarioEntities.contains(comentarioList.get(i)));
        }
    }

    @Test
	void testGetBooksInvalidUsuario() {
		assertThrows(EntityNotFoundException.class, () -> {
			usuarioComentarioService.getComentarios(0L);
		});
	}

    @Test
    void testGetComentario() throws EntityNotFoundException, IllegalOperationException{
        ComentarioEntity comentarioEntity=comentarioList.getFirst();
        ComentarioEntity newComentario=usuarioComentarioService.getComentario(usuario.getId(),comentarioEntity.getId());
        
        assertNotNull(newComentario);
        assertEquals(comentarioEntity.getId(), newComentario.getId());
        assertEquals(comentarioEntity.getFecha(), newComentario.getFecha());
        assertEquals(comentarioEntity.getContenido(), newComentario.getContenido());
    }

    @Test
	void testGetComentarioInvalidUsuario() {
		assertThrows(EntityNotFoundException.class, () -> {
			ComentarioEntity comentarioEntity = comentarioList.getFirst();
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
    void testGetComentarioNotAssociatedAuthor(){
        assertThrows(IllegalOperationException.class, () -> {
			UsuarioEntity usuarioEntity = factory.manufacturePojo(UsuarioEntity.class);
			entityManager.persist(usuarioEntity);


            ComentarioEntity comentarioEntity=factory.manufacturePojo(ComentarioEntity.class);
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
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		
		List<ComentarioEntity> comentarioEntities=usuarioComentarioService.replaceComentario(usuario.getId(), nuevaLista);
		
		for (ComentarioEntity item : nuevaLista) {
			assertTrue(comentarioEntities.contains(item));
		}
	}

    @Test
    void testReplaceComentarioInvalidUsuario(){
        assertThrows(EntityNotFoundException.class, () -> {
			List<ComentarioEntity> nuevaLista = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
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
