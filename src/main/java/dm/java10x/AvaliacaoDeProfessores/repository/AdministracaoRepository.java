package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdministracaoRepository extends JpaRepository<AdministracaoModel, Long> {

    UserDetails findByLogin(String email);

    AdministracaoModel findAdministracaoModelByLogin(String email);
}
