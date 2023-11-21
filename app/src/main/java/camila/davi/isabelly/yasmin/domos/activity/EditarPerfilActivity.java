package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;

public class EditarPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        TextView etNomeEditar = findViewById(R.id.etNomeEditar);
        TextView etCpfEditar = findViewById(R.id.etCpfEditar);
        TextView etEmailCadastro = findViewById(R.id.etEmailCadastro);

        TextView etSenhaEditar = findViewById(R.id.etSenhaEditar);


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