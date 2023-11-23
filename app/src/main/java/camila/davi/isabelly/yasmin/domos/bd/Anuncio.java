package camila.davi.isabelly.yasmin.domos.bd;

public class Anuncio{
    int tag, codigoPostagem;
    String dataHoraPostagem, descricao, titulo;
    Usuario usuario;
    Imagem img;


    public Anuncio(int codigoPostagem, String dataHoraPostagem, String descricao, String titulo, Usuario usuario, Imagem img, int tag) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.tag = tag;
        this.img = img;
    }
}
