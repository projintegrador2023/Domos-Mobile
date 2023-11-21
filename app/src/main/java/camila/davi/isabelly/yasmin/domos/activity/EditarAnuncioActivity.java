package camila.davi.isabelly.yasmin.domos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import camila.davi.isabelly.yasmin.domos.R;

public class EditarAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);

        TextView etTitEditarAnuncio = findViewById(R.id.etTitEditarAnuncio);
        TextView etDescEditarAnuncio = findViewById(R.id.etDescEditarAnuncio);
        Spinner spTagEditarAnuncio = findViewById(R.id.spTagsEditarAnuncio);

        Button btnUpImgEditarAnuncio = findViewById(R.id.btnUpImgEditarAnuncio);

        btnUpImgEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnPublicarEditarAnuncio = findViewById(R.id.btnPubliEditarAnuncio);

        btnPublicarEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitEditarAnuncio.getText().toString();
                if (titulo.isEmpty()) {
                    Toast.makeText(EditarAnuncioActivity.this, "É necessário inserir um título",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String descricao = etDescEditarAnuncio.getText().toString();
                if (descricao.isEmpty()) {
                    Toast.makeText(EditarAnuncioActivity.this, "É necessário inserir uma descrição",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String tag = spTagEditarAnuncio.getSelectedItem().toString();
                if (tag.isEmpty()) {
                    Toast.makeText(EditarAnuncioActivity.this, "É necessário escolher uma tag",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(EditarAnuncioActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
