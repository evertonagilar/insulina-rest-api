package br.api.insulinacontrol.controllers;

import br.api.insulinacontrol.configs.security.UserDetailsServiceImpl;
import br.api.insulinacontrol.dtos.RequestHistoricoInsulinaDto;
import br.api.insulinacontrol.models.HistoricoInsulina;
import br.api.insulinacontrol.models.UserModel;
import br.api.insulinacontrol.services.HistoricoInsulinaService;
import exception.RegraNegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/historico-insulina")
public class HistoricoInsulinaController {

    final HistoricoInsulinaService historicoInsulinaService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public HistoricoInsulinaController(HistoricoInsulinaService historicoInsulinaService) {
        this.historicoInsulinaService = historicoInsulinaService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid RequestHistoricoInsulinaDto historicoInsulinaDto) throws Exception {
        UUID idPaciente = historicoInsulinaDto.getIdPaciente();
        UserModel paciente = userDetailsService.findByUserId(idPaciente)
                .orElseThrow(() -> new RegraNegocioException("Paciente com identificador %s não encontrado", idPaciente));

        UUID idResponsavel = historicoInsulinaDto.getIdPaciente();
        UserModel responsavel = userDetailsService.findByUserId(idPaciente)
                .orElseThrow(() -> new RegraNegocioException("Responsável com identificador %s não encontrado", idResponsavel));

        if (historicoInsulinaService.existsByDataAplicacaoAndPaciente(historicoInsulinaDto.getDataAplicacao(), paciente)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Histórico de insulina já existe");
        }

        var historico = new HistoricoInsulina();
        BeanUtils.copyProperties(historicoInsulinaDto, historico);
        historico.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        historico.setPaciente(paciente);
        historico.setResponsavel(responsavel);
        return ResponseEntity.status(HttpStatus.CREATED).body(historicoInsulinaService.save(historico));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<HistoricoInsulina>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(historicoInsulinaService.findAll(pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID id) {
        Optional<HistoricoInsulina> historicoOptional = historicoInsulinaService.findById(id);
        if (!historicoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Histórico de insulina não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(historicoOptional.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<HistoricoInsulina> historicoOptional = historicoInsulinaService.findById(id);
        if (!historicoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Histórico de insulina não encontrado.");
        }
        historicoInsulinaService.delete(historicoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Histórico de insulina removido com sucesso.");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid RequestHistoricoInsulinaDto historicoInsulinaDto) {
        Optional<HistoricoInsulina> historicoOptional = historicoInsulinaService.findById(id);
        if (!historicoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Histórico de insulina não encontrado.");
        }

        var idPacienteOptional = Optional.ofNullable(historicoInsulinaDto.getIdPaciente());
        if (idPacienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não pode alterar o paciente do histórico de insulina.");
        }

        if (historicoInsulinaService.existsByDataAplicacaoAndPaciente(historicoInsulinaDto.getDataAplicacao(), historicoOptional.get().getPaciente())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Histórico de insulina já existe");
        }

        var historico = new HistoricoInsulina();
        var dataRegistro = historico.getDataRegistro();
        BeanUtils.copyProperties(historicoInsulinaDto, historico);
        historico.setDataRegistro(dataRegistro);
        return ResponseEntity.status(HttpStatus.OK).body(historicoInsulinaService.save(historico));
    }


}
