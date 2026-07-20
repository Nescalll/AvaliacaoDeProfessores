package dm.java10x.AvaliacaoDeProfessores.model;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Melhorias;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_avaliacao")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Melhorias melhorias;

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

    public AvaliacaoModel(long id, AlunoModel alunoModel, ProfessorModel professorModel, AulaModel aulaModel, Melhorias melhorias) {
        this.id = id;
        this.alunoModel = alunoModel;
        this.aulaModel = aulaModel;
        this.professorModel = professorModel;
        this.melhorias = melhorias;
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

    public Melhorias getMelhorias() {
        return melhorias;
    }

    public void setMelhorias(Melhorias melhorias) {
        this.melhorias = melhorias;
    }
}
