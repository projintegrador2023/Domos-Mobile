package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.fragment.CadastroUsuario1Fragment;
import camila.davi.isabelly.yasmin.domos.fragment.CadastroUsuario2Fragment;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;
import camila.davi.isabelly.yasmin.domos.model.LoginViewModel;
import camila.davi.isabelly.yasmin.domos.util.Config;

public class CadastroUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        CadastroUsuario1Fragment cadastroUsuario1Fragment = CadastroUsuario1Fragment.newInstance();
        setFragment(cadastroUsuario1Fragment);

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainerCadastro, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setCadastroUsuario2Fragment(Usuario usuario){
        CadastroUsuario2Fragment cadastroUsuario2Fragment = CadastroUsuario2Fragment.newInstance(usuario);
        setFragment(cadastroUsuario2Fragment);
    }

    public void cadastrar(String cpf, String nome, String email, String senha, String codigoCondominio, String nApto, String divisao) {
        CadastroUsuarioViewModel cadastroUsuarioViewModel = new ViewModelProvider(this).get(CadastroUsuarioViewModel.class);

        LiveData<Boolean> resultLD = cadastroUsuarioViewModel.cadastro(cpf, nome, email, senha, codigoCondominio, nApto, divisao);

        resultLD.observe(CadastroUsuarioActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean) {

                    // guarda os dados de login e senha dentro da app
                    Config.setLogin(CadastroUsuarioActivity.this, cpf);
                    Config.setPassword(CadastroUsuarioActivity.this, senha);

                    // exibe uma mensagem indicando que o login deu certo
                    Toast.makeText(CadastroUsuarioActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();

                    // Navega para tela principal
                    Intent i = new Intent(CadastroUsuarioActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else {

                    Toast.makeText(CadastroUsuarioActivity.this, "Não foi possível realizar o cadastro", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
