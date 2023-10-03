package camila.davi.isabelly.yasmin.domos.bd;

public class Aviso extends Postagem{
    int codigoImportanciaAviso;

    public Aviso(int codigoPostagem, String dataHoraPostagem, String descricao, String titulo, Usuario usuario, Imagem imagem, int codigoImportanciaAviso) {
        super(codigoPostagem, dataHoraPostagem, descricao, titulo, usuario, imagem);
        this.codigoImportanciaAviso = codigoImportanciaAviso;
    }
}
