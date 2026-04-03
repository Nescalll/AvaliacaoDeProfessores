package dm.java10x.AvaliacaoDeProfessores.enumeradores;

public enum Adjetivo {


    OTIMO(1, "ótimo"),
    BOM(2, "bom"),
    MEDIO(3, "médio"),
    RUIM(4, "ruim"),
    PESSIMO(5, "péssimo");


    private final int codigo;
    private final String descricao;

    Adjetivo (int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Adjetivo fromCodigo(int codigo) {
        for (Adjetivo a : Adjetivo.values()) {
            if (a.codigo == codigo) {
                return a;
            }
        }
        return null; // ou lançar uma exceção
    }
}
