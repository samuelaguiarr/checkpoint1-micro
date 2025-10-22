package br.com.fiap.rm_550212.integration;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class AmbienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ambienteRepository.deleteAll();
    }

    @Test
    void testCreateAndGetAmbiente() throws Exception {
        AmbienteRequestCreate request = new AmbienteRequestCreate();
        request.setLocalizacao("Sala de Teste");
        request.setTemperaturaAtual(new BigDecimal("20.0"));
        request.setEstaChovendo(false);

        // Criar ambiente
        mockMvc.perform(post("/api/ambientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.localizacao").value("Sala de Teste"))
                .andExpect(jsonPath("$.temperaturaAtual").value(20.0))
                .andExpect(jsonPath("$.estaChovendo").value(false));

        // Verificar se foi salvo no banco
        assertEquals(1, ambienteRepository.count());
        Ambiente ambiente = ambienteRepository.findAll().get(0);
        assertEquals("Sala de Teste", ambiente.getLocalizacao());
        assertEquals(new BigDecimal("20.0"), ambiente.getTemperaturaAtual());
        assertEquals(false, ambiente.isEstaChovendo());
    }

    @Test
    void testGetAllAmbientes() throws Exception {
        // Criar ambientes de teste
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setLocalizacao("Sala 1");
        ambiente1.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente1.setEstaChovendo(false);
        ambienteRepository.save(ambiente1);

        Ambiente ambiente2 = new Ambiente();
        ambiente2.setLocalizacao("Sala 2");
        ambiente2.setTemperaturaAtual(new BigDecimal("22.0"));
        ambiente2.setEstaChovendo(true);
        ambienteRepository.save(ambiente2);

        // Buscar todos os ambientes
        mockMvc.perform(get("/api/ambientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].localizacao").value("Sala 1"))
                .andExpect(jsonPath("$[1].localizacao").value("Sala 2"));
    }

    @Test
    void testGetAmbienteById() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala de Teste");
        ambiente.setTemperaturaAtual(new BigDecimal("15.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        mockMvc.perform(get("/api/ambientes/" + ambiente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ambiente.getId()))
                .andExpect(jsonPath("$.localizacao").value("Sala de Teste"))
                .andExpect(jsonPath("$.temperaturaAtual").value(15.0))
                .andExpect(jsonPath("$.estaChovendo").value(false));
    }

    @Test
    void testUpdateAmbiente() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala Original");
        ambiente.setTemperaturaAtual(new BigDecimal("10.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        String updateJson = """
                {
                    "temperaturaAtual": 25.0,
                    "estaChovendo": true
                }
                """;

        mockMvc.perform(put("/api/ambientes/" + ambiente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperaturaAtual").value(25.0))
                .andExpect(jsonPath("$.estaChovendo").value(true));

        // Verificar se foi atualizado no banco
        Ambiente ambienteAtualizado = ambienteRepository.findById(ambiente.getId()).orElseThrow();
        assertEquals(new BigDecimal("25.0"), ambienteAtualizado.getTemperaturaAtual());
        assertEquals(true, ambienteAtualizado.isEstaChovendo());
    }

    @Test
    void testDeleteAmbiente() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala para Deletar");
        ambiente.setTemperaturaAtual(new BigDecimal("10.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        assertEquals(1, ambienteRepository.count());

        mockMvc.perform(delete("/api/ambientes/" + ambiente.getId()))
                .andExpect(status().isOk());

        assertEquals(0, ambienteRepository.count());
    }
}