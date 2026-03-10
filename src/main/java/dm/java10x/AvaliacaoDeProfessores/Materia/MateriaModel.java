package dm.java10x.AvaliacaoDeProfessores.Materia;

import dm.java10x.AvaliacaoDeProfessores.Professores.ProfessorModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_materia")
public class MateriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String area;

    @OneToMany(mappedBy = "materia")
    private List<ProfessorModel> professor;

    public MateriaModel() {
    }

    public MateriaModel(String nome, String area) {
        this.nome = nome;
        this.area = area;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
