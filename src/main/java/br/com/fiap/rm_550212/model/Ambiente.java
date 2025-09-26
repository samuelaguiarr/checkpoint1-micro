package br.com.fiap.rm_550212.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ambiente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String localizacao;
    private LocalDate dataRegistro;
    private BigDecimal temperaturaAtual;
    private boolean estaChovendo;

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
