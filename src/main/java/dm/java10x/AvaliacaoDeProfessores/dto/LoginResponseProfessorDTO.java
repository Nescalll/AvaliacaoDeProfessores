package dm.java10x.AvaliacaoDeProfessores.dto;

public record LoginResponseProfessorDTO(String token, String tipo, String nome, String email, String materia) {
}
