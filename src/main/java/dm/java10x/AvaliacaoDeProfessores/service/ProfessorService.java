package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorModel findById(long id){
        Optional<ProfessorModel> professorModel = this.professorRepository.findById(id);
        return professorModel.orElseThrow(() -> new RuntimeException(
                "Professor não encontrado"
        ));
    }

    @Transactional
    public ProfessorModel creat(ProfessorModel obj){
        obj = this.professorRepository.save(obj);
        return obj;
    }

    @Transactional
    public ProfessorModel update(ProfessorModel obj){
        ProfessorModel newProfessor = findById(obj.getId());
        newProfessor.setSenha(obj.getSenha());
        return this.professorRepository.save(newProfessor);
    }

    @Transactional
    public void delete(long id){
        findById(id);
        try {
            this.professorRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }
}
