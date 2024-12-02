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
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;

import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(ArtefactoUsuarioService.class)
class ArtefactoUsuarioServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private ArtefactoUsuarioService artefactoUsuarioService;

    @Autowired
    private TestEntityManager entityManager;

    private List<ArtefactoEntity> artefactoList = new ArrayList<>();
    private List<UsuarioEntity> usuarioList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ArtefactoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 2; i++) {
            ArtefactoEntity artefacto = factory.manufacturePojo(ArtefactoEntity.class);
            entityManager.persist(artefacto);
            artefactoList.add(artefacto);
        }
        for (int i = 0; i < 2; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            entityManager.persist(usuario);
            usuarioList.add(usuario);
        }
        artefactoList.get(0).setAutor(usuarioList.get(0));
        entityManager.persist(artefactoList.get(0));
    }

    @Test
    void testAddArtefacto() throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioList.get(1);
        ArtefactoEntity artefacto = artefactoList.get(1);
        ArtefactoEntity response = artefactoUsuarioService.addArtefacto(usuario.getId(), artefacto.getId());
        assertNotNull(response);
        assertEquals(artefacto.getId(), response.getId());
        assertEquals(usuario.getId(), response.getAutor().getId());
    }

    @Test
    void testAddInvalidArtefacto() {
        assertThrows(EntityNotFoundException.class, () -> {
            UsuarioEntity usuario = usuarioList.get(1);
            artefactoUsuarioService.addArtefacto(usuario.getId(), 0L);
        });
    }

    @Test
    void testAddArtefactoInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            ArtefactoEntity artefacto = artefactoList.get(0);
            artefactoUsuarioService.addArtefacto(0L, artefacto.getId());
        });
    }

    @Test
    void testGetArtefactosInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.getArtefactos(0L);
        });
    }

    @Test
    void testGetArtefacto() throws EntityNotFoundException, IllegalOperationException {
        UsuarioEntity usuario = usuarioList.get(0);
        ArtefactoEntity artefacto = artefactoList.get(0);
        ArtefactoEntity response = artefactoUsuarioService.getArtefacto(usuario.getId(), artefacto.getId());
        assertNotNull(response);
        assertEquals(artefacto.getId(), response.getId());
    }

    @Test
    void testGetArtefactoInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.getArtefacto(0L, artefactoList.get(0).getId());
        });
    }

    @Test
    void testGetArtefactoInvalidArtefacto() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.getArtefacto(usuarioList.get(0).getId(), 0L);
        });
    }

    @Test
    void testGetArtefactoIllegalOperation() {
        assertThrows(IllegalOperationException.class, () -> {
            UsuarioEntity usuario = usuarioList.get(1); // Usuario diferente al autor
            ArtefactoEntity artefacto = artefactoList.get(0); // Artefacto asociado al otro usuario
            artefactoUsuarioService.getArtefacto(usuario.getId(), artefacto.getId());
        });
    }


    @Test
    void testAddArtefactosInvalidUsuario() {
        List<ArtefactoEntity> newArtefactos = new ArrayList<>();
        newArtefactos.add(artefactoList.get(0));
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.addArtefactos(0L, newArtefactos);
        });
    }

    @Test
    void testAddArtefactoInvalidArtefacto() {
        Long invalidArtefactoId = 9999L; 
        Long usuarioId = usuarioList.get(0).getId(); 
    
        
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.addArtefacto(usuarioId, invalidArtefactoId);
        });
    
        
        assertEquals(ErrorMessage.ARTEFACTO_NOT_FOUND, thrown.getMessage());
    }
    

    @Test
    void testRemoveArtefacto() throws EntityNotFoundException {
        UsuarioEntity usuario = usuarioList.get(0);
        ArtefactoEntity artefacto = artefactoList.get(0);
        artefactoUsuarioService.removeArtefacto(usuario.getId(), artefacto.getId());
        ArtefactoEntity removedArtefacto = entityManager.find(ArtefactoEntity.class, artefacto.getId());
        assertNull(removedArtefacto.getAutor());
    }

    @Test
    void testRemoveArtefactoInvalidUsuario() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.removeArtefacto(0L, artefactoList.get(0).getId());
        });
    }

    @Test
    void testRemoveArtefactoInvalidArtefacto() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoUsuarioService.removeArtefacto(usuarioList.get(0).getId(), 0L);
        });
    }
}
