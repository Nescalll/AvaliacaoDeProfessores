package dm.java10x.AvaliacaoDeProfessores.model;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aula")
public class AulaModel {

    @Enumerated(EnumType.STRING)
    private Adjetivo adjetivo;

    private int nota;

    private LocalDateTime data;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public AulaModel() { }

    public AulaModel(Adjetivo adjetivo, int nota, LocalDateTime data){
        this.adjetivo = adjetivo;
        this.nota = nota;
        this.data = data;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
