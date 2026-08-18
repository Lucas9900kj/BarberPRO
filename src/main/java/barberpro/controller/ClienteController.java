package barberpro.controller;

import barberpro.entity.Cliente;
import barberpro.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public Cliente criarCliente(@Valid @RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public String excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return "Cliente excluído com sucesso.";
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/teste")
    public String teste() {
        return "BarberPro funcionando!";
    }
}