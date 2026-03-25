package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.AulaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository  extends JpaRepository<AulaModel, Long> {
}
