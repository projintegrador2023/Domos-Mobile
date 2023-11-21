package camila.davi.isabelly.yasmin.domos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import camila.davi.isabelly.yasmin.domos.R;

public class RecuperaSenhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        TextView tvEmailSenha = findViewById(R.id.tvEmailSenha);

        Button btEnviarRecSenha = findViewById(R.id.btEnviarRecSenha);
        btEnviarRecSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmailSenha.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(RecuperaSenhaActivity.this, "É necessário inserir um email",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }

        });
    }

}
