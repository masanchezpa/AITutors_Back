package co.edu.uniandes.dse.aitutors.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;





@DataJpaTest
@Transactional
@Import(TemaService.class)
class TemaServiceTest {

    @Autowired
    private TemaService temaService;

    @Autowired
    private TestEntityManager entityManager;
  
     private PodamFactory factory = new PodamFactoryImpl();

    private List<TemaEntity> temaList = new ArrayList<>();
    private List<DocumentoEntity> documentoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from DocumentoEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
            entityManager.persist(documentoEntity);
            documentoList.add(documentoEntity);
        }
        for (int i = 0; i < 3; i++) {
            TemaEntity temaEntity = factory.manufacturePojo(TemaEntity.class);
            temaEntity.setDocumentos(documentoList);
            entityManager.persist(temaEntity);
            temaList.add(temaEntity);
        }
    }

    @Test
    void testCreateTema() {
        TemaEntity newTema = factory.manufacturePojo(TemaEntity.class);
        TemaEntity result = temaService.createTema(newTema);
        assertNotNull(result);
        TemaEntity entity = entityManager.find(TemaEntity.class, result.getId());
        assertEquals(newTema.getId(), entity.getId());
    }

    @Test
    void testGetTemas() {
        List<TemaEntity> list = temaService.getTemas();
        assertEquals(temaList.size(), list.size());
        for (TemaEntity entity : list) {
            boolean found = false;
            for (TemaEntity storedEntity : temaList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetTema() throws EntityNotFoundException {
        TemaEntity entity = temaList.get(0);
        TemaEntity resultEntity = temaService.getTema(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
    }

    @Test
    void testGetInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaService.getTema(0L);
        });
    }

    @Test
    void testUpdateTema() throws EntityNotFoundException {
        TemaEntity entity = temaList.get(0);
        TemaEntity updatedEntity = factory.manufacturePojo(TemaEntity.class);
        updatedEntity.setId(entity.getId());
        temaService.updateTema(entity.getId(), updatedEntity);
        TemaEntity resp = entityManager.find(TemaEntity.class, entity.getId());
        assertEquals(updatedEntity.getId(), resp.getId());
    }

    @Test
    void testUpdateInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            TemaEntity updatedEntity = factory.manufacturePojo(TemaEntity.class);
            updatedEntity.setId(0L);
            temaService.updateTema(0L, updatedEntity);
        });
    }

    @Test
    void testDeleteTema() throws EntityNotFoundException {
        TemaEntity entity = temaList.get(1);
        temaService.deleteTema(entity.getId());
        TemaEntity deleted = entityManager.find(TemaEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testDeleteInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            temaService.deleteTema(0L);
        });
    }

    @Test
    void testGetDocumentos() throws IllegalOperationException {
        TemaEntity tema = temaList.get(0);
        List<DocumentoEntity> result = temaService.getDocumentos(tema.getId());
        assertNotNull(result);
        assertEquals(documentoList.size(), result.size());
        for (DocumentoEntity documento : result) {
            assertTrue(documentoList.contains(documento));
        }
    }

    @Test
    void testGetDocumentosTemaNoExistente() {
        assertThrows(IllegalOperationException.class, () -> {
            temaService.getDocumentos(0L);
        });
    }
}
