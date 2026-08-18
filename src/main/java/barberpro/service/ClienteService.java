package barberpro.service;

import barberpro.entity.Cliente;
import barberpro.repository.ClienteRepository;
import barberpro.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Cliente não encontrado.")
                );
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente salvarCliente(Cliente cliente) {

    if (clienteRepository.existsByEmail(cliente.getEmail())) {
        throw new IllegalArgumentException(
                "Já existe um cliente cadastrado com este e-mail."
        );
    }

    if (clienteRepository.existsByTelefone(cliente.getTelefone())) {
        throw new IllegalArgumentException(
                "Já existe um cliente cadastrado com este telefone."
        );
    }

    return clienteRepository.save(cliente);
    }

    public void excluirCliente(Long id) {
        buscarPorId(id);
        clienteRepository.deleteById(id);
    }
}