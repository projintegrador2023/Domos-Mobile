package camila.davi.isabelly.yasmin.domos.model;

public class CadastroUsuarioViewModel {
    private String nome, cpf, email, senha, codigo, nApto, divisao;

    public CadastroUsuarioViewModel(String nome, String cpf, String email, String senha, String codigo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.codigo = codigo;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getnApto() {
        return nApto;
    }

    public void setnApto(String nApto) {
        this.nApto = nApto;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }
}
