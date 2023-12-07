package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;
import camila.davi.isabelly.yasmin.domos.model.PerfilViewModel;

public class EditarPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        TextView etNomeEditar = findViewById(R.id.etNomeEditar);
        TextView etCpfEditar = findViewById(R.id.etCpfEditar);
        TextView etEmailCadastro = findViewById(R.id.etEmailCadastro);
        TextView etSenhaEditar = findViewById(R.id.etSenhaEditar);
        Spinner spNum = findViewById(R.id.spNum);
        Spinner spDivisao = findViewById(R.id.spDivisao);

        PerfilViewModel perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        LiveData<Usuario> resultLD = perfilViewModel.loadPerfil();


        CadastroUsuarioViewModel cadastroUsuarioViewModel = new ViewModelProvider(this).get(CadastroUsuarioViewModel.class);
        resultLD.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                if(usuario != null) {
                    etNomeEditar.setText(usuario.nome);
                    etCpfEditar.setText(usuario.cpf);
                    etEmailCadastro.setText(usuario.email);

                    LiveData<NumDivCondominio> resultLD1 = cadastroUsuarioViewModel.pegarNumDiv(usuario.codigoCondominio);

                    resultLD1.observe(EditarPerfilActivity.this, new Observer<NumDivCondominio>() {
                        @Override
                        public void onChanged(NumDivCondominio numDivCondominio) {

                            if(numDivCondominio != null) {
                                ArrayAdapter adapterNums = new ArrayAdapter<String>(EditarPerfilActivity.this, android.R.layout.simple_spinner_item, numDivCondominio.getNumeros());
                                spNum.setAdapter(adapterNums);

                                ArrayAdapter adapterDiv = new ArrayAdapter<String>(EditarPerfilActivity.this,android.R.layout.simple_spinner_item, numDivCondominio.getDivisoes());
                                spDivisao.setAdapter(adapterDiv);


                            }
                        }
                    });


                }
            }
        });



        Button btnSalvarEditar = findViewById(R.id.btnSalvarEditar);
        btnSalvarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeEditar.getText().toString();
                if (nome.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir um nome",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String cpf = etCpfEditar.getText().toString();
                if (cpf.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir um cpf",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String tag = etEmailCadastro.getText().toString();
                if (tag.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir email",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String senha = etSenhaEditar.getText().toString();
                if (senha.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir uma senha",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Button btnAlterarImagem = findViewById(R.id.btnAlterarImagem);

        btnAlterarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}