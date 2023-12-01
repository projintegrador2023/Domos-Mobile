package camila.davi.isabelly.yasmin.domos.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.CriarAvisoViewModel;
import camila.davi.isabelly.yasmin.domos.model.LoginViewModel;

public class CriarAvisoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_aviso);

        CriarAvisoViewModel criarAvisoViewModel = new ViewModelProvider(this).get(CriarAvisoViewModel.class);

        TextView etTitCriarAviso = findViewById(R.id.etTitCriarAviso);
        TextView etDescCriarAviso = findViewById(R.id.etDescCriarAviso);
        Spinner spTagCriarAviso = findViewById(R.id.spTagCriarAviso);
        Button btnPublicarEditarAviso = findViewById(R.id.btnPubliAviso);
        btnPublicarEditarAviso.setEnabled(false);

        LiveData<List<String>> resultLD = criarAvisoViewModel.pegarImportancia();

        resultLD.observe(CriarAvisoActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> listaImportancia) {

                if(listaImportancia != null) {
                    ArrayAdapter adapterImportancia = new ArrayAdapter<String>(CriarAvisoActivity.this,android.R.layout.simple_spinner_item, listaImportancia);
                    spTagCriarAviso.setAdapter(adapterImportancia);

                    btnPublicarEditarAviso.setEnabled(true);


                }
            }
        });

        btnPublicarEditarAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

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
                String importancia = spTagCriarAviso.getSelectedItem().toString();
                if (importancia.isEmpty()) {
                    Toast.makeText(CriarAvisoActivity.this, "É necessário escolher uma importância",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(CriarAvisoActivity.this, HomeActivity.class);
                startActivity(i);

                LiveData<Boolean> resultLD = criarAvisoViewModel.criarAviso(titulo, importancia, descricao);


                resultLD.observe(CriarAvisoActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        if(aBoolean == true) {
                            Toast.makeText(CriarAvisoActivity.this, "Aviso criado com sucesso", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                            v.setEnabled(true);
                            Toast.makeText(CriarAvisoActivity.this, "Ocorreu um erro ao criar o aviso", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }



        });
    }
}