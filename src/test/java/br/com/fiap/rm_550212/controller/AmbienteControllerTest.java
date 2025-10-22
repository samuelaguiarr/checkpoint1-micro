package br.com.fiap.rm_550212.controller;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.dto.AmbienteResponse;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.service.AmbienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AmbienteController.class)
class AmbienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmbienteService ambienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAmbiente() throws Exception {
        AmbienteRequestCreate request = new AmbienteRequestCreate();
        request.setLocalizacao("Sala de Reunião");
        request.setTemperaturaAtual(new BigDecimal("25.5"));
        request.setEstaChovendo(false);

        Ambiente ambiente = new Ambiente();
        ambiente.setId(1L);
        ambiente.setLocalizacao("Sala de Reunião");
        ambiente.setTemperaturaAtual(new BigDecimal("25.5"));
        ambiente.setEstaChovendo(false);

        AmbienteResponse response = new AmbienteResponse();
        response.setId(1L);
        response.setLocalizacao("Sala de Reunião");
        response.setTemperaturaAtual(new BigDecimal("25.5"));
        response.setEstaChovendo(false);

        when(ambienteService.criarAmbiente(any(AmbienteRequestCreate.class))).thenReturn(ambiente);

        mockMvc.perform(post("/api/ambientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.localizacao").value("Sala de Reunião"))
                .andExpect(jsonPath("$.temperaturaAtual").value(25.5))
                .andExpect(jsonPath("$.estaChovendo").value(false));
    }

    @Test
    void testGetAllAmbientes() throws Exception {
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setId(1L);
        ambiente1.setLocalizacao("Sala 1");
        ambiente1.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente1.setEstaChovendo(false);

        Ambiente ambiente2 = new Ambiente();
        ambiente2.setId(2L);
        ambiente2.setLocalizacao("Sala 2");
        ambiente2.setTemperaturaAtual(new BigDecimal("22.0"));
        ambiente2.setEstaChovendo(true);

        List<Ambiente> ambientes = Arrays.asList(ambiente1, ambiente2);
        when(ambienteService.buscarTodos()).thenReturn(ambientes);

        mockMvc.perform(get("/api/ambientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].localizacao").value("Sala 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].localizacao").value("Sala 2"));
    }

    @Test
    void testGetAmbienteById() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setId(1L);
        ambiente.setLocalizacao("Sala de Reunião");
        ambiente.setTemperaturaAtual(new BigDecimal("25.5"));
        ambiente.setEstaChovendo(false);

        when(ambienteService.buscarPorId(1L)).thenReturn(Optional.of(ambiente));

        mockMvc.perform(get("/api/ambientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.localizacao").value("Sala de Reunião"))
                .andExpect(jsonPath("$.temperaturaAtual").value(25.5))
                .andExpect(jsonPath("$.estaChovendo").value(false));
    }

    @Test
    void testGetAmbienteByIdNotFound() throws Exception {
        when(ambienteService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ambientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAmbiente() throws Exception {
        AmbienteRequestUpdate request = new AmbienteRequestUpdate();
        request.setTemperaturaAtual(new BigDecimal("30.0"));
        request.setEstaChovendo(true);

        Ambiente ambienteAtualizado = new Ambiente();
        ambienteAtualizado.setId(1L);
        ambienteAtualizado.setLocalizacao("Sala Atualizada");
        ambienteAtualizado.setTemperaturaAtual(new BigDecimal("30.0"));
        ambienteAtualizado.setEstaChovendo(true);

        when(ambienteService.atualizarAmbiente(anyLong(), any(AmbienteRequestUpdate.class))).thenReturn(Optional.of(ambienteAtualizado));

        mockMvc.perform(put("/api/ambientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.temperaturaAtual").value(30.0))
                .andExpect(jsonPath("$.estaChovendo").value(true));
    }

    @Test
    void testUpdateAmbienteNotFound() throws Exception {
        AmbienteRequestUpdate request = new AmbienteRequestUpdate();
        request.setTemperaturaAtual(new BigDecimal("30.0"));

        when(ambienteService.atualizarAmbiente(anyLong(), any(AmbienteRequestUpdate.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/ambientes/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAmbiente() throws Exception {
        when(ambienteService.deletarAmbiente(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/ambientes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAmbienteNotFound() throws Exception {
        when(ambienteService.deletarAmbiente(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/ambientes/999"))
                .andExpect(status().isNotFound());
    }
}