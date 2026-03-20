package dm.java10x.AvaliacaoDeProfessores.Controler.Materia.Professores;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_cadastro")
public class ProfessorModel {

    private String nome;

    private int idade;

    private List<String> pontoMelhorar;

    @Column(nullable = false)
    private int nota;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    public ProfessorModel() {
    }

    public ProfessorModel(String nome, int idade, int nota) {
        this.idade = idade;
        this.nome = nome;
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

}
