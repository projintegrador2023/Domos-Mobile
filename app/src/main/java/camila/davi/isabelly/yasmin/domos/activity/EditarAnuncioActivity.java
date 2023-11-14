package camila.davi.isabelly.yasmin.domos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import camila.davi.isabelly.yasmin.domos.R;

public class EditarAnuncioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);

        Button btnUpImgEditarAnuncio = findViewById(R.id.btnUpImgEditarAnuncio);

        btnUpImgEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnPublicarEditarAnuncio = findViewById(R.id.btnPublicarEditarAnuncio);

        btnPublicarEditarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditarAnuncioActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
