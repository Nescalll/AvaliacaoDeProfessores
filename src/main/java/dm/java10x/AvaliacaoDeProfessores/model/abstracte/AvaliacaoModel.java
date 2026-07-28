package dm.java10x.AvaliacaoDeProfessores.model.abstracte;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_avaliacao")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "professorId")
    private ProfessorModel professorModel;

    @ManyToOne
    @JoinColumn(name = "alunoId")
    private AlunoModel alunoModel;

    @OneToOne
    @JoinColumn(name = "aulaId")
    private AulaModel aulaModel;

    public AvaliacaoModel(){}

    public AvaliacaoModel(long id, AlunoModel alunoModel, ProfessorModel professorModel, AulaModel aulaModel) {
        this.id = id;
        this.alunoModel = alunoModel;
        this.aulaModel = aulaModel;
        this.professorModel = professorModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProfessorModel getProfessorModel() {
        return professorModel;
    }

    public void setProfessorModel(ProfessorModel professorModel) {
        this.professorModel = professorModel;
    }

    public AlunoModel getAlunoModel() {
        return alunoModel;
    }

    public void setAlunoModel(AlunoModel alunoModel) {
        this.alunoModel = alunoModel;
    }

    public AulaModel getAulaModel() {
        return aulaModel;
    }

    public void setAulaModel(AulaModel aulaModel) {
        this.aulaModel = aulaModel;
    }


}
