package br.com.fiap.rm_550212.integration;

import br.com.fiap.rm_550212.dto.AmbienteRequestCreate;
import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestMvc
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
        request.setNome("Sala de Teste");
        request.setCapacidade(20);
        request.setTipo("Auditório");

        // Criar ambiente
        mockMvc.perform(post("/api/ambientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Sala de Teste"))
                .andExpect(jsonPath("$.capacidade").value(20))
                .andExpect(jsonPath("$.tipo").value("Auditório"));

        // Verificar se foi salvo no banco
        assertEquals(1, ambienteRepository.count());
        Ambiente ambiente = ambienteRepository.findAll().get(0);
        assertEquals("Sala de Teste", ambiente.getNome());
        assertEquals(20, ambiente.getCapacidade());
        assertEquals("Auditório", ambiente.getTipo());
    }

    @Test
    void testGetAllAmbientes() throws Exception {
        // Criar ambientes de teste
        Ambiente ambiente1 = new Ambiente();
        ambiente1.setNome("Sala 1");
        ambiente1.setCapacidade(10);
        ambiente1.setTipo("Reunião");
        ambienteRepository.save(ambiente1);

        Ambiente ambiente2 = new Ambiente();
        ambiente2.setNome("Sala 2");
        ambiente2.setCapacidade(20);
        ambiente2.setTipo("Auditório");
        ambienteRepository.save(ambiente2);

        // Buscar todos os ambientes
        mockMvc.perform(get("/api/ambientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Sala 1"))
                .andExpect(jsonPath("$[1].nome").value("Sala 2"));
    }

    @Test
    void testGetAmbienteById() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setNome("Sala de Teste");
        ambiente.setCapacidade(15);
        ambiente.setTipo("Reunião");
        ambiente = ambienteRepository.save(ambiente);

        mockMvc.perform(get("/api/ambientes/" + ambiente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ambiente.getId()))
                .andExpect(jsonPath("$.nome").value("Sala de Teste"))
                .andExpect(jsonPath("$.capacidade").value(15))
                .andExpect(jsonPath("$.tipo").value("Reunião"));
    }

    @Test
    void testUpdateAmbiente() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setNome("Sala Original");
        ambiente.setCapacidade(10);
        ambiente.setTipo("Reunião");
        ambiente = ambienteRepository.save(ambiente);

        String updateJson = """
                {
                    "nome": "Sala Atualizada",
                    "capacidade": 25,
                    "tipo": "Auditório"
                }
                """;

        mockMvc.perform(put("/api/ambientes/" + ambiente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sala Atualizada"))
                .andExpect(jsonPath("$.capacidade").value(25))
                .andExpect(jsonPath("$.tipo").value("Auditório"));

        // Verificar se foi atualizado no banco
        Ambiente ambienteAtualizado = ambienteRepository.findById(ambiente.getId()).orElseThrow();
        assertEquals("Sala Atualizada", ambienteAtualizado.getNome());
        assertEquals(25, ambienteAtualizado.getCapacidade());
        assertEquals("Auditório", ambienteAtualizado.getTipo());
    }

    @Test
    void testDeleteAmbiente() throws Exception {
        Ambiente ambiente = new Ambiente();
        ambiente.setNome("Sala para Deletar");
        ambiente.setCapacidade(10);
        ambiente.setTipo("Reunião");
        ambiente = ambienteRepository.save(ambiente);

        assertEquals(1, ambienteRepository.count());

        mockMvc.perform(delete("/api/ambientes/" + ambiente.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, ambienteRepository.count());
    }
}
