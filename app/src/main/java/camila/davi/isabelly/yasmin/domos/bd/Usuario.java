package camila.davi.isabelly.yasmin.domos.bd;

public class Usuario {
    public String cpf, nome, email, senha, codigoCondominio;
    public int codigoNivelPermissao;
    public Moradia moradia;
    public Imagem imagem;

    public Usuario(String cpf, String nome, String email, String senha, String codigoCondominio) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.codigoCondominio = codigoCondominio;
    }
}
