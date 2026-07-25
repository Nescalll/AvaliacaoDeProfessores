package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.repository.AdministracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdministracaoService {

    @Autowired
    private AdministracaoRepository administracaoRepository;

    @Transactional
    public AdministracaoModel creat(AdministracaoModel obj){
        return administracaoRepository.save(obj);
    }

    public AdministracaoModel findById(Long id){
        Optional<AdministracaoModel> adm = administracaoRepository.findById(id);
        return adm.orElseThrow(() -> new RuntimeException("Adm inexistente"));
    }

    public AdministracaoModel findByEmail(String email){
        return administracaoRepository.findAdministracaoModelByLogin(email);
    }

    public List<AdministracaoModel> findAll(){
        return administracaoRepository.findAll();
    }

    @Transactional
    public void deletarAdm(AdministracaoModel administracaoModel){
        administracaoRepository.delete(administracaoModel);
    }

    @Transactional
    public AdministracaoModel update(AdministracaoModel obj){
        AdministracaoModel newObj = findById(obj.getId());
        if(Objects.nonNull(obj.getLogin())){newObj.setLogin(obj.getLogin());}
        if(Objects.nonNull(obj.getSenha())){newObj.setSenha(obj.getSenha());}
        return this.administracaoRepository.save(newObj);
    }
    }
