package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import dm.java10x.AvaliacaoDeProfessores.model.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AulaRepository;
import dm.java10x.AvaliacaoDeProfessores.repository.AvaliacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<AulaModel> findAll(){
        return aulaRepository.findAll();
    }

    public AulaModel findById(long id){
        Optional<AulaModel> aulaModel = this.aulaRepository.findById(id);
        return aulaModel.orElseThrow(() -> new RuntimeException((
                "Aula não encontrada"
                )));
    }

    @Transactional
    public AulaModel create(AulaModel obj){
        obj.setDataDeCriacao(LocalDateTime.now());
        obj.setDataDeInspiracao(obj.getDataDeCriacao().plusMonths(3));
        obj = this.aulaRepository.save(obj);
        return obj;
    }

    @Transactional
    public void delete(long id){
        AulaModel obj = findById(id);
        try {
            this.avaliacaoRepository.deleteByAulaModel(obj);
            this.aulaRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }
}
