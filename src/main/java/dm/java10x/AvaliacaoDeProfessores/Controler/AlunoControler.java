
package dm.java10x.AvaliacaoDeProfessores.Controler;



import dm.java10x.AvaliacaoDeProfessores.dto.AvaliacaoDaAulaDTO;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AlunoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.AvaliacaoModel;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.service.AlunoService;
import dm.java10x.AvaliacaoDeProfessores.service.AulaService;
import dm.java10x.AvaliacaoDeProfessores.service.AvaliacaoService;
import dm.java10x.AvaliacaoDeProfessores.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/aluno")
public class AlunoControler {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AvaliacaoService avaliacaoService;


    @GetMapping("/aulas/{email}")
    public ResponseEntity<List<ProfessorModel>> avaliarAulas(@PathVariable String email){
        System.out.println("Requisição recebida");
        AlunoModel aluno = alunoService.findAlunoModelByEmail(email);
        System.out.println("Aluno encontrado");
        List<ProfessorModel> professoresQueEnsinamMesmaTurma = professorService.filtrarPorTurma(aluno.getTurma());
        System.out.println("Professores pela turma recebido");
        List<ProfessorModel> professoresFiltrados = professorService.filtrarProfessoresNaoAvaliadosEstaSemana(professoresQueEnsinamMesmaTurma, aluno);
        System.out.println("Professores filtrados");
        System.out.println("Enviando");
        return ResponseEntity.ok(professoresFiltrados);
    }
    @PostMapping("/aula")
    public ResponseEntity<?> avaliarAula(@RequestBody AvaliacaoDaAulaDTO aulaDTO){
        AlunoModel aluno = alunoService.findAlunoModelByEmail(aulaDTO.email());
        AulaModel aula = new AulaModel( aulaDTO.nota(), null, null);
        if (aulaDTO.comentario().isPresent()){
            if (aulaService.validarComentario(aulaDTO.comentario().get())){
            aula.setComentario(aulaDTO.comentario().get());
            } else {
                return ResponseEntity.badRequest().body("Palavras inapropriadas");
            }
        }
        AulaModel obj = this.aulaService.create(aula);
        AvaliacaoModel avaliacao = this.avaliacaoService.creat(aluno.getId(), aulaDTO.id_professor(), obj.getId());
        return ResponseEntity.ok(obj);
    }
}