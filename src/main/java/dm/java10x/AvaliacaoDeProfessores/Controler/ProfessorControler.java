package dm.java10x.AvaliacaoDeProfessores.Controler;

import dm.java10x.AvaliacaoDeProfessores.dto.ProfessorUpdateDTO;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import dm.java10x.AvaliacaoDeProfessores.model.abstracte.Image;
import dm.java10x.AvaliacaoDeProfessores.model.entity.ProfessorModel;
import dm.java10x.AvaliacaoDeProfessores.service.AlunoService;
import dm.java10x.AvaliacaoDeProfessores.service.AulaService;
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
@RequestMapping("/professor")
public class ProfessorControler {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private AlunoService alunoService;

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

    @GetMapping("/adjetivo/{id}")
    public ResponseEntity<?> buscarAdjetivoPorId(@PathVariable Long id){
        aulaService.deletarAulasVencidas();
        Map<Adjetivo, Integer> adjetivo = professorService.adjetivos(id);
        return ResponseEntity.ok(adjetivo);
    }

    @GetMapping("/melhoria/{id}")
    public ResponseEntity<?> buscarMelhoriaPorId(@PathVariable Long id){
        aulaService.deletarAulasVencidas();
        Map<Adjetivo, Integer> adjetivo = professorService.adjetivos(id);
        return ResponseEntity.ok(adjetivo);
    }
}
