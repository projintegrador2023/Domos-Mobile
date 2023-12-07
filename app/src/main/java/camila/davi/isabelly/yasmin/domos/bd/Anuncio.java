package camila.davi.isabelly.yasmin.domos.bd;

public class Anuncio{
    public String codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, img, num, divisao;
    public int tag;


    public Anuncio(String codigoPostagem, String dataHoraPostagem, String descricao, String titulo, String usuario, String img, int tag) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.tag = tag;
        this.img = img;
    }

    public Anuncio(String codigoPostagem, String dataHoraPostagem, String descricao, String titulo, String usuario, String img, int tag, String num, String divisao) {
        this.codigoPostagem = codigoPostagem;
        this.dataHoraPostagem = dataHoraPostagem;
        this.descricao = descricao;
        this.titulo = titulo;
        this.usuario = usuario;
        this.tag = tag;
        this.img = img;
        this.num = num;
        this.divisao = divisao;
    }
}
