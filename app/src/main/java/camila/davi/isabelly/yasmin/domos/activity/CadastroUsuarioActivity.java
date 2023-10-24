package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.fragment.CadastroUsuario1Fragment;
import camila.davi.isabelly.yasmin.domos.fragment.CadastroUsuario2Fragment;

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

    public void setCadastroUsuario2Fragment(){
        CadastroUsuario2Fragment cadastroUsuario2Fragment = CadastroUsuario2Fragment.newInstance();
        setFragment(cadastroUsuario2Fragment);
    }
}