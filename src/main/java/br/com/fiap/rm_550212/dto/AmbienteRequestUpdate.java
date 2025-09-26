package br.com.fiap.rm_550212.dto;

import java.math.BigDecimal;

import br.com.fiap.rm_550212.model.Ambiente;

public class AmbienteRequestUpdate {
    private BigDecimal temperaturaAtual;
    private boolean estaChovendo;

    public Ambiente toModel(Ambiente ambiente){
        ambiente.setTemperaturaAtual(this.getTemperaturaAtual());
        ambiente.setEstaChovendo(this.isEstaChovendo());
        return ambiente;
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
