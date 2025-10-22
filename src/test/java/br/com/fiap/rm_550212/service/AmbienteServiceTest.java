package br.com.fiap.rm_550212.service;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.dto.AmbienteResponse;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmbienteServiceTest {

    @Mock
    private AmbienteRepository ambienteRepository;

    @InjectMocks
    private AmbienteService ambienteService;

    private Ambiente ambiente;
    private AmbienteRequestCreate requestCreate;
    private AmbienteRequestUpdate requestUpdate;

    @BeforeEach
    void setUp() {
        ambiente = new Ambiente();
        ambiente.setId(1L);
        ambiente.setNome("Sala de Reunião");
        ambiente.setCapacidade(10);
        ambiente.setTipo("Reunião");

        requestCreate = new AmbienteRequestCreate();
        requestCreate.setNome("Sala de Reunião");
        requestCreate.setCapacidade(10);
        requestCreate.setTipo("Reunião");

        requestUpdate = new AmbienteRequestUpdate();
        requestUpdate.setNome("Sala Atualizada");
        requestUpdate.setCapacidade(15);
        requestUpdate.setTipo("Auditório");
    }

    @Test
    void testCreate() {
        when(ambienteRepository.save(any(Ambiente.class))).thenReturn(ambiente);

        AmbienteResponse response = ambienteService.create(requestCreate);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Sala de Reunião", response.getNome());
        assertEquals(10, response.getCapacidade());
        assertEquals("Reunião", response.getTipo());

        verify(ambienteRepository, times(1)).save(any(Ambiente.class));
    }

    @Test
    void testGetAll() {
        Ambiente ambiente2 = new Ambiente();
        ambiente2.setId(2L);
        ambiente2.setNome("Auditório");
        ambiente2.setCapacidade(50);
        ambiente2.setTipo("Auditório");

        List<Ambiente> ambientes = Arrays.asList(ambiente, ambiente2);
        when(ambienteRepository.findAll()).thenReturn(ambientes);

        List<AmbienteResponse> responses = ambienteService.getAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("Sala de Reunião", responses.get(0).getNome());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("Auditório", responses.get(1).getNome());

        verify(ambienteRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        when(ambienteRepository.findById(1L)).thenReturn(Optional.of(ambiente));

        Optional<AmbienteResponse> response = ambienteService.getById(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Sala de Reunião", response.get().getNome());
        assertEquals(10, response.get().getCapacidade());
        assertEquals("Reunião", response.get().getTipo());

        verify(ambienteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(ambienteRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<AmbienteResponse> response = ambienteService.getById(999L);

        assertFalse(response.isPresent());
        verify(ambienteRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdate() {
        Ambiente ambienteAtualizado = new Ambiente();
        ambienteAtualizado.setId(1L);
        ambienteAtualizado.setNome("Sala Atualizada");
        ambienteAtualizado.setCapacidade(15);
        ambienteAtualizado.setTipo("Auditório");

        when(ambienteRepository.findById(1L)).thenReturn(Optional.of(ambiente));
        when(ambienteRepository.save(any(Ambiente.class))).thenReturn(ambienteAtualizado);

        Optional<AmbienteResponse> response = ambienteService.update(1L, requestUpdate);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Sala Atualizada", response.get().getNome());
        assertEquals(15, response.get().getCapacidade());
        assertEquals("Auditório", response.get().getTipo());

        verify(ambienteRepository, times(1)).findById(1L);
        verify(ambienteRepository, times(1)).save(any(Ambiente.class));
    }

    @Test
    void testUpdateNotFound() {
        when(ambienteRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<AmbienteResponse> response = ambienteService.update(999L, requestUpdate);

        assertFalse(response.isPresent());
        verify(ambienteRepository, times(1)).findById(999L);
        verify(ambienteRepository, never()).save(any(Ambiente.class));
    }

    @Test
    void testDelete() {
        when(ambienteRepository.existsById(1L)).thenReturn(true);

        boolean result = ambienteService.delete(1L);

        assertTrue(result);
        verify(ambienteRepository, times(1)).existsById(1L);
        verify(ambienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        when(ambienteRepository.existsById(999L)).thenReturn(false);

        boolean result = ambienteService.delete(999L);

        assertFalse(result);
        verify(ambienteRepository, times(1)).existsById(999L);
        verify(ambienteRepository, never()).deleteById(anyLong());
    }
}
