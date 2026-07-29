package barberpro.controller;

import barberpro.entity.Barbeiro;
import barberpro.service.BarbeiroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @GetMapping
    public List<Barbeiro> listarBarbeiros() {
        return barbeiroService.listarBarbeiros();
    }

    @PostMapping
    public Barbeiro criarBarbeiro(@RequestBody Barbeiro barbeiro) {
        return barbeiroService.salvarBarbeiro(barbeiro);
    }

    @DeleteMapping("/{id}")
    public void excluirBarbeiro(@PathVariable Long id) {
        barbeiroService.excluirBarbeiro(id);
    }
}