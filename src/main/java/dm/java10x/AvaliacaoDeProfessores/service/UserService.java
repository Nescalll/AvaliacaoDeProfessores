package dm.java10x.AvaliacaoDeProfessores.service;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.TurmaModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.UserModel;
import dm.java10x.AvaliacaoDeProfessores.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel findById(long id){
        Optional<UserModel> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usario inexistente"));
    }

    @Transactional
    public UserModel create(UserModel obj){
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public void delete(long id){
        UserModel user = findById(id);
        this.userRepository.delete(user);
    }
}
