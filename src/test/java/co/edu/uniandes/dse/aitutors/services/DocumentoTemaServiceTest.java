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


import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
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
@Import(DocumentoTemaService.class)
class DocumentoTemaServiceTest {

	private PodamFactory factory = new PodamFactoryImpl();

	@Autowired
	private DocumentoTemaService documentoTemaService;

	@Autowired
	private TestEntityManager entityManager;

	private List<TemaEntity> temaList = new ArrayList<>();
	private List<DocumentoEntity> documentoList = new ArrayList<>();

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
		entityManager.getEntityManager().createQuery("delete from DocumentoEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from TemaEntity").executeUpdate();
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */

    private void insertData() {
        for (int i = 0; i < 2; i++) {
            DocumentoEntity Documento = factory.manufacturePojo(DocumentoEntity.class);
            entityManager.persist(Documento);
            documentoList.add(Documento);
        }
        for (int i = 0; i < 2; i++) {
            TemaEntity tema = factory.manufacturePojo(TemaEntity.class);
            entityManager.persist(tema);
            temaList.add(tema);
        }
        documentoList.get(0).setTema(temaList.get(0));
        entityManager.persist(documentoList.get(0));
    }

    @Test
    void testAddTema() throws EntityNotFoundException, IllegalOperationException {
        DocumentoEntity Documento = documentoList.get(1);
        TemaEntity tema = temaList.get(1);
        TemaEntity response = documentoTemaService.addTema(Documento.getId(), tema.getId());
        assertNotNull(response);
        assertEquals(tema.getId(), response.getId());
    }

    @Test
    void testAddInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            DocumentoEntity Documento = documentoList.get(1);
            documentoTemaService.addTema(Documento.getId(), 0L);
        });
    }
    
    @Test
    void testAddTemaInvalidDocumento() {
        assertThrows(EntityNotFoundException.class, () -> {
            TemaEntity tema = temaList.get(0);
            documentoTemaService.addTema(0L, tema.getId());
        });
    }
	
	@Test
    void testGetTema() throws EntityNotFoundException {
        DocumentoEntity entity = documentoList.get(0);
        TemaEntity response = documentoTemaService.getTema(entity.getId());
        assertNotNull(response);
        assertEquals(entity.getTema().getId(), response.getId());
    }
	
    @Test
    void testGetTemaInvalidDocumento() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoTemaService.getTema(0L);
        });
    }

	


    @Test
    void testReplaceInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoTemaService.replaceTema(0L, temaList.get(1).getId());
        });
    }
    

    @Test
    void testReplaceTema() throws EntityNotFoundException {
        TemaEntity tema = temaList.get(0);
        DocumentoEntity Documento = documentoList.get(1);
        TemaEntity response = documentoTemaService.replaceTema(Documento.getId(), tema.getId());
        assertNotNull(response);
        assertEquals(tema.getId(), response.getId());
    }

    @Test
    void testRemoveInvalidTema() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoTemaService.removeTema(0L);
        });
    }

    @Test
    void testReplaceTemaInvalidDocumento() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoTemaService.replaceTema(0L, temaList.get(1).getId());
        });
    }

	
	@Test
    void testRemoveDocumento() throws EntityNotFoundException {
        documentoTemaService.removeTema(documentoList.get(0).getId());
        DocumentoEntity documento = entityManager.find(DocumentoEntity.class, documentoList.get(0).getId());
        assertNull(documento.getTema());

    }

    @Test
    void testRemoveInvalidDocumento() {
        assertThrows(EntityNotFoundException.class, () -> {
            documentoTemaService.removeTema(0L);
        });
    }
    
    @Test
    void testRemoveTema() {
    // Suponiendo que el ID 9999 no existe en la base de datos
    Long nonExistentId = 9999L;
    
    assertThrows(EntityNotFoundException.class, () -> {
        documentoTemaService.removeTema(nonExistentId);
    });
}





}

