package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.AvaliacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {

    void deleteByAulaModel(AulaModel aulaModel);

    void deleteByProfessorModel(ProfessorModel professorModel);

    void deleteByAlunoModel(AlunoModel alunoModel);

    List<AvaliacaoModel> findByProfessorModel(ProfessorModel professorModel);

    List<AvaliacaoModel> findByProfessorModelAndAlunoModel(ProfessorModel professorModel, AlunoModel alunoModel);
}

