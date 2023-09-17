package br.api.insulinacontrol.repositories;

import br.api.insulinacontrol.models.HistoricoInsulina;
import br.api.insulinacontrol.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface HistoricoInsulinaRepository extends JpaRepository<HistoricoInsulina, UUID> {
    boolean existsByDataAplicacaoAndPaciente(LocalDateTime dataAplicacao, UserModel paciente);
}
