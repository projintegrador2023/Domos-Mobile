package camila.davi.isabelly.yasmin.domos.model;

public class CadastroUsuarioViewModel {
    String nome, cpf, email, senha, confirmarSenha, codigo, nApto, divisao;

    public CadastroUsuarioViewModel(String nome, String cpf, String email, String senha, String confirmarSenha, String codigo, String nApto, String divisao) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.codigo = codigo;
        this.nApto = nApto;
        this.divisao = divisao;
    }


}
