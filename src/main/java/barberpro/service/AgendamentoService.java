package barberpro.service;

import barberpro.entity.Agendamento;
import barberpro.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento salvarAgendamento(Agendamento agendamento) {

        boolean ocupado =
                agendamentoRepository.existsByBarbeiroIdAndDataHora(
                        agendamento.getBarbeiro().getId(),
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
                        new RuntimeException("Agendamento não encontrado."));
    }

    public void excluirAgendamento(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Agendamento atualizarAgendamento(Long id, Agendamento agendamento) {
        Agendamento agendamentoExistente = buscarPorId(id);

        boolean ocupado =
                agendamentoRepository.existsByBarbeiroIdAndDataHoraAndIdNot(
                        agendamento.getBarbeiro().getId(),
                        agendamento.getDataHora(),
                        id
                );

        if (ocupado) {
            throw new IllegalArgumentException(
                    "O barbeiro já possui um agendamento nesse horário."
            );
        }

        agendamento.setId(id);

        return agendamentoRepository.save(agendamentoExistente);
    }
}