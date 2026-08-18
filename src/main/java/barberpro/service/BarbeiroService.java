package barberpro.service;

import barberpro.entity.Barbeiro;
import barberpro.repository.BarbeiroRepository;
import barberpro.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;

    public BarbeiroService(BarbeiroRepository barbeiroRepository) {
        this.barbeiroRepository = barbeiroRepository;
    }

    public List<Barbeiro> listarBarbeiros() {
        return barbeiroRepository.findAll();
    }

    public Barbeiro buscarPorId(Long id) {
        return barbeiroRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Barbeiro não encontrado.")
                );
    }

    public Barbeiro salvarBarbeiro(Barbeiro barbeiro) {

        if (barbeiroRepository.existsByTelefone(barbeiro.getTelefone())) {
            throw new IllegalArgumentException(
                    "Já existe um barbeiro cadastrado com este telefone."
            );
        }

        return barbeiroRepository.save(barbeiro);
    }

    public void excluirBarbeiro(Long id) {
        buscarPorId(id);
        barbeiroRepository.deleteById(id);
    }
}