package barberpro.service;

import barberpro.dto.AgendamentoResponseDTO;
import barberpro.entity.Agendamento;
import barberpro.entity.Barbeiro;
import barberpro.entity.Cliente;
import barberpro.entity.Servico;
import barberpro.entity.StatusAgendamento;
import barberpro.repository.AgendamentoRepository;
import barberpro.repository.BarbeiroRepository;
import barberpro.repository.ClienteRepository;
import barberpro.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final BarbeiroRepository barbeiroRepository;
    private final ServicoRepository servicoRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            ClienteRepository clienteRepository,
            BarbeiroRepository barbeiroRepository,
            ServicoRepository servicoRepository) {

        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento salvarAgendamento(Agendamento agendamento) {

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Não é possível realizar um agendamento para uma data passada."
            );
        }

        Cliente cliente = clienteRepository.findById(
                agendamento.getCliente().getId()
        ).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado.")
        );

        Barbeiro barbeiro = barbeiroRepository.findById(
                agendamento.getBarbeiro().getId()
        ).orElseThrow(() ->
                new RuntimeException("Barbeiro não encontrado.")
        );

        Servico servico = servicoRepository.findById(
                agendamento.getServico().getId()
        ).orElseThrow(() ->
                new RuntimeException("Serviço não encontrado.")
        );

        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);

        boolean ocupado =
                agendamentoRepository.existsByBarbeiroIdAndDataHora(
                        barbeiro.getId(),
                        agendamento.getDataHora()
                );

        if (ocupado) {
            throw new IllegalArgumentException(
                    "O barbeiro já possui um agendamento nesse horário."
            );
        }

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Agendamento não encontrado.")
                );
    }

    public Agendamento alterarStatus(Long id, StatusAgendamento status) {

        Agendamento agendamento = buscarPorId(id);

        agendamento.setStatus(status);

        return agendamentoRepository.save(agendamento);
    }

    public void excluirAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Agendamento atualizarAgendamento(
            Long id,
            Agendamento agendamento) {

        buscarPorId(id);

        Cliente cliente = clienteRepository.findById(
                agendamento.getCliente().getId()
        ).orElseThrow(() ->
                new RuntimeException("Cliente não encontrado.")
        );

        Barbeiro barbeiro = barbeiroRepository.findById(
                agendamento.getBarbeiro().getId()
        ).orElseThrow(() ->
                new RuntimeException("Barbeiro não encontrado.")
        );

        Servico servico = servicoRepository.findById(
                agendamento.getServico().getId()
        ).orElseThrow(() ->
                new RuntimeException("Serviço não encontrado.")
        );

        boolean ocupado =
                agendamentoRepository.existsByBarbeiroIdAndDataHoraAndIdNot(
                        barbeiro.getId(),
                        agendamento.getDataHora(),
                        id
                );

        if (ocupado) {
            throw new IllegalArgumentException(
                    "O barbeiro já possui um agendamento nesse horário."
            );
        }

        agendamento.setId(id);
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);

        return agendamentoRepository.save(agendamento);
    }

    public AgendamentoResponseDTO converterParaDTO(Agendamento agendamento) {

        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();

        dto.setId(agendamento.getId());

        dto.setClienteId(agendamento.getCliente().getId());
        dto.setClienteNome(agendamento.getCliente().getNome());

        dto.setBarbeiroId(agendamento.getBarbeiro().getId());
        dto.setBarbeiroNome(agendamento.getBarbeiro().getNome());

        dto.setServicoId(agendamento.getServico().getId());
        dto.setServicoNome(agendamento.getServico().getNome());
        dto.setServicoPreco(agendamento.getServico().getPreco());

        dto.setDataHora(agendamento.getDataHora());
        dto.setStatus(agendamento.getStatus());

        return dto;
    }
}