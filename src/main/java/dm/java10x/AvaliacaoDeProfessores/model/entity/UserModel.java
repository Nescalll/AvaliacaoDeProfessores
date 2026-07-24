package dm.java10x.AvaliacaoDeProfessores.model.entity;


import dm.java10x.AvaliacaoDeProfessores.enumeradores.Materia;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name ="senha" ,nullable = false)
    private String senha;

    @Column(name = "turma", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Turma> turmas;

    @Enumerated(EnumType.STRING)
    private Materia materia;

    @Column(name ="role" ,nullable = false)
    private String role;
    public UserModel() {
    }

    public UserModel(String nome, String email, String senha, List<Turma> turmas, Materia materia, String role){
        this.email = email;
        this.nome = nome;
        this.materia =materia;
        this.turmas = turmas;
        this.senha = senha;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia getMateria() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
