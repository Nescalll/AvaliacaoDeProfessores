package dm.java10x.AvaliacaoDeProfessores.Controler;


import dm.java10x.AvaliacaoDeProfessores.dto.ProfessorUpdateDTO;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.service.AlunoService;
import dm.java10x.AvaliacaoDeProfessores.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

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
}
