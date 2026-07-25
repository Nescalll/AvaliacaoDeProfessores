package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.NotificacaoModel;
import dm.java10x.AvaliacaoDeProfessores.repository.NotificacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Transactional
    public NotificacaoModel creat(NotificacaoModel obj){
        obj = this.notificacaoRepository.save(obj);
        return obj;
    }

    public List<NotificacaoModel> findAll(){
        return this.notificacaoRepository.findAll();
    }

    public NotificacaoModel findById(Long id){
        Optional<NotificacaoModel> notificacao = notificacaoRepository.findById(id);
        return notificacao.orElseThrow(() -> new RuntimeException("Notificação não existe"));
    }

    @Transactional
    public void delete(Long id){
        this.notificacaoRepository.delete(findById(id));
    }

    public NotificacaoModel findNewUserByIdDeReferencia(Long id){
        List<NotificacaoModel> notificacoes = this.notificacaoRepository.findByIdDeReferencia(id);
            for (NotificacaoModel n: notificacoes){
                if (n.getIdDeReferencia().equals(TipoDaNotificacao.USUARIO)) return n;
            }

        System.out.println("Notificação não encontrada");
        return null;
    }

}
