package br.com.fiap.rm_550212.integration;

import br.com.fiap.rm_550212.model.Ambiente;
import br.com.fiap.rm_550212.repository.AmbienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AmbienteIntegrationTest {

    @Autowired
    private AmbienteRepository ambienteRepository;

    @BeforeEach
    void setUp() {
        ambienteRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveAmbiente() {
        // Create ambiente
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala de Teste");
        ambiente.setTemperaturaAtual(new BigDecimal("25.0"));
        ambiente.setEstaChovendo(false);
        
        Ambiente savedAmbiente = ambienteRepository.save(ambiente);
        
        // Verify ambiente was saved
        assertNotNull(savedAmbiente.getId());
        assertEquals("Sala de Teste", savedAmbiente.getLocalizacao());
        assertEquals(new BigDecimal("25.0"), savedAmbiente.getTemperaturaAtual());
        assertEquals(false, savedAmbiente.isEstaChovendo());
        
        // Verify count
        assertEquals(1, ambienteRepository.count());
    }

    @Test
    void testUpdateAmbiente() {
        // Create ambiente first
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala Original");
        ambiente.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        // Update ambiente
        ambiente.setTemperaturaAtual(new BigDecimal("30.0"));
        ambiente.setEstaChovendo(true);
        Ambiente updatedAmbiente = ambienteRepository.save(ambiente);

        // Verify update
        assertEquals(new BigDecimal("30.0"), updatedAmbiente.getTemperaturaAtual());
        assertEquals(true, updatedAmbiente.isEstaChovendo());
    }

    @Test
    void testDeleteAmbiente() {
        // Create ambiente first
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao("Sala para Deletar");
        ambiente.setTemperaturaAtual(new BigDecimal("20.0"));
        ambiente.setEstaChovendo(false);
        ambiente = ambienteRepository.save(ambiente);

        // Delete ambiente
        ambienteRepository.deleteById(ambiente.getId());

        // Verify deletion
        assertFalse(ambienteRepository.existsById(ambiente.getId()));
    }

    @Test
    void testGetAllAmbientes() {
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
        assertEquals(2, ambienteRepository.count());
    }
}
