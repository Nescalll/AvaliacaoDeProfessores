package dm.java10x.AvaliacaoDeProfessores.model;


import dm.java10x.AvaliacaoDeProfessores.enumeradores.Materia;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_professor")
public class ProfessorModel {

    @Column(nullable = false)
    private String nome;

    private Materia materia;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private Turma turma;

    public ProfessorModel() {}

    public ProfessorModel(String nome, Materia materia,String senha, Turma turma) {
        this.nome = nome;
        this.materia = materia;
        this.senha = senha;
        this.turma = turma;
    }

    public Enum getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
}
