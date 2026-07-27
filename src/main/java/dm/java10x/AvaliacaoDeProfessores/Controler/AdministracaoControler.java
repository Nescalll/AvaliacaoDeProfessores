package dm.java10x.AvaliacaoDeProfessores.Controler;


import dm.java10x.AvaliacaoDeProfessores.dto.ProfessorUpdateDTO;
import dm.java10x.AvaliacaoDeProfessores.dto.RegisterAlunoDTO;
import dm.java10x.AvaliacaoDeProfessores.dto.RegisterAdmDTO;
import dm.java10x.AvaliacaoDeProfessores.dto.RegisterProfessorDTO;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.NotificacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AdministracaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.UserModel;
import dm.java10x.AvaliacaoDeProfessores.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/administracao")
public class AdministracaoControler {

    @Autowired
    private AdministracaoService administracaoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UserService userService;

    //Alunos
    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoModel>> listarTodosAlunos() {
        List<AlunoModel> alunos = alunoService.findAll();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<AlunoModel> buscarAlunoPorId(@PathVariable Long id) {
        AlunoModel aluno = alunoService.findById(id);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/alunos/{id}")
    public ResponseEntity<AlunoModel> atualizarAluno(@PathVariable Long id, @RequestBody AlunoModel aluno) {
        aluno.setId(id);
        AlunoModel alunoAtualizado = alunoService.update(aluno);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        AlunoModel alunoModel = alunoService.findById(id);
        if (Objects.isNull(alunoModel)){
            return ResponseEntity.notFound().build();
        }
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Professor

    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorModel>> listarTodosProfessores() {
        List<ProfessorModel> professor = professorService.findAll();
        return ResponseEntity.ok(professor);
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<ProfessorModel> buscarProfessorPorId(@PathVariable Long id) {
        ProfessorModel professor = professorService.findById(id);
        return ResponseEntity.ok(professor);
    }

    @PutMapping(value = "/professores/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @ModelAttribute ProfessorUpdateDTO obj) {
        if (Objects.isNull(professorService.findById(id))){
            return ResponseEntity.notFound().build();
        }
        ProfessorModel professorAtualizado = professorService.update(obj, id);
        return ResponseEntity.ok(professorAtualizado);
    }

    @PostMapping("/professores/upload/{id}")
    public ResponseEntity<?> uploadImageProfessor(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {

            if (Objects.isNull(professorService.findById(id))){
                return ResponseEntity.notFound().build();
            }

            Image savedImage = professorService.uploadImage(file, professorService.findById(id));

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Imagem enviada com sucesso!");
            response.put("id", savedImage.getId());
            response.put("name", savedImage.getName());
            response.put("type", savedImage.getType());
            response.put("size", savedImage.getImageData().length + " bytes");
            response.put("url", "/api/images/download/" + savedImage.getId());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "error", "Erro ao processar a imagem: " + e.getMessage()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "error", "Erro inesperado: " + e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Administração

    @GetMapping
    public ResponseEntity<List<AdministracaoModel>> listarTodosAdms(){
        return ResponseEntity.ok(administracaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministracaoModel> listarAdmPeloId(@PathVariable Long id){
        return ResponseEntity.ok(administracaoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministracaoModel> atualizarAdm(@PathVariable Long id, @RequestBody AdministracaoModel
                                                    administracaoModel) {
        administracaoModel.setId(id);
        AdministracaoModel newAdm = administracaoService.update(administracaoModel);
        return ResponseEntity.ok(newAdm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdm(@PathVariable Long id) {
        administracaoService.deletarAdm(administracaoService.findById(id));
        return ResponseEntity.noContent().build();
    }

    //Registro

    @PostMapping("/register/aluno")
    public ResponseEntity registerAluno(@RequestBody RegisterAlunoDTO data){
        try {
            // Verifica se email já existe como aluno
            if(alunoService.findByEmail(data.email()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como aluno");
            }

            // Verifica se email já existe como professor (opcional - para evitar conflito)
            if(professorService.findByEmail(data.email()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como professor. Use outro email.");
            }

            if(! data.email().contains("@")){
                return ResponseEntity.badRequest().build();
            }

            String senhaCripto = new BCryptPasswordEncoder().encode(data.senha());
            AlunoModel aluno = new AlunoModel(data.nome(), data.turma(), senhaCripto, data.email());
            alunoService.create(aluno);

            return ResponseEntity.status(201).body("Aluno registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar aluno: " + e.getMessage());
        }
    }

    @PostMapping("/register/professor")
    public ResponseEntity registerProfessor(@RequestBody RegisterProfessorDTO data){
        try {
            if(professorService.findByEmail(data.email()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como professor");
            }

            if(alunoService.findByEmail(data.email()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como aluno. Use outro email.");
            }

            if (! data.email().contains("@")){
                return ResponseEntity.badRequest().body("Email invalido");
            }

            String senhaCripto = new BCryptPasswordEncoder().encode(data.senha());
            ProfessorModel professor = new ProfessorModel(
                    data.nome(),
                    data.materia(),
                    senhaCripto,
                    data.email());
            professorService.create(professor, data.turmas(), data.file());
            ProfessorModel professorSalvo = professorService.findProfessorModelByEmail(data.email());
            return ResponseEntity.status(201).body("Professor registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar professor: " + e.getMessage());
        }
    }

    @PostMapping("/register/adm")
    public ResponseEntity registerAdm(@RequestBody RegisterAdmDTO data){
        try {
            // Verifica se email já existe como aluno
            if(alunoService.findByEmail(data.login()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como aluno");
            }

            // Verifica se email já existe como professor (opcional - para evitar conflito)
            if(professorService.findByEmail(data.login()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado como professor. Use outro email.");
            }

            if(! data.login().contains("@")){
                return ResponseEntity.badRequest().build();
            }

            String senhaCripto = new BCryptPasswordEncoder().encode(data.senha());
            AdministracaoModel newAdm = new AdministracaoModel(senhaCripto, data.login());
            newAdm = administracaoService.creat(newAdm);

            return ResponseEntity.status(201).body("Adm registrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar adm: " + e.getMessage());
        }
    }

    //Notificações


    @GetMapping("/notificacao")
    public ResponseEntity<List<NotificacaoModel>> listarTodasNotificacoes(){
        return ResponseEntity.ok(notificacaoService.findAll());
    }

    @PostMapping("/notificacao/adicionar/{id}")
    public ResponseEntity adicionarNovoUsuario(@PathVariable Long id){
        try {
            NotificacaoModel notificao = this.notificacaoService.findNewUserByIdDeReferencia(id);
            UserModel usuario = this.userService.findById(notificao.getIdDeReferencia());
            if (usuario.getRole().equalsIgnoreCase("aluno")){
                ResponseEntity entity = registerAluno(new RegisterAlunoDTO(usuario.getNome(), usuario.getTurmas().get(0), usuario.getSenha(), usuario.getEmail()));
                this.userService.delete(usuario.getId());
                this.notificacaoService.delete(notificao.getId());
                return entity;
            } else if (usuario.getRole().equalsIgnoreCase("professor")) {
                ResponseEntity entity = registerProfessor(new RegisterProfessorDTO(usuario.getNome(), usuario.getMateria(), usuario.getSenha(), usuario.getEmail(),usuario.getTurmas(), null ));
                this.userService.delete(usuario.getId());
                this.notificacaoService.delete(notificao.getId());
                return entity;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return null;
    }
}
