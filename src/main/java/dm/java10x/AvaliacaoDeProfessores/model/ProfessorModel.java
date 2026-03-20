package dm.java10x.AvaliacaoDeProfessores.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_professor")
public class ProfessorModel {

    @Column(nullable = false)
    private String nome;

    private Enum materia;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    private Enum turma;

    public ProfessorModel() {}

    public ProfessorModel(String nome, Enum materia,String senha, Enum turma) {
        this.nome = nome;
        this.materia = materia;
        this.senha = senha;
        this.turma = turma;
    }

    public Enum getMateria() {
        return materia;
    }

    public void setMateria(Enum materia) {
        this.materia = materia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Enum getTurma() {
        return turma;
    }

    public void setTurma(Enum turma) {
        this.turma = turma;
    }
}
