package dm.java10x.AvaliacaoDeProfessores.Controler;

import dm.java10x.AvaliacaoDeProfessores.dto.*;
import dm.java10x.AvaliacaoDeProfessores.infra.security.TokenService;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.repository.TurmaRepository;
import dm.java10x.AvaliacaoDeProfessores.service.AdministracaoService;
import dm.java10x.AvaliacaoDeProfessores.service.AlunoService;
import dm.java10x.AvaliacaoDeProfessores.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
}