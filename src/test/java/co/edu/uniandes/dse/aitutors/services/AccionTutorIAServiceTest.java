package co.edu.uniandes.dse.aitutors.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

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
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.aitutors.entities.*;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.AccionRepository;
import co.edu.uniandes.dse.aitutors.repositories.DocumentoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import co.edu.uniandes.dse.aitutors.repositories.TutorIARepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TemaDocumentoService.class)
public class AccionTutorIAServiceTest {
    
    @Autowired
    private AccionTutorIAService servicioAccionTutor;

    @MockBean
    private TutorIARepository repositorioTutor;

    @MockBean
    private AccionRepository repositorioAccion;

    private AccionEntity accionEntity;

    private TutorIAEntity tutorIAEntity;

    @BeforeEach
    void setUp() {
        accionEntity = new AccionEntity();
        accionEntity.setId(1L);
        
        tutorIAEntity = new TutorIAEntity();
        tutorIAEntity.setId(1L);
    }

    @Test
    void testAgregarTutorIA() throws IllegalOperationException {
        when(repositorioTutor.findById(1L)).thenReturn(Optional.of(tutorIAEntity));
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        try{
        TutorIAEntity result = servicioAccionTutor.addTutorIA(1L, 1L);
        assertNotNull(result);
        assertEquals(accionEntity.getTutorIA(), result);
        }
        catch (EntityNotFoundException e) {}

    }

    @Test
    void testAgregarTutorIAAccionNoExistente() {

        when(repositorioTutor.findById(1L)).thenReturn(Optional.of(tutorIAEntity));
        when(repositorioAccion.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.addTutorIA(1L, 1L));
        assertEquals(ErrorMessage.TUTORIA_NOT_FOUND, error.getMessage());

    }

    @Test
    void testAgregarTutorIAYaTieneTutor() {

        TutorIAEntity tutor2 = new TutorIAEntity();
        tutor2.setId(2L);
        when(repositorioTutor.findById(1L)).thenReturn(Optional.of(tutorIAEntity));
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        accionEntity.setTutorIA(tutor2);

        IllegalOperationException error = assertThrows(IllegalOperationException.class, () ->servicioAccionTutor.addTutorIA(1L, 1L));
        assertEquals("El accion ya tiene un tutorIA asociado", error.getMessage());
    }

    @Test
    void testAgregarTutorIAATUTORIANoExistente() {

        when(repositorioTutor.findById(1L)).thenReturn(Optional.empty());
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.addTutorIA(1L, 1L));
        assertEquals(ErrorMessage.ACCION_NOT_FOUND, error.getMessage());

    }

    @Test
    void testgetTutorIA() {
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        try{
        TutorIAEntity result = servicioAccionTutor.getTutorIA(1L);
        assertNotNull(result);
        assertEquals(result, accionEntity);
        }
        catch(EntityNotFoundException e ) {} 
    }


    @Test
    void testgetTutorIA_AccionNoFound() {

        when(repositorioAccion.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.getTutorIA(1L));
        assertEquals(ErrorMessage.ACCION_NOT_FOUND, error.getMessage());

    }

    @Test
    void testgetTutorIA_NoHayTutor() {

        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        accionEntity.setTutorIA(null);
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.getTutorIA(1L));
        assertEquals("El accion no tiene tutorIA", error.getMessage());

    }

    @Test
    void testreplaceTutorIA() throws IllegalOperationException {
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        when(repositorioTutor.findById(1L)).thenReturn(Optional.of(tutorIAEntity));
        try{
            TutorIAEntity result = servicioAccionTutor.replaceTutorIA(1L, 1L);
            assertNotNull(result);
            assertEquals(result, tutorIAEntity);
        }
        catch (EntityNotFoundException e) {}
    }

    @Test
    void testreplaceTutorIA_TutoriaNotFound() {

        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        when(repositorioTutor.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.replaceTutorIA(1L, 1L));
        assertEquals(ErrorMessage.TUTORIA_NOT_FOUND, error.getMessage());

    }

    @Test
    void testreplaceTutorIA_AccionNotFound() {

        when(repositorioAccion.findById(1L)).thenReturn(Optional.empty());
        when(repositorioTutor.findById(1L)).thenReturn(Optional.of(tutorIAEntity));
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.replaceTutorIA(1L, 1L));
        assertEquals(ErrorMessage.ACCION_NOT_FOUND, error.getMessage());

    }
    @Test
    void testremoveTutorIA() {
        when(repositorioAccion.findById(1L)).thenReturn(Optional.of(accionEntity));
        try{
        servicioAccionTutor.removeTutorIA(1L);
        verify(repositorioAccion, times(1)).findById(1L);
        assertNull(accionEntity.getTutorIA());
        }
        catch(EntityNotFoundException e ) {}
    }

    @Test
    void testremoveTutorIA_AccionNotFound() {

        when(repositorioAccion.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioAccionTutor.removeTutorIA(1L));
        assertEquals(ErrorMessage.ACCION_NOT_FOUND, error.getMessage());


    }

}
