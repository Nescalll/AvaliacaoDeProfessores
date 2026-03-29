package dm.java10x.AvaliacaoDeProfessores.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_avaliacao")
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "professorId")
    private ProfessorModel professor;

    @ManyToOne
    @JoinColumn(name = "alunoId")
    private AlunoModel aluno;

    @ManyToOne
    @JoinColumn(name = "aulaId")
    private AulaModel idAula;

    public AvaliacaoModel(){}

    public AvaliacaoModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
