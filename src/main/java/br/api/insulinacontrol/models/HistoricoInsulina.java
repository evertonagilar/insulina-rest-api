package br.api.insulinacontrol.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_HISTORICO_INSULINA")
public class HistoricoInsulina implements Serializable {
    private static final long serialVersionUID = 6156559679997323265L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dataAplicacao;
    private LocalDateTime dataRegistro;
    private LocalDateTime dataAlteracao;
    @ManyToOne
    private UserModel paciente;
    @ManyToOne
    private UserModel responsavel;
    private Long qtdeInsulinaAplicada;
    private Long valorGlicose;

    public HistoricoInsulina() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataRegistro) {
        this.dataAplicacao = dataRegistro;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public UserModel getPaciente() {
        return paciente;
    }

    public void setPaciente(UserModel paciente) {
        this.paciente = paciente;
    }

    public UserModel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UserModel responsavel) {
        this.responsavel = responsavel;
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
