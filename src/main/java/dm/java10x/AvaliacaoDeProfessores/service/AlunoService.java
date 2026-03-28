package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoModel findById(Long id){
        Optional<AlunoModel> alunoModel = this.alunoRepository.findById(id);
        return alunoModel.orElseThrow(() -> new RuntimeException(
                "Aluno não encontrado"
        ));
    }

}
