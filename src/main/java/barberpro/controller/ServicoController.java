package barberpro.controller;

import barberpro.entity.Servico;
import barberpro.service.ServicoService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id);
    }

    @PostMapping
    public Servico salvarServico(
            @Valid @RequestBody Servico servico) {

        return servicoService.salvarServico(servico);
    }

    @DeleteMapping("/{id}")
    public void excluirServico(@PathVariable Long id) {
        servicoService.excluirServico(id);
    }
}