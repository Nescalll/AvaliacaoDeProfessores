package dm.java10x.AvaliacaoDeProfessores.model.entity;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Melhorias;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_aula")
public class AulaModel {

    @Enumerated(EnumType.STRING)
    @Column(name = "adjetivo", nullable = false)
    private Adjetivo adjetivo;

    @Column(name = "nota", nullable = false)
    private int nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "melhorias", nullable = false)
    private List<Melhorias> melhorias;

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

    public AulaModel(Adjetivo adjetivo, int nota, LocalDateTime dataDeCriacao, LocalDateTime dataDeInspiracao, List<Melhorias> melhorias, String comentario) {
        this.adjetivo = adjetivo;
        this.nota = nota;
        this.dataDeCriacao = dataDeCriacao;
        this.dataDeInspiracao = dataDeInspiracao;
        this.melhorias = melhorias;
        this.comentario = comentario;

    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Adjetivo getAdjetivo() {
        return adjetivo;
    }

    public void setAdjetivo(Adjetivo adjetivo) {
        this.adjetivo = adjetivo;
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

    public List<Melhorias> getMelhorias() {return melhorias;}

    public void setMelhorias(List<Melhorias> melhorias){this.melhorias.addAll(melhorias);}
}
