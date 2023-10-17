package camila.davi.isabelly.yasmin.domos.bd;

public class Moradia {
    int codigo;
    Divisao divisao;
    String numeroMoradia;

    public Moradia(int codigo, Divisao divisao, String numeroMoradia) {
        this.codigo = codigo;
        this.divisao = divisao;
        this.numeroMoradia = numeroMoradia;
    }
}
