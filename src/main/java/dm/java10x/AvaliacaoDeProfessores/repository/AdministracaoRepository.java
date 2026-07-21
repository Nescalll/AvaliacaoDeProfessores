package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministracaoRepository extends JpaRepository<AdministracaoModel, Long> {
}
