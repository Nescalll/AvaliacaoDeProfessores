package dm.java10x.AvaliacaoDeProfessores.Controler;

import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.service.AulaService;
import dm.java10x.AvaliacaoDeProfessores.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professor")
public class ProfessorControler {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private ProfessorService professorService;


    @GetMapping("/buscar/{email}")
    public ResponseEntity<ProfessorModel> buscarPorEmail(@PathVariable String email) {
        ProfessorModel professor = professorService.findProfessorModelByEmail(email);
        if (professor != null) {
            return ResponseEntity.ok(professor);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/media/{id}")
    public ResponseEntity<Integer> buscarMediaPorId(@PathVariable Long id){
        aulaService.deletarAulasVencidas();
        Integer media = professorService.mediaDoProfessorPorId(id);
        return ResponseEntity.ok(media);
    }

    @GetMapping("/comentario/{id}")
    public ResponseEntity<?> buscarComentarioPorId(@PathVariable Long id){
        Map<Long, String> comentarios = professorService.buscarComentariosPeloId(id);
        return ResponseEntity.ok().body(comentarios);
    }

    @PostMapping("/comentario/{id}")
    public ResponseEntity<?> denunciarComentarioPorId(@PathVariable Long id){
        professorService.reportarComentariosPeloId(id);
        return ResponseEntity.ok().body("Comentario denunciado para os adiministradores");
    }
}
