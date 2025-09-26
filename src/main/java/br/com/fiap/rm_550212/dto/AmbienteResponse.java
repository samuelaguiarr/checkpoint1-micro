package br.com.fiap.rm_550212.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fiap.rm_550212.model.Ambiente;

public class AmbienteResponse {
    private Long id;
    private String localizacao;
    private LocalDate dataRegistro;
    private BigDecimal temperaturaAtual;
    private boolean estaChovendo;

    public AmbienteResponse toDto(Ambiente ambiente){
        this.setId(ambiente.getId());
        this.setLocalizacao(ambiente.getLocalizacao());
        this.setTemperaturaAtual(ambiente.getTemperaturaAtual());
        this.setDataRegistro(ambiente.getDataRegistro());
        this.setEstaChovendo(ambiente.isEstaChovendo());
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public BigDecimal getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(BigDecimal temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public boolean isEstaChovendo() {
        return estaChovendo;
    }

    public void setEstaChovendo(boolean estaChovendo) {
        this.estaChovendo = estaChovendo;
    }
}
