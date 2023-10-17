package camila.davi.isabelly.yasmin.domos.bd;

public class Usuario {
    String cpf, nome, email;
    Condominio condominio;
    int codigoNivelPermissao;
    Moradia moradia;
    Imagem imagem;

    public Usuario(String cpf, String nome, String email, Condominio condominio, int codigoNivelPermissao, Moradia moradia, Imagem imagem) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.condominio = condominio;
        this.codigoNivelPermissao = codigoNivelPermissao;
        this.moradia = moradia;
        this.imagem = imagem;
    }
}
