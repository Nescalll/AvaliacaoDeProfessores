package dm.java10x.AvaliacaoDeProfessores.model.abstracte;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import dm.java10x.AvaliacaoDeProfessores.model.entity.UserModel;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_notificacao")
public class NotificacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descrocao")
    private String descricao;

    @Column(name = "id_referencia")
    private Long idDeReferencia;

    @Column(name = "tipo_notificao")
    @Enumerated(EnumType.STRING)
    private TipoDaNotificacao notificacao;

    public NotificacaoModel() {
    }

    public NotificacaoModel(TipoDaNotificacao notificacao, Long idDeReferencia, String desscricao) {
        this.notificacao = notificacao;
        this.descricao = desscricao;
        this.idDeReferencia = idDeReferencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdDeReferencia() {
        return idDeReferencia;
    }

    public void setIdDeReferencia(Long idDeReferencia) {
        this.idDeReferencia = idDeReferencia;
    }

    public TipoDaNotificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(TipoDaNotificacao notificacao) {
        this.notificacao = notificacao;
    }
}
