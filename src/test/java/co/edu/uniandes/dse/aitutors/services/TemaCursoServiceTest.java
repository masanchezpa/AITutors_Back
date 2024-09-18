package co.edu.uniandes.dse.aitutors.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Error;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import co.edu.uniandes.dse.aitutors.entities.AccionEntity;
import co.edu.uniandes.dse.aitutors.entities.CursoEntity;
import co.edu.uniandes.dse.aitutors.entities.TemaEntity;
import co.edu.uniandes.dse.aitutors.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.aitutors.exceptions.ErrorMessage;
import co.edu.uniandes.dse.aitutors.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.aitutors.repositories.CursoRepository;
import co.edu.uniandes.dse.aitutors.repositories.TemaRepository;
import lombok.ToString;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TemaCursoServiceTest {
    

    @Autowired
	private TemaCursoService servicioTemaCurso;

	@MockBean
    private TemaRepository repositorioTema;

    @MockBean
    private CursoRepository repositorioCurso;

    private TemaEntity temaEntity;
    private CursoEntity cursoEntity;

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
        temaEntity = new TemaEntity();
        temaEntity.setId(1L);

        cursoEntity = new CursoEntity();
        cursoEntity.setId(1L);
    }


    @Test
    void testaddCurso() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(repositorioCurso.findById(1L)).thenReturn(Optional.of(cursoEntity));

        CursoEntity result = servicioTemaCurso.addCurso(1L, 1L);
        assertNotNull(result);
        assertEquals(result, cursoEntity);
    }

    @Test
    void testaddCurso_TemaNotFound() {

        when(repositorioTema.findById(1L)).thenReturn(Optional.empty());
        when(repositorioCurso.findById(1L)).thenReturn(Optional.of(cursoEntity));

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.addCurso(1L, 1L));
        assertEquals(ErrorMessage.TEMA_NOT_FOUND, error.getMessage());

    }

    @Test
    void testaddCurso_CursoNotFound() {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(repositorioCurso.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.addCurso(1L, 1L));
        assertEquals(ErrorMessage.CURSO_NOT_FOUND, error.getMessage());

    }

    @Test
    void testAddCurso_CursoAsociado() {

        CursoEntity curso2 = new CursoEntity();
        curso2.setId(2L);

        temaEntity.setCurso(curso2);

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(repositorioCurso.findById(1L)).thenReturn(Optional.of(cursoEntity));

        IllegalOperationException error = assertThrows(IllegalOperationException.class, () -> servicioTemaCurso.addCurso(1L, 1L));
        assertEquals("El tema ya tiene un curso asociado", error.getMessage());

    }


    @Test
    void testGetCurso() throws EntityNotFoundException, IllegalOperationException {
        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        temaEntity.setCurso(cursoEntity);
        CursoEntity result = servicioTemaCurso.getCurso(1L);
        assertNotNull(result);
        assertEquals(result, cursoEntity);
    }

    @Test
    void testGetCurso_NoEncontroTema()  {
        when(repositorioTema.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.getCurso(1L));
        assertEquals(ErrorMessage.TEMA_NOT_FOUND, error);
    }

    @Test
    void testGetCurso_TemaSinCurso() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        temaEntity.setCurso(null);

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.getCurso(1L));
        assertEquals("El tema no tiene curso", error.getMessage());
    }


    @Test
    void testReplaceCurso() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(repositorioCurso.findById(1L)).thenReturn(Optional.of(cursoEntity));

        CursoEntity result = servicioTemaCurso.replaceCurso(1L, 1L);
        assertNotNull(result);
        assertEquals(result, cursoEntity);

    }
    @Test
    void testReplaceCurso_CursoNotFound() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.empty());
        when(repositorioCurso.findById(1L)).thenReturn(Optional.of(cursoEntity));

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.addCurso(1L, 1L));
        assertEquals(ErrorMessage.CURSO_NOT_FOUND, error.getMessage());

    }
    @Test
    void testReplaceCurso_TemaNotFound() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        when(repositorioCurso.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.addCurso(1L, 1L));
        assertEquals(ErrorMessage.TEMA_NOT_FOUND, error.getMessage());

    }

    @Test
    void removeCurso() throws EntityNotFoundException, IllegalOperationException {

        when(repositorioTema.findById(1L)).thenReturn(Optional.of(temaEntity));
        temaEntity.setCurso(cursoEntity);
        servicioTemaCurso.removeCurso(1L);
        assertNull(temaEntity.getCurso());
    }

    @Test
    void removeCurso_TemaNotFound() throws EntityNotFoundException, IllegalOperationException {
        when(repositorioTema.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException error = assertThrows(EntityNotFoundException.class, () -> servicioTemaCurso.removeCurso(1L));
        assertEquals(ErrorMessage.TEMA_NOT_FOUND, error.getMessage());
    }


	
}
