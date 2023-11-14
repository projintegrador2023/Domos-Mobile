package camila.davi.isabelly.yasmin.domos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import camila.davi.isabelly.yasmin.domos.R;

public class CriarAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);

        TextView etTituloEditarAnuncio = findViewById(R.id.etTitCriarAnuncio);
        TextView etDescricaoEditarAnuncio = findViewById(R.id.etDescCriarAnuncio);
        // chamar tags

        Button btnUploadImgEditarAnuncio = findViewById(R.id.btnUploadImgEditarAviso);

        btnUploadImgEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnPublicarEditarAnuncio = findViewById(R.id.btnPublicarEditarAnuncio);

        btnPublicarEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CriarAnuncioActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
