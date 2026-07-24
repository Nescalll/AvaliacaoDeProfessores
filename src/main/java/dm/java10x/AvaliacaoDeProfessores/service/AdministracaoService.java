package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AdministracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministracaoService {

    @Autowired
    private AdministracaoRepository administracaoRepository;

    @Transactional
    public AdministracaoModel creat(AdministracaoModel obj){
        return administracaoRepository.save(obj);
    }

    private AdministracaoModel findByEmail(String email){
        return administracaoRepository.findAdministracaoModelByLogin(email);
    }

}
