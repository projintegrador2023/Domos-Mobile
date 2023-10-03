package camila.davi.isabelly.yasmin.domos.bd;

public class Postagem {
    int codigoPostagem;
    String dataHoraPostagem, descricao, titulo;
    Usuario usuario;
    Imagem imagem;

    public Postagem(int codigoPostagem, String dataHoraPostagem, String descricao, String titulo, Usuario usuario, Imagem imagem) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.imagem = imagem;
    }
}
