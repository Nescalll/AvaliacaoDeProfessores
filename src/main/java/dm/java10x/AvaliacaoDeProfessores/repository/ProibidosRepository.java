package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.ProibidosModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProibidosRepository extends JpaRepository<ProibidosModel, Long> {
    List<ProibidosModel> findAllByTipo(TipoDaNotificacao tipo);

}
