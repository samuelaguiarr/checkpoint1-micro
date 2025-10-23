package br.com.fiap.rm_550212.integration;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.dto.AmbienteRequestUpdate;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestEntityManager
class AmbienteIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ambienteRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveAmbiente() throws Exception {
        AmbienteRequestCreate request = new AmbienteRequestCreate();
        request.setLocalizacao("Sala de Teste");
        request.setTemperaturaAtual(new BigDecimal("25.0"));
        request.setEstaChovendo(false);

        // Create ambiente
        mockMvc.perform(post("/api/ambientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.localizacao").value("Sala de Teste"))
                .andExpect(jsonPath("$.temperaturaAtual").value(25.0))
                .andExpect(jsonPath("$.estaChovendo").value(false));

        // Verify ambiente was saved
        assert ambienteRepository.count() == 1;
    }

    @Test
    void testUpdateAmbiente() throws Exception {
        // Create ambiente first
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala Original");
        ambiente.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        AmbienteRequestUpdate updateRequest = new AmbienteRequestUpdate();
        updateRequest.setTemperaturaAtual(new BigDecimal("30.0"));
        updateRequest.setEstaChovendo(true);

        // Update ambiente
        mockMvc.perform(put("/api/ambientes/" + ambiente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperaturaAtual").value(30.0))
                .andExpect(jsonPath("$.estaChovendo").value(true));

        // Verify update
        Ambiente updatedAmbiente = ambienteRepository.findById(ambiente.getId()).orElse(null);
        assert updatedAmbiente != null;
        assert updatedAmbiente.getTemperaturaAtual().equals(new BigDecimal("30.0"));
        assert updatedAmbiente.isEstaChovendo() == true;
    }

    @Test
    void testDeleteAmbiente() throws Exception {
        // Create ambiente first
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala para Deletar");
        ambiente.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        // Delete ambiente
        mockMvc.perform(delete("/api/ambientes/" + ambiente.getId()))
                .andExpect(status().isOk());

        // Verify deletion
        assert !ambienteRepository.existsById(ambiente.getId());
    }

    @Test
    void testGetAllAmbientes() throws Exception {
        // Create multiple ambientes
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setLocalizacao("Sala 1");
        ambiente1.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente1.setEstaChovendo(false);
        ambienteRepository.save(ambiente1);

        Ambiente ambiente2 = new Ambiente();
        ambiente2.setLocalizacao("Sala 2");
        ambiente2.setTemperaturaAtual(new BigDecimal("25.0"));
        ambiente2.setEstaChovendo(true);
        ambienteRepository.save(ambiente2);

        // Get all ambientes
        mockMvc.perform(get("/api/ambientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
