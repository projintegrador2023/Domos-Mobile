package camila.davi.isabelly.yasmin.domos.bd;

public class Usuario {
    public String cpf, nome, email, num_moradia, divisao, codigoCondominio, senha, imagem;

    public Usuario(String cpf, String nome, String email, String num_moradia, String divisao, String codigo_condominio, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.num_moradia = num_moradia;
        this.divisao = divisao;
        this.codigoCondominio = codigo_condominio;
        this.senha = senha;

    }

    public Usuario(String cpf, String nome, String email, String num_moradia, String divisao, String imagem) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.num_moradia = num_moradia;
        this.divisao = divisao;
        this.imagem = imagem;

    }
}
