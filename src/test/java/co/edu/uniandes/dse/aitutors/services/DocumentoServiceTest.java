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

import co.edu.uniandes.dse.aitutors.entities.DocumentoEntity;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(DocumentoService.class)
class DocumentoServiceTest {
    @TestConfiguration
    static class DocumentoServiceTestContextConfiguration {
        @Bean
        public DocumentoService documentoService() {
            return new DocumentoService();
        }
    }

    @Autowired
    private DocumentoService documentoService;

    @MockBean
    private DocumentoRepository documentoRepository;

    private DocumentoEntity documentoEntity;

    @BeforeEach
    void setUp() {
        documentoEntity = new DocumentoEntity();
        documentoEntity.setContenido("Contenido del documento");
    }

    @Test
    void testMostrarContenido() throws IllegalOperationException {
        // Configurar el comportamiento del mock
        when(documentoRepository.findById(1L)).thenReturn(java.util.Optional.of(documentoEntity));

        // Llamar al método y verificar el resultado
        String contenido = documentoService.mostrarContenido(1L);
        assertEquals("Contenido del documento", contenido);
    }

    @Test
    void testMostrarContenidoException() throws IllegalOperationException {
        // Configurar el comportamiento del mock para lanzar una excepción
        when(documentoRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        String contenido = documentoService.mostrarContenido(1L);
        // Llamar al método y verificar que se lance la excepción esperada
        assertEquals("", contenido);
    }
}
