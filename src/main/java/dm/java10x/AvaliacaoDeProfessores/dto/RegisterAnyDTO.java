package dm.java10x.AvaliacaoDeProfessores.dto;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Materia;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Turma;

import java.util.List;
import java.util.Optional;

public record RegisterAnyDTO (

        String nome,
        String email,
        String senha,
        Optional<Materia> materia,
        String role,
        List<Turma> turmas
){}
