package br.api.insulinacontrol.services;

import br.api.insulinacontrol.models.HistoricoInsulina;
import br.api.insulinacontrol.models.UserModel;
import br.api.insulinacontrol.repositories.HistoricoInsulinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class HistoricoInsulinaService {

    final HistoricoInsulinaRepository historicoInsulinaRepository;

    public HistoricoInsulinaService(HistoricoInsulinaRepository historicoInsulinaRepository) {
        this.historicoInsulinaRepository = historicoInsulinaRepository;
    }

    @Transactional
    public HistoricoInsulina save(HistoricoInsulina historicoInsulina) {
        return historicoInsulinaRepository.save(historicoInsulina);
    }

    public Page<HistoricoInsulina> findAll(Pageable pageable) {
        return historicoInsulinaRepository.findAll(pageable);
    }

    public Optional<HistoricoInsulina> findById(UUID id) {
        return historicoInsulinaRepository.findById(id);
    }

    @Transactional
    public void delete(HistoricoInsulina historicoInsulina) {
        historicoInsulinaRepository.delete(historicoInsulina);
    }

    public boolean existsByDataAplicacaoAndPaciente(LocalDateTime dataAplicacao, UserModel paciente){
        return historicoInsulinaRepository.existsByDataAplicacaoAndPaciente(dataAplicacao, paciente);
    }
}
