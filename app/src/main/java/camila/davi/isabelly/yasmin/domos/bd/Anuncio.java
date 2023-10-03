package camila.davi.isabelly.yasmin.domos.bd;

public class Anuncio extends Postagem{
    int codigoTag;

    public Anuncio(int codigoPostagem, String dataHoraPostagem, String descricao, String titulo, Usuario usuario, Imagem imagem, int codigoTag) {
        super(codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, imagem);
        this.codigoTag = codigoTag;
    }
}
