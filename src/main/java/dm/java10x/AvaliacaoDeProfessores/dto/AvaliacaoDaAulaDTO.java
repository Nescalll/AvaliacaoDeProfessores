package dm.java10x.AvaliacaoDeProfessores.dto;

import java.util.Optional;

public record AvaliacaoDaAulaDTO(
        String email,
        Long id_professor,
        Integer nota,
        Optional<String> comentario) {
}
