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
import barberpro.exception.RecursoNaoEncontradoException;

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

        if (agendamento.getDataHora() == null) {
            throw new IllegalArgumentException(
                    "A data e hora do agendamento são obrigatórias."
            );
        }

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Não é possível realizar um agendamento para uma data passada."
            );
        }

        if (agendamento.getCliente() == null ||
                agendamento.getCliente().getId() == null) {

            throw new IllegalArgumentException(
                    "O cliente é obrigatório."
            );
        }

        if (agendamento.getBarbeiro() == null ||
                agendamento.getBarbeiro().getId() == null) {

            throw new IllegalArgumentException(
                    "O barbeiro é obrigatório."
            );
        }

        if (agendamento.getServico() == null ||
                agendamento.getServico().getId() == null) {

            throw new IllegalArgumentException(
                    "O serviço é obrigatório."
            );
        }

        Cliente cliente = clienteRepository.findById(
                agendamento.getCliente().getId()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException("Cliente não encontrado.")
        );

        Barbeiro barbeiro = barbeiroRepository.findById(
                agendamento.getBarbeiro().getId()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException("Barbeiro não encontrado.")
        );

        Servico servico = servicoRepository.findById(
                agendamento.getServico().getId()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException("Serviço não encontrado.")
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

        boolean clienteOcupado =
                agendamentoRepository.existsByClienteIdAndDataHora(
                        cliente.getId(),
                        agendamento.getDataHora()
                );

        if (clienteOcupado) {
            throw new IllegalArgumentException(
                    "O cliente já possui um agendamento nesse horário."
            );
        }

        if (agendamento.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO);
        }

        return agendamentoRepository.save(agendamento);
    }

        public Agendamento buscarPorId(Long id) {
           return agendamentoRepository.findById(id)
                   .orElseThrow(() ->
                           new RecursoNaoEncontradoException(
                            "Agendamento não encontrado."
                    )
            );
}
    public Agendamento alterarStatus(
            Long id,
            StatusAgendamento novoStatus) {

        if (novoStatus == null) {
            throw new IllegalArgumentException(
                    "O status do agendamento é obrigatório."
            );
        }

        Agendamento agendamento = buscarPorId(id);

        StatusAgendamento statusAtual = agendamento.getStatus();

        boolean transicaoValida =
                (statusAtual == StatusAgendamento.AGENDADO &&
                        (novoStatus == StatusAgendamento.CONFIRMADO ||
                         novoStatus == StatusAgendamento.CANCELADO))
                ||
                (statusAtual == StatusAgendamento.CONFIRMADO &&
                        (novoStatus == StatusAgendamento.CONCLUIDO ||
                         novoStatus == StatusAgendamento.CANCELADO));

        if (!transicaoValida) {
            throw new IllegalArgumentException(
                    "Alteração de status inválida: "
                            + statusAtual + " → " + novoStatus
            );
        }

        agendamento.setStatus(novoStatus);

        return agendamentoRepository.save(agendamento);
    }

    public void excluirAgendamento(Long id) {

        Agendamento agendamento = buscarPorId(id);

        if (agendamento.getStatus() == StatusAgendamento.CONCLUIDO ||
                agendamento.getStatus() == StatusAgendamento.CANCELADO) {

            throw new IllegalArgumentException(
                    "Não é possível excluir um agendamento concluído ou cancelado."
            );
        }

        agendamentoRepository.delete(agendamento);
    }

    public Agendamento atualizarAgendamento(
            Long id,
            Agendamento agendamento) {

        Agendamento existente = buscarPorId(id);

        if (existente.getStatus() == StatusAgendamento.CONCLUIDO ||
                existente.getStatus() == StatusAgendamento.CANCELADO) {

            throw new IllegalArgumentException(
                    "Não é possível alterar um agendamento concluído ou cancelado."
            );
        }

        if (agendamento.getDataHora() == null) {
            throw new IllegalArgumentException(
                    "A data e hora do agendamento são obrigatórias."
            );
        }

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Não é possível alterar um agendamento para uma data passada."
            );
        }

        if (agendamento.getCliente() == null ||
                agendamento.getCliente().getId() == null) {

            throw new IllegalArgumentException(
                    "O cliente é obrigatório."
            );
        }

        if (agendamento.getBarbeiro() == null ||
                agendamento.getBarbeiro().getId() == null) {

            throw new IllegalArgumentException(
                    "O barbeiro é obrigatório."
            );
        }

        if (agendamento.getServico() == null ||
                agendamento.getServico().getId() == null) {

            throw new IllegalArgumentException(
                    "O serviço é obrigatório."
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

        boolean clienteOcupado =
                agendamentoRepository.existsByClienteIdAndDataHoraAndIdNot(
                        cliente.getId(),
                        agendamento.getDataHora(),
                        id
                );

        if (clienteOcupado) {
            throw new IllegalArgumentException(
                    "O cliente já possui um agendamento nesse horário."
            );
        }

        agendamento.setId(id);
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);
        agendamento.setStatus(existente.getStatus());

        return agendamentoRepository.save(agendamento);
    }

    public AgendamentoResponseDTO converterParaDTO(
            Agendamento agendamento) {

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