package barberpro.controller;

import barberpro.entity.Agendamento;
import barberpro.service.AgendamentoService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Agendamento salvarAgendamento(@RequestBody Agendamento agendamento) {
        return agendamentoService.salvarAgendamento(agendamento);
    }
}