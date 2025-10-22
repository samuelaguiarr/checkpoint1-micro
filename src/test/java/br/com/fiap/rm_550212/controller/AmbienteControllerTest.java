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
        request.setNome("Sala de Reunião");
        request.setCapacidade(10);
        request.setTipo("Reunião");

        Ambiente ambiente = new Ambiente();
        ambiente.setId(1L);
        ambiente.setNome("Sala de Reunião");
        ambiente.setCapacidade(10);
        ambiente.setTipo("Reunião");

        AmbienteResponse response = new AmbienteResponse();
        response.setId(1L);
        response.setNome("Sala de Reunião");
        response.setCapacidade(10);
        response.setTipo("Reunião");

        when(ambienteService.create(any(AmbienteRequestCreate.class))).thenReturn(response);

        mockMvc.perform(post("/api/ambientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala de Reunião"))
                .andExpect(jsonPath("$.capacidade").value(10))
                .andExpect(jsonPath("$.tipo").value("Reunião"));
    }

    @Test
    void testGetAllAmbientes() throws Exception {
        AmbienteResponse ambiente1 = new AmbienteResponse();
        ambiente1.setId(1L);
        ambiente1.setNome("Sala 1");
        ambiente1.setCapacidade(10);
        ambiente1.setTipo("Reunião");

        AmbienteResponse ambiente2 = new AmbienteResponse();
        ambiente2.setId(2L);
        ambiente2.setNome("Sala 2");
        ambiente2.setCapacidade(20);
        ambiente2.setTipo("Auditório");

        List<AmbienteResponse> ambientes = Arrays.asList(ambiente1, ambiente2);

        when(ambienteService.getAll()).thenReturn(ambientes);

        mockMvc.perform(get("/api/ambientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Sala 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Sala 2"));
    }

    @Test
    void testGetAmbienteById() throws Exception {
        AmbienteResponse ambiente = new AmbienteResponse();
        ambiente.setId(1L);
        ambiente.setNome("Sala de Reunião");
        ambiente.setCapacidade(10);
        ambiente.setTipo("Reunião");

        when(ambienteService.getById(1L)).thenReturn(Optional.of(ambiente));

        mockMvc.perform(get("/api/ambientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala de Reunião"))
                .andExpect(jsonPath("$.capacidade").value(10))
                .andExpect(jsonPath("$.tipo").value("Reunião"));
    }

    @Test
    void testGetAmbienteByIdNotFound() throws Exception {
        when(ambienteService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ambientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAmbiente() throws Exception {
        AmbienteRequestUpdate request = new AmbienteRequestUpdate();
        request.setNome("Sala Atualizada");
        request.setCapacidade(15);
        request.setTipo("Auditório");

        AmbienteResponse response = new AmbienteResponse();
        response.setId(1L);
        response.setNome("Sala Atualizada");
        response.setCapacidade(15);
        response.setTipo("Auditório");

        when(ambienteService.update(anyLong(), any(AmbienteRequestUpdate.class))).thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/ambientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala Atualizada"))
                .andExpect(jsonPath("$.capacidade").value(15))
                .andExpect(jsonPath("$.tipo").value("Auditório"));
    }

    @Test
    void testUpdateAmbienteNotFound() throws Exception {
        AmbienteRequestUpdate request = new AmbienteRequestUpdate();
        request.setNome("Sala Atualizada");

        when(ambienteService.update(anyLong(), any(AmbienteRequestUpdate.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/ambientes/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAmbiente() throws Exception {
        when(ambienteService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/ambientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAmbienteNotFound() throws Exception {
        when(ambienteService.delete(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/ambientes/999"))
                .andExpect(status().isNotFound());
    }
}
