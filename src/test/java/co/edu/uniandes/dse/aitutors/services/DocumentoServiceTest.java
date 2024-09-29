package co.edu.uniandes.dse.aitutors.services;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import java.util.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(DocumentoService.class)
class DocumentoServiceTest {
    
    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

   private List<DocumentoEntity> documentoList = new ArrayList<>();

    private DocumentoEntity documento;

    private TemaEntity tema;

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from DocumentoEntity");
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DocumentoEntity documento = factory.manufacturePojo(DocumentoEntity.class);
            entityManager.persist(documento);
            documentoList.add(documento);
        }
        tema = factory.manufacturePojo(TemaEntity.class);
        entityManager.persist(tema);

        documento = documentoList.get(0);
        documento.setTema(tema);
        entityManager.persist(documento);
    }

    @Test
    void testCrearDocumento() throws IllegalOperationException {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        documentoEntity.setTema(tema);
        DocumentoEntity result = documentoService.createDocumento(documentoEntity);
        assertNotNull(result);
        assertEquals(documentoEntity.getTipo(), result.getTipo());
        assertEquals(documentoEntity.getContenido(), result.getContenido());
        assertEquals(documentoEntity.getId(), result.getId());
        assertEquals(documentoEntity.getTema().getId(), result.getTema().getId());
    }

    @Test
    void testCrearDocumentoTipoVacio() {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        documentoEntity.setTipo("");
        assertThrows(IllegalOperationException.class, () -> documentoService.createDocumento(documentoEntity));
    }

    @Test
    void testCrearDocumentoContenidoVacio() {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        documentoEntity.setContenido("");
        assertThrows(IllegalOperationException.class, () -> documentoService.createDocumento(documentoEntity));
    }

    @Test
    void testCrearDocumentoTemaNulo() {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        documentoEntity.setTema(null);
        assertThrows(IllegalOperationException.class, () -> documentoService.createDocumento(documentoEntity));
    }

    @Test
    void testCrearDocumentoTemaNoExiste() {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        TemaEntity temaEntity = factory.manufacturePojo(TemaEntity.class);
        temaEntity.setId(0L);
        documentoEntity.setTema(temaEntity);
        assertThrows(IllegalOperationException.class, () -> documentoService.createDocumento(documentoEntity));
    }

    @Test
    void testGetDocumentos() {
        List<DocumentoEntity> result = documentoService.getDocumentos();
        assertEquals(documentoList.size(), result.size());
        for (DocumentoEntity documentoEntity : result) {
            boolean found = false;
            for (DocumentoEntity entity : documentoList) {
                if (documentoEntity.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    void testGetDocumento() throws IllegalOperationException,EntityNotFoundException {
        DocumentoEntity result = documentoService.getDocumento(documento.getId());
        assertNotNull(result);
        assertEquals(documento.getId(), result.getId());
        assertEquals(documento.getTipo(), result.getTipo());
        assertEquals(documento.getContenido(), result.getContenido());
        assertEquals(documento.getTema().getId(), result.getTema().getId());
    }

    @Test
    void testGetDocumentoNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoService.getDocumento(0L);
        });
    }

    @Test
    void testUpdateDocumento() throws IllegalOperationException,EntityNotFoundException {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        documentoEntity.setTema(tema);
        documentoEntity.setId(documento.getId());

        DocumentoEntity result = documentoService.updateDocumento(documento.getId(), documentoEntity);

        assertNotNull(result);
        assertEquals(documentoEntity.getId(), result.getId());
        assertEquals(documentoEntity.getTipo(), result.getTipo());
        assertEquals(documentoEntity.getContenido(), result.getContenido());
        assertEquals(documentoEntity.getTema().getId(), result.getTema().getId());
    }

    @Test
    void testUpdateDocumentoNoExiste() {
        DocumentoEntity documentoEntity = factory.manufacturePojo(DocumentoEntity.class);
        assertThrows(IllegalOperationException.class, () -> documentoService.updateDocumento(0L, documentoEntity));
    }

    @Test
    void testDeleteDocumento() throws IllegalOperationException {
        documentoService.deleteDocumento(documento.getId());
        assertThrows(EntityNotFoundException.class, () -> documentoService.getDocumento(documento.getId()));
    }

    @Test
    void testDeleteDocumentoNoExiste() {
        assertThrows(IllegalOperationException.class, () -> documentoService.deleteDocumento(0L));
    }
}