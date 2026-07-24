package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.abstracte.ProibidosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProibidosRepository extends JpaRepository<ProibidosModel, Long> {
}
