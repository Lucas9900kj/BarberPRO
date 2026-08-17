package barberpro.controller;

import barberpro.dto.AgendamentoResponseDTO;
import barberpro.dto.StatusAgendamentoDTO;
import barberpro.entity.Agendamento;
import barberpro.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return agendamentoService.listarAgendamentos()
                .stream()
                .map(agendamentoService::converterParaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgendamentoResponseDTO buscarPorId(@PathVariable Long id) {

        Agendamento agendamento = agendamentoService.buscarPorId(id);

        return agendamentoService.converterParaDTO(agendamento);
    }

    @PostMapping
    public AgendamentoResponseDTO salvarAgendamento(
            @Valid @RequestBody Agendamento agendamento) {

        Agendamento salvo = agendamentoService.salvarAgendamento(agendamento);

        return agendamentoService.converterParaDTO(salvo);
    }

    @DeleteMapping("/{id}")
    public void excluirAgendamento(@PathVariable Long id) {
        agendamentoService.excluirAgendamento(id);
    }

    @PutMapping("/{id}")
    public AgendamentoResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody Agendamento agendamento) {

        Agendamento atualizado =
                agendamentoService.atualizarAgendamento(id, agendamento);

        return agendamentoService.converterParaDTO(atualizado);
    }

    @PatchMapping("/{id}/status")
    public AgendamentoResponseDTO alterarStatus(
            @PathVariable Long id,
            @RequestBody StatusAgendamentoDTO statusAgendamentoDTO) {

        Agendamento atualizado =
                agendamentoService.alterarStatus(
                        id,
                        statusAgendamentoDTO.getStatus()
                );

        return agendamentoService.converterParaDTO(atualizado);
    }
}