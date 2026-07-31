package barberpro.controller;

import barberpro.entity.Servico;
import barberpro.service.ServicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public List<Servico> listarServicos() {
        return servicoService.listarServicos();
    }

    @PostMapping
    public Servico salvarServico(@RequestBody Servico servico) {
        return servicoService.salvarServico(servico);
    }
}