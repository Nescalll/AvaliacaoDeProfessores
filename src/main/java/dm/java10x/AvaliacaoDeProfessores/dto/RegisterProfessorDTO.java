package dm.java10x.AvaliacaoDeProfessores.dto;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Materia;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public record RegisterProfessorDTO(
        String nome,
        Materia materia,
        String senha,
        String email,
        List<Turma> turmas,
        Optional<MultipartFile> file
) {
}