package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.ArtefactoEntity;
import co.edu.uniandes.dse.aitutors.entities.UsuarioEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@DataJpaTest
@Transactional
@Import(ArtefactoService.class)
class ArtefactoServiceTest {

    @Autowired
    private ArtefactoService artefactoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory=new PodamFactoryImpl();

    private List<ArtefactoEntity> artefactoList=new ArrayList<>();

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UsuarioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from ArtefactoEntity").executeUpdate();
    }


    private void insertData() {

        usuarioEntity=factory.manufacturePojo(UsuarioEntity.class);
        entityManager.persist(usuarioEntity);

        for (int i = 0; i < 3; i++) {
            ArtefactoEntity artefactoEntity = factory.manufacturePojo(ArtefactoEntity.class);
            entityManager.persist(artefactoEntity);
            artefactoList.add(artefactoEntity);
        }
    }

    @Test
    void testCrearArtefacto() throws IllegalOperationException{
        ArtefactoEntity newEntity=factory.manufacturePojo(ArtefactoEntity.class);
        newEntity.setAutor(usuarioEntity);
        ArtefactoEntity result=artefactoService.crearArtefacto(newEntity);
        assertNotNull(result);
        ArtefactoEntity entity=entityManager.find(ArtefactoEntity.class,result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getContenido(), entity.getContenido());
        assertEquals(newEntity.getTipo(), entity.getTipo());
    }

    @Test
    void testCrearArtefactoNoTipo(){
        assertThrows(IllegalOperationException.class, ()->{
            ArtefactoEntity newEntity=factory.manufacturePojo(ArtefactoEntity.class);
            newEntity.setTipo("");
            artefactoService.crearArtefacto(newEntity);
        });
    }

    @Test
    void testCrearArtefactoNoContenido(){
        assertThrows(IllegalOperationException.class, ()->{
            ArtefactoEntity newEntity=factory.manufacturePojo(ArtefactoEntity.class);
            newEntity.setContenido("");
            artefactoService.crearArtefacto(newEntity);
        });
    }

    @Test
    void testCrearArtefactoAutorNull(){
        assertThrows(IllegalOperationException.class, ()->{
            ArtefactoEntity newEntity=factory.manufacturePojo(ArtefactoEntity.class);
            artefactoService.crearArtefacto(newEntity);
        });
    }

    @Test
    void testCrearArtefactoAutorNoCreado(){
        assertThrows(IllegalOperationException.class, ()->{
            ArtefactoEntity newEntity=factory.manufacturePojo(ArtefactoEntity.class);
            UsuarioEntity usuario=factory.manufacturePojo(UsuarioEntity.class);
            usuario.setId(0L);
            newEntity.setAutor(usuario);
            artefactoService.crearArtefacto(newEntity);
        });
    }

    @Test
    void testModificarContenidoBlank(){
        assertThrows(IllegalOperationException.class, ()->{
            ArtefactoEntity newEntity=artefactoList.get(0);
            artefactoService.modificarContenido("", newEntity);
        });
    }

    @Test
    void testModificarContenido() throws IllegalOperationException{
        ArtefactoEntity entity=artefactoList.get(0);
        String nuevoContenido="Nuevo Contenido";
        ArtefactoEntity respuesta=artefactoService.modificarContenido(nuevoContenido, entity);

        assertEquals(nuevoContenido, respuesta.getContenido());
        assertEquals(entity.getId(), respuesta.getId());
        assertEquals(entity.getTipo(), respuesta.getTipo());
    }

    @Test
    void testGetArtefacto() throws EntityNotFoundException {
        ArtefactoEntity entity = artefactoList.get(0);
        ArtefactoEntity result = artefactoService.getArtefacto(usuarioEntity.getId(), entity.getId());
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getContenido(), result.getContenido());
        assertEquals(entity.getTipo(), result.getTipo());
    }

    @Test
    void testGetArtefactoNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoService.getArtefacto(usuarioEntity.getId(), 0L);
        });
    }

    @Test
    void testUpdateArtefactoNotFound() {
        ArtefactoEntity newEntity = factory.manufacturePojo(ArtefactoEntity.class);
        newEntity.setId(0L);
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoService.updateArtefacto(usuarioEntity.getId(), newEntity);
        });
    }

    @Test
    void testDeleteArtefacto() throws EntityNotFoundException {
        ArtefactoEntity entity = artefactoList.get(0);
        artefactoService.deleteArtefacto(usuarioEntity.getId(), entity.getId());
        ArtefactoEntity deleted = entityManager.find(ArtefactoEntity.class, entity.getId());
        assertEquals(null, deleted);
    }

    @Test
    void testDeleteArtefactoNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            artefactoService.deleteArtefacto(usuarioEntity.getId(), 0L);
        });
    }

}
