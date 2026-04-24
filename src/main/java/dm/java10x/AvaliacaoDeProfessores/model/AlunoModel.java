package dm.java10x.AvaliacaoDeProfessores.model;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aluno")
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "nome", nullable = false)
    private  String nome;

    @Column(name = "turma", nullable = false)
    @Enumerated(EnumType.STRING)
    private Turma turma;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public AlunoModel(){}

    public AlunoModel(String nome, Turma turma, String senha, String email){
        this.nome = nome;
        this.turma = turma;
        this.senha = senha;
        this.email = email;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}