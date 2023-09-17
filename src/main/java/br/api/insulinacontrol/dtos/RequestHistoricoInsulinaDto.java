package br.api.insulinacontrol.dtos;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class RequestHistoricoInsulinaDto {

    @NotNull
    private UUID idPaciente;
    @NotNull
    private UUID idResponsavel;
    @NotNull
    LocalDateTime dataAplicacao;
    @NotNull
    private Long qtdeInsulinaAplicada;
    @NotNull
    private Long valorGlicose;

    public RequestHistoricoInsulinaDto() {
    }

    public UUID getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(UUID idPaciente) {
        this.idPaciente = idPaciente;
    }

    public UUID getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(UUID idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Long getQtdeInsulinaAplicada() {
        return qtdeInsulinaAplicada;
    }

    public void setQtdeInsulinaAplicada(Long qtdeInsulinaAplicada) {
        this.qtdeInsulinaAplicada = qtdeInsulinaAplicada;
    }

    public Long getValorGlicose() {
        return valorGlicose;
    }

    public void setValorGlicose(Long valorGlicose) {
        this.valorGlicose = valorGlicose;
    }
}
