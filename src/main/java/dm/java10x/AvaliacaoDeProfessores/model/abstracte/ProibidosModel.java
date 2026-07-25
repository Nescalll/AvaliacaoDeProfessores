package dm.java10x.AvaliacaoDeProfessores.model.abstracte;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import jakarta.persistence.*;


@Entity
@Table(name = "tb_proibicao")
public class ProibidosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    private TipoDaNotificacao tipo;

    @Column(name = "bloqueado")
    private String bloqueado;

    public ProibidosModel() {
    }

    public ProibidosModel(TipoDaNotificacao tipo, String bloqueado) {
        this.tipo = tipo;
        this.bloqueado = bloqueado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }

    public TipoDaNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoDaNotificacao tipo) {
        this.tipo = tipo;
    }
}
