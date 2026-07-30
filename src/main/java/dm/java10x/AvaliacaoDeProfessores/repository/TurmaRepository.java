package dm.java10x.AvaliacaoDeProfessores.repository;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.TurmaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<TurmaModel, Long> {

    // Busca pelo número (SMALLINT)
    @Query("SELECT t FROM TurmaModel t WHERE t.turma = :turmaNumero")
    List<TurmaModel> findTurmaModelByTurmaNumero(@Param("turmaNumero") Integer turmaNumero);

    // Busca pelo Enum (se usar ORDINAL)
    @Query("SELECT t FROM TurmaModel t WHERE t.turma = :turma")
    List<TurmaModel> findTurmaModelByTurma(@Param("turma") Turma turma);
    List<ProfessorModel> findProfessorModelByTurma(Turma turma);

    void deleteByProfessorModel(ProfessorModel professor);
}
