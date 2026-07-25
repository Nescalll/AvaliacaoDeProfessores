package dm.java10x.AvaliacaoDeProfessores.Controler;

import dm.java10x.AvaliacaoDeProfessores.dto.*;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.TipoDaNotificacao;
import dm.java10x.AvaliacaoDeProfessores.infra.security.TokenService;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.NotificacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.UserModel;
import dm.java10x.AvaliacaoDeProfessores.repository.TurmaRepository;
import dm.java10x.AvaliacaoDeProfessores.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AdministracaoService administracaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private TokenService tokenService;

    // ============ LOGINS ============


    @PostMapping("/login/aluno")
    public ResponseEntity loginAluno(@RequestBody AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            if (!(userDetails instanceof AlunoModel)) {
                return ResponseEntity.badRequest().body("Usuário não é um aluno");
            }

            String token = tokenService.generateToken(userDetails);
            AlunoModel aluno = (AlunoModel) userDetails;

            return ResponseEntity.ok(new LoginResponseAlunoDTO(
                    token, "aluno", aluno.getNome(), aluno.getEmail(), aluno.getTurma().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no login: " + e.getMessage());
        }
    }

    @PostMapping("/login/professor")
    public ResponseEntity loginProfessor(@RequestBody AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            if (!(userDetails instanceof ProfessorModel)) {
                return ResponseEntity.badRequest().body("Usuário não é um professor");
            }

            String token = tokenService.generateToken(userDetails);
            ProfessorModel professor = (ProfessorModel) userDetails;

            return ResponseEntity.ok(new LoginResponseProfessorDTO(
                    token, "professor", professor.getNome(), professor.getEmail(),
                    professor.getMateria().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no login: " + e.getMessage());
        }
    }

    @PostMapping("/login/adm")
    public ResponseEntity loginAdm(@RequestBody AuthenticationDTO data){
        try {
            System.out.println("Adm recebido");
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            System.out.println("Configurar authentificação");
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            if (!(userDetails instanceof AdministracaoModel)) {
                return ResponseEntity.badRequest().body("Usuário não é um adm");
            }

            String token = tokenService.generateToken(userDetails);
            AdministracaoModel adm = (AdministracaoModel) userDetails;

            return ResponseEntity.ok(new LoginResponseDTO(
                    token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro no login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity solicitarRegistro(@RequestBody RegisterAnyDTO data){
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("success", true);
        try{
        if(alunoService.findByEmail(data.email()) != null
                || professorService.findByEmail(data.email()) != null
                || administracaoService.findByEmail(data.email()) != null
                || ! data.email().contains("@")) {
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Email", "O email: "+data.email()+" não é valido");
        }
        if (data.senha().length() < 8){
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Senha", "A senha deve conter no minimo 8 caracteres");
        }
        if (data.turmas().isEmpty()){
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Turma", "Deve conter no minimo uma turma");
        }

        if (data.turmas().size() > 1 && data.role().equalsIgnoreCase("aluno")){
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Turma", "Aluno deve conter apenas uma turma");
        }

        if (data.role().equalsIgnoreCase("professor") && data.materia().isEmpty()){
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Materia", "Um professor deve conter uma materia");
        }

        if (! data.role().equalsIgnoreCase("professor") && ! data.role().equalsIgnoreCase("aluno")){
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Role", "role: "+data.role()+" invalida");
        }

        Boolean sucess = (Boolean) resposta.get("success");
        if (sucess){
            UserModel user = new UserModel();
            user.setEmail(data.email());
            user.setNome(data.nome());
            user.setRole(data.role());
            user.setSenha(data.senha());
            user.setTurmas(data.turmas());
            if (data.materia().isPresent()){
                user.setMateria(data.materia().get());
            }
            user = userService.create(user);
            NotificacaoModel notificacao = new NotificacaoModel(TipoDaNotificacao.USUARIO, user.getId(), "Adicionar novo usuario com role: "+user.getRole());
            notificacaoService.creat(notificacao);
            resposta.put("notificação", "O Adm recebeu uma notifição sobre sua solicitação de cadastro com a role: "+user.getRole());
            return ResponseEntity.ok().body(resposta);
        }
        return ResponseEntity.badRequest().body(resposta);
        } catch (Exception e) {
            resposta.compute("success", (key, value) -> value = false);
            resposta.put("Error", e.getMessage());
            throw new RuntimeException(resposta.toString());
        }
    }
}