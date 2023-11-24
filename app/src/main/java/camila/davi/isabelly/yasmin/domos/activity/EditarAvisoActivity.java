package camila.davi.isabelly.yasmin.domos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.model.CriarAvisoViewModel;
import camila.davi.isabelly.yasmin.domos.model.EditarAvisoViewModel;

public class EditarAvisoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_aviso);

        EditarAvisoViewModel editarAvisoViewModel = new ViewModelProvider(this).get(EditarAvisoViewModel.class);

        TextView etTitEditarAviso = findViewById(R.id.etTitEditarAviso);
        TextView etDescEditarAviso = findViewById(R.id.etDescEditarAviso);
        Spinner spTagEditarAviso = findViewById(R.id.spTagEditarAviso);

        Button btnPubliEditarAviso = findViewById(R.id.btnPubliEditarAviso);

        btnPubliEditarAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                String titulo = etTitEditarAviso.getText().toString();
                if (titulo.isEmpty()) {
                    Toast.makeText(EditarAvisoActivity.this, "É necessário inserir um título",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String descricao = etDescEditarAviso.getText().toString();
                if (descricao.isEmpty()) {
                    Toast.makeText(EditarAvisoActivity.this, "É necessário inserir uma descrição",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String importancia = spTagEditarAviso.getSelectedItem().toString();
                if (importancia.isEmpty()) {
                    Toast.makeText(EditarAvisoActivity.this, "É necessário escolher uma importância",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(EditarAvisoActivity.this, HomeActivity.class);
                startActivity(i);

                LiveData<Boolean> resultLD = editarAvisoViewModel.editarAviso(titulo, importancia, descricao);


                resultLD.observe(EditarAvisoActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        if(aBoolean == true) {
                            Toast.makeText(EditarAvisoActivity.this, "Aviso editado com sucesso", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                            v.setEnabled(true);
                            Toast.makeText(EditarAvisoActivity.this, "Ocorreu um erro ao editar o aviso", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }



        });
    }
}