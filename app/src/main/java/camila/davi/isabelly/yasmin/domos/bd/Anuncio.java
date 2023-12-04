package camila.davi.isabelly.yasmin.domos.bd;

public class Anuncio{
    String tag, codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, img;


    public Anuncio(String codigoPostagem, String dataHoraPostagem, String descricao, String titulo, String usuario, String img, String tag) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.tag = tag;
        this.img = img;
    }
}
