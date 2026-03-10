package dm.java10x.AvaliacaoDeProfessores;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_cadastro")
public class ProfessorModel {

    String nome;
    String materia;
    int idade;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    public ProfessorModel() {
    }

    public ProfessorModel(String nome, String materia, int idade) {
        this.idade = idade;
        this.nome = nome;
        this.materia = materia;
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
