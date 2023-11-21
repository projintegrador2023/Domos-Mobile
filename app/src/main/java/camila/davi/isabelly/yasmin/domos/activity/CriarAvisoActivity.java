package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import camila.davi.isabelly.yasmin.domos.R;

public class CriarAvisoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_aviso);

        TextView etTitCriarAviso = findViewById(R.id.etTitCriarAviso);
        TextView etDescCriarAviso = findViewById(R.id.etDescCriarAviso);
        Spinner spTagCriarAviso = findViewById(R.id.spTagCriarAviso);

        Button btnPublicarEditarAnuncio = findViewById(R.id.btnPubliAviso);

        btnPublicarEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitCriarAviso.getText().toString();
                if (titulo.isEmpty()) {
                    Toast.makeText(CriarAvisoActivity.this, "É necessário inserir um título",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String descricao = etDescCriarAviso.getText().toString();
                if (descricao.isEmpty()) {
                    Toast.makeText(CriarAvisoActivity.this, "É necessário inserir uma descrição",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String tag = spTagCriarAviso.getSelectedItem().toString();
                if (tag.isEmpty()) {
                    Toast.makeText(CriarAvisoActivity.this, "É necessário escolher uma tag",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(CriarAvisoActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}