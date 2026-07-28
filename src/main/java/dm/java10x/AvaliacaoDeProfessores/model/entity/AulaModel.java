package dm.java10x.AvaliacaoDeProfessores.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aula")
public class AulaModel {

    @Column(name = "nota", nullable = false)
    private int nota;

    @Column(name = "dataDeCriacao", nullable = false)
    private LocalDateTime dataDeCriacao;

    @Column(name = "dataDeInspiracao", nullable = false)
    private LocalDateTime dataDeInspiracao;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "comentario")
    private String comentario;
    public AulaModel() {
    }

    public AulaModel(int nota, LocalDateTime dataDeCriacao, LocalDateTime dataDeInspiracao, String comentario) {
        this.nota = nota;
        this.dataDeCriacao = dataDeCriacao;
        this.dataDeInspiracao = dataDeInspiracao;
        this.comentario = comentario;

    }

    public AulaModel(int nota, LocalDateTime dataDeCriacao, LocalDateTime dataDeInspiracao) {
        this.nota = nota;
        this.dataDeCriacao = dataDeCriacao;
        this.dataDeInspiracao = dataDeInspiracao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDataDeInspiracao() {
        return dataDeInspiracao;
    }

    public void setDataDeInspiracao(LocalDateTime dataDeInspiracao) {
        this.dataDeInspiracao = dataDeInspiracao;
    }
}
