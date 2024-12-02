package co.edu.uniandes.dse.aitutors.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;


import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;



@DataJpaTest
@Import(TemaDocumentoService.class)
class TemaDocumentoServiceTest {
    
    @Autowired
    private TemaDocumentoService temaDocumentoService;

    @MockBean
    private TemaRepository temaRepository;

    @MockBean
    private DocumentoRepository documentoRepository;

    private TemaEntity temaEntity;
    private DocumentoEntity documentoEntity;

    @BeforeEach
    void setUp() {
        temaEntity = new TemaEntity();
        temaEntity.setId(1L);
        temaEntity.setDocumentos(new ArrayList<>());
        
        documentoEntity = new DocumentoEntity();
        documentoEntity.setId(1L);
        documentoEntity.setTema(temaEntity);
    }

    @Test
    void testAgregarDocumento() throws IllegalOperationException {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(documentoRepository.findById(1L)).thenReturn(Optional.of(documentoEntity));
        DocumentoEntity result = temaDocumentoService.agregarDocumento(1L, 1L);
        assertNotNull(result);
        assertTrue(temaEntity.getDocumentos().contains(result));
    }

    @Test
    void testAgregarDocumentoTemaNoExistente() {
        when(temaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalOperationException.class, () -> {
            temaDocumentoService.agregarDocumento(1L, 1L);
        });
    }

    @Test
    void testAgregarDocumentoDocumentoNoExistente() {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(documentoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalOperationException.class, () -> {
            temaDocumentoService.agregarDocumento(1L, 1L);
        });
    }

    @Test
    void testEliminarDocumento() throws IllegalOperationException {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(documentoRepository.findById(1L)).thenReturn(Optional.of(documentoEntity));
        temaEntity.getDocumentos().add(documentoEntity);
        temaDocumentoService.eliminarDocumento(1L, 1L);
        verify(documentoRepository, times(1)).delete(documentoEntity);
    }

    @Test
    void testEliminarDocumentoTemaNoExistente() {
        when(temaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalOperationException.class, () -> {
            temaDocumentoService.eliminarDocumento(1L, 1L);
        });
    }

    @Test
    void testEliminarDocumentoDocumentoNoExistente() {
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(documentoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalOperationException.class, () -> {
            temaDocumentoService.eliminarDocumento(1L, 1L);
        });
    }

    @Test
    void testEliminarDocumentoDocumentoNoPerteneceAlTema() {
        
        DocumentoEntity documentoNoRelacionado = new DocumentoEntity();
        documentoNoRelacionado.setId(2L);
        documentoNoRelacionado.setTema(new TemaEntity()); 
        when(temaRepository.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(documentoRepository.findById(2L)).thenReturn(Optional.of(documentoNoRelacionado));
        
        assertThrows(IllegalOperationException.class, () -> {
            temaDocumentoService.eliminarDocumento(1L, 2L);
        });
    }
}
