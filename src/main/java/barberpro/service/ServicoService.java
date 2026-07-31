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
        return servicoRepository.save(servico);
    }
}