package dm.java10x.AvaliacaoDeProfessores.Controler;


import dm.java10x.AvaliacaoDeProfessores.model.AulaModel;
import dm.java10x.AvaliacaoDeProfessores.model.AvaliacaoModel;
import dm.java10x.AvaliacaoDeProfessores.service.AulaService;
import dm.java10x.AvaliacaoDeProfessores.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aula")
public class AulaControler {

    @Autowired
    private AulaService aulaService;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    public ResponseEntity<List<AulaModel>> listarTodos() {
        List<AulaModel> aulaModels = aulaService.findAll();
        return ResponseEntity.ok(aulaModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaModel> buscarPorId(@PathVariable Long id) {
        AulaModel aulaModel= aulaService.findById(id);
        return ResponseEntity.ok(aulaModel);
    }

    @PostMapping("/{id_aluno}/{id_professor}")
    public ResponseEntity<AulaModel> criar(@PathVariable Long id_aluno, @PathVariable Long id_professor,  @RequestBody AulaModel aulaModel) {
        AulaModel aulaCriada = aulaService.create(aulaModel);
        AvaliacaoModel avaliacaoModel = avaliacaoService.creat(id_aluno, id_professor, aulaCriada.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(aulaCriada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aulaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
