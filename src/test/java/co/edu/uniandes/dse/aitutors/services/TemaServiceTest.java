package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TemaService.class)
class TemaServiceTest {
    @Autowired
    private TemaService temaService;

    @MockBean
    private TemaRepository temaRepository;

    private TemaEntity temaEntity;
    private DocumentoEntity documentoEntity1;
    private DocumentoEntity documentoEntity2;

    @BeforeEach
    void setUp() {
        temaEntity = new TemaEntity();
        temaEntity.setId(1L);
        documentoEntity1 = new DocumentoEntity();
        documentoEntity1.setId(1L);
        documentoEntity2 = new DocumentoEntity();
        documentoEntity2.setId(2L);
        List<DocumentoEntity> documentos = new ArrayList<>();
        documentos.add(documentoEntity1);
        documentos.add(documentoEntity2);
        temaEntity.setDocumentos(documentos);
    }

    @Test
    void testGetDocumentos() throws IllegalOperationException {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        List<DocumentoEntity> result = temaService.getDocumentos(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(documentoEntity1));
        assertTrue(result.contains(documentoEntity2));
    }

    @Test
    void testGetDocumentosTemaNoExistente() {
        when(temaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalOperationException.class, () -> {
            temaService.getDocumentos(1L);
        });
    }
}
