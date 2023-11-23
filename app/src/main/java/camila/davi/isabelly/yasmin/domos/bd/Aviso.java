package camila.davi.isabelly.yasmin.domos.bd;

public class Aviso {

    int codigoPostagem, importancia, usuario;
    String dataHoraPostagem, descricao, titulo;

    public Aviso(int codigoPostagem, int importancia, String dataHoraPostagem, String descricao, String titulo, int usuario) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.importancia = importancia;
    }
}
