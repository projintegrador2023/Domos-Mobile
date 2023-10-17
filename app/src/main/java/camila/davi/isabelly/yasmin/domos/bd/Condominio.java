package camila.davi.isabelly.yasmin.domos.bd;

public class Condominio {
    int qtdDivisoes, codigoFaixaMoradores, codigoTipoMoradia;
    String cnpj, nome, codigoCondominio, email;
    Regimento regimento;

    public Condominio(int qtdDivisoes, int codigoFaixaMoradores, int codigoTipoMoradia, String cnpj, String nome, String codigoCondominio, String email, Regimento regimento) {
        this.qtdDivisoes = qtdDivisoes;
        this.codigoFaixaMoradores = codigoFaixaMoradores;
        this.codigoTipoMoradia = codigoTipoMoradia;
        this.cnpj = cnpj;
        this.nome = nome;
        this.codigoCondominio = codigoCondominio;
        this.email = email;
        this.regimento = regimento;
    }
}
