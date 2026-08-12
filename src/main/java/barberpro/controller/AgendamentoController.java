package barberpro.controller;

import barberpro.entity.Agendamento;
import barberpro.service.AgendamentoService;
import org.springframework.web.bind.annotation.*;
import barberpro.dto.StatusAgendamentoDTO;
import jakarta.validation.Valid;
import barberpro.dto.AgendamentoResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public List<Agendamento> listarAgendamentos() {
        return agendamentoService.listarAgendamentos();
    }

    @GetMapping("/{id}")
    public Agendamento buscarPorId(@PathVariable Long id) {
        return agendamentoService.buscarPorId(id);
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
    public Agendamento atualizar(
            @PathVariable Long id,
            @RequestBody Agendamento agendamento) {

            return agendamentoService.atualizarAgendamento(id, agendamento);
        }

    @PatchMapping("/{id}/status")
    public Agendamento alterarStatus(
            @PathVariable Long id,
            @RequestBody StatusAgendamentoDTO statusAgendamentoDTO) {

        return agendamentoService.alterarStatus(id, statusAgendamentoDTO.getStatus());
    }

}