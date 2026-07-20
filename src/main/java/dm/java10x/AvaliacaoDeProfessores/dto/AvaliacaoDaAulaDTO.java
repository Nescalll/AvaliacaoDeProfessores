package dm.java10x.AvaliacaoDeProfessores.dto;

import dm.java10x.AvaliacaoDeProfessores.enumeradores.Adjetivo;
import dm.java10x.AvaliacaoDeProfessores.enumeradores.Melhorias;

public record AvaliacaoDaAulaDTO(String email, Long id_professor, Adjetivo  adjetivo, Integer nota, Melhorias melhorias) {
}
