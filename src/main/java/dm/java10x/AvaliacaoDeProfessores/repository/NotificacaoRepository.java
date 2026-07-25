package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.abstracte.NotificacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacaoRepository extends JpaRepository<NotificacaoModel, Long> {


    List<NotificacaoModel> findByIdDeReferencia(Long aLong);
}
