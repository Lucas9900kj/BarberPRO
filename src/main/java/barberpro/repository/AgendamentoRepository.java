package barberpro.repository;

import barberpro.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiroIdAndDataHora(
            Long barbeiroId,
            LocalDateTime dataHora
    );

    boolean existsByBarbeiroIdAndDataHoraAndIdNot(
            Long barbeiroId,
            LocalDateTime dataHora,
            Long id
    );

    boolean existsByClienteIdAndDataHora(
            Long clienteId,
            LocalDateTime dataHora
    );

    boolean existsByClienteIdAndDataHoraAndIdNot(
            Long clienteId,
            LocalDateTime dataHora,
            Long id
    );
}