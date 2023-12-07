package camila.davi.isabelly.yasmin.domos.bd;

public class Aviso {
    public String codigoPostagem, usuario, dataHoraPostagem, descricao, titulo;
    public int importancia;

    public Aviso(String codigoPostagem, int importancia, String dataHoraPostagem, String descricao, String titulo, String usuario) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.importancia = importancia;
    }
}
