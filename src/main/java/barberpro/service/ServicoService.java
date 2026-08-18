package barberpro.service;

import barberpro.entity.Servico;
import barberpro.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    public Servico salvarServico(Servico servico) {

    if (servicoRepository.existsByNome(servico.getNome())) {
        throw new IllegalArgumentException(
            "Já existe um serviço cadastrado com este nome."
        );
    }

    return servicoRepository.save(servico);
}
}