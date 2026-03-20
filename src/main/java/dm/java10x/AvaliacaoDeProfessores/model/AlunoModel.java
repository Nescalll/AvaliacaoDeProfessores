package dm.java10x.AvaliacaoDeProfessores.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aluno")
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  String nome;

    private Enum turma;

    private String senha;

    private String email;

    public void AlunoModel(){}

    public void AlunoModel(String nome, Enum turma, String senha, String email){
        this.nome = nome;
        this.turma = turma;
        this.senha = senha;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNota(String nome) {
        this.nome = nome;
    }

    public Enum getTurma() {
        return turma;
    }

    public void setTurma(Enum turma) {
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
}