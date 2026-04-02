package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public List<AlunoModel> findAll() {
        return this.alunoRepository.findAll();
    }


    public AlunoModel findById(Long id){
        Optional<AlunoModel> alunoModel = this.alunoRepository.findById(id);
        return alunoModel.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }

    @Transactional
    public AlunoModel create(AlunoModel obj){
        obj = this.alunoRepository.save(obj);
        return obj;
    }

    @Transactional
    public AlunoModel update(AlunoModel obj){
        AlunoModel newObj = findById(obj.getId());
        if(Objects.nonNull(obj.getNome())){newObj.setNome(obj.getNome());}
        if(Objects.nonNull(obj.getEmail())){newObj.setEmail(obj.getEmail());}
        if(Objects.nonNull(obj.getSenha())){newObj.setSenha(obj.getSenha());}
        if(Objects.nonNull(obj.getTurma())){newObj.setTurma(obj.getTurma());}
        return this.alunoRepository.save(newObj);
    }

    @Transactional
    public void delete(long id){
        findById(id);
        try {
            this.alunoRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possivel excluir pois há entidades relacionadas");
        }
    }

}