package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import camila.davi.isabelly.yasmin.domos.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView etCpfSenha = findViewById(R.id.etCpfSenha);
        TextView tvCadastraUsuario = findViewById(R.id.tvCadastraUsuario);
        TextView tvEsqueceuSenha = findViewById(R.id.tvEsqueceuSenha);
        tvCadastraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(i);
            }
        });

        tvEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RecuperaSenhaActivity.class);
                startActivity(i);
            }
        });
    }
}