package dm.java10x.AvaliacaoDeProfessores.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aula")
public class AulaModel {

    private Enum adjetivo;

    private int nota;

    private LocalDateTime data;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public void AulaModel() { }

    public void AulaModel(Enum adjetivo, int nota, LocalDateTime data){

    }

}
