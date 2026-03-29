package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AulaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    public AulaModel findById(long id){
        Optional<AulaModel> aulaModel = this.aulaRepository.findById(id);
        return aulaModel.orElseThrow(() -> new RuntimeException((
                "Aula não encontrada"
                )));
    }

    @Transactional
    public AulaModel creat(AulaModel obj){
        obj = this.aulaRepository.save(obj);
        return obj;
    }

    @Transactional
    public void delete(long id){
        findById(id);
        try {
            this.aulaRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }
}
