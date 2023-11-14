package camila.davi.isabelly.yasmin.domos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import camila.davi.isabelly.yasmin.domos.activity.CriarAnuncioActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;

public class CriarAvisoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_aviso);

        Button btnUploadImgEditarAviso = findViewById(R.id.btnUploadImgEditarAviso);

        btnUploadImgEditarAviso.setOnClickListener(new View.OnClickListener() {
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