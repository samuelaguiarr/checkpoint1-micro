package br.com.fiap.rm_550212.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fiap.rm_550212.model.Ambiente;

public class AmbienteRequestCreate {
    private String localizacao;
    private LocalDate dataRegistro;  
    private BigDecimal temperaturaAtual;
    private boolean estaChovendo;

    public Ambiente toModel(){
        Ambiente ambiente = new Ambiente();
        ambiente.setLocalizacao(this.getLocalizacao());
        ambiente.setDataRegistro(this.getDataRegistro());
        ambiente.setTemperaturaAtual(this.getTemperaturaAtual());
        ambiente.setEstaChovendo(this.isEstaChovendo());
        return ambiente;
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
