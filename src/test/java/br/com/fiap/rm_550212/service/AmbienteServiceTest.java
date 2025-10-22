package br.com.fiap.rm_550212.service;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        ambiente.setLocalizacao("Sala de Reunião");
        ambiente.setTemperaturaAtual(new BigDecimal("25.5"));
        ambiente.setEstaChovendo(false);

        requestCreate = new AmbienteRequestCreate();
        requestCreate.setLocalizacao("Sala de Reunião");
        requestCreate.setTemperaturaAtual(new BigDecimal("25.5"));
        requestCreate.setEstaChovendo(false);

        requestUpdate = new AmbienteRequestUpdate();
        requestUpdate.setTemperaturaAtual(new BigDecimal("30.0"));
        requestUpdate.setEstaChovendo(true);
    }

    @Test
    void testCriarAmbiente() {
        when(ambienteRepository.save(any(Ambiente.class))).thenReturn(ambiente);

        Ambiente result = ambienteService.criarAmbiente(requestCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sala de Reunião", result.getLocalizacao());
        assertEquals(new BigDecimal("25.5"), result.getTemperaturaAtual());
        assertEquals(false, result.isEstaChovendo());

        verify(ambienteRepository, times(1)).save(any(Ambiente.class));
    }

    @Test
    void testBuscarTodos() {
        Ambiente ambiente2 = new Ambiente();
        ambiente2.setId(2L);
        ambiente2.setLocalizacao("Auditório");
        ambiente2.setTemperaturaAtual(new BigDecimal("22.0"));
        ambiente2.setEstaChovendo(true);

        List<Ambiente> ambientes = Arrays.asList(ambiente, ambiente2);
        when(ambienteRepository.findAll()).thenReturn(ambientes);

        List<Ambiente> responses = ambienteService.buscarTodos();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(1L, responses.get(0).getId());
        assertEquals("Sala de Reunião", responses.get(0).getLocalizacao());
        assertEquals(2L, responses.get(1).getId());
        assertEquals("Auditório", responses.get(1).getLocalizacao());

        verify(ambienteRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(ambienteRepository.findById(1L)).thenReturn(Optional.of(ambiente));

        Optional<Ambiente> response = ambienteService.buscarPorId(1L);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Sala de Reunião", response.get().getLocalizacao());
        assertEquals(new BigDecimal("25.5"), response.get().getTemperaturaAtual());
        assertEquals(false, response.get().isEstaChovendo());

        verify(ambienteRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorIdNotFound() {
        when(ambienteRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Ambiente> response = ambienteService.buscarPorId(999L);

        assertFalse(response.isPresent());
        verify(ambienteRepository, times(1)).findById(999L);
    }

    @Test
    void testAtualizarAmbiente() {
        Ambiente ambienteAtualizado = new Ambiente();
        ambienteAtualizado.setId(1L);
        ambienteAtualizado.setLocalizacao("Sala Atualizada");
        ambienteAtualizado.setTemperaturaAtual(new BigDecimal("30.0"));
        ambienteAtualizado.setEstaChovendo(true);

        when(ambienteRepository.findById(1L)).thenReturn(Optional.of(ambiente));
        when(ambienteRepository.save(any(Ambiente.class))).thenReturn(ambienteAtualizado);

        Optional<Ambiente> response = ambienteService.atualizarAmbiente(1L, requestUpdate);

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("Sala Atualizada", response.get().getLocalizacao());
        assertEquals(new BigDecimal("30.0"), response.get().getTemperaturaAtual());
        assertEquals(true, response.get().isEstaChovendo());

        verify(ambienteRepository, times(1)).findById(1L);
        verify(ambienteRepository, times(1)).save(any(Ambiente.class));
    }

    @Test
    void testAtualizarAmbienteNotFound() {
        when(ambienteRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Ambiente> response = ambienteService.atualizarAmbiente(999L, requestUpdate);

        assertFalse(response.isPresent());
        verify(ambienteRepository, times(1)).findById(999L);
        verify(ambienteRepository, never()).save(any(Ambiente.class));
    }

    @Test
    void testDeletarAmbiente() {
        when(ambienteRepository.existsById(1L)).thenReturn(true);

        boolean result = ambienteService.deletarAmbiente(1L);

        assertTrue(result);
        verify(ambienteRepository, times(1)).existsById(1L);
        verify(ambienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletarAmbienteNotFound() {
        when(ambienteRepository.existsById(999L)).thenReturn(false);

        boolean result = ambienteService.deletarAmbiente(999L);

        assertFalse(result);
        verify(ambienteRepository, times(1)).existsById(999L);
        verify(ambienteRepository, never()).deleteById(anyLong());
    }
}