package barberpro.repository;

import barberpro.entity.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
    
    boolean existsByTelefone(String telefone);
    
}