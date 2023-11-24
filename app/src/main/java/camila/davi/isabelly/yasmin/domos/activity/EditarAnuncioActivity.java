package camila.davi.isabelly.yasmin.domos.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.EditarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.util.Util;

public class EditarAnuncioActivity extends AppCompatActivity {
    static int RESULT_TAKE_PICTURE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);

        EditarAnuncioViewModel editarAnuncioViewModel = new ViewModelProvider(this).get(EditarAnuncioViewModel.class);
        // O ViewModel guarda o local da última foto escolhida pelo usuário.
        // Aqui, verificamos se já existe uma foto selecionada pelo usuário. Se sim, nós setamos
        // essa foto no ImageView.
        String currentPhotoPath = editarAnuncioViewModel.getCurrentPhotoPath();
        if (!currentPhotoPath.isEmpty()) {
            ImageView imvPhoto = findViewById(R.id.btnUpImgEditarAnuncio);
            // aqui carregamos a foto que está guardada dentro do arquivo currentPhotoPath dentro
            // de um objeto do tipo Bitmap. A imagem é carregada e sofre uma escala pra ficar
            // exatamente do tamanho do ImageView
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
            imvPhoto.setImageBitmap(bitmap);
        }

        TextView etTitEditarAnuncio = findViewById(R.id.etTitEditarAnuncio);
        TextView etDescEditarAnuncio = findViewById(R.id.etDescEditarAnuncio);
        Spinner spTagEditarAnuncio = findViewById(R.id.spTagsEditarAnuncio);


        ImageButton btnUpImgCriarAnuncio = findViewById(R.id.btnUpImgEditarAnuncio);
        // define a ação que será feita quando o botão for acionado
        btnUpImgCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchGalleryOrCameraIntent();
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

                try {
                    int h = (int) getResources().getDimension(R.dimen.img_height);
                    Util.scaleImage(currentPhotoPath, -1, 2 * h);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                LiveData<Boolean> resultLD = editarAnuncioViewModel.editarAnuncio(titulo, tag, descricao, currentPhotoPath);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o cadastro do produto deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(EditarAnuncioActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        // aBoolean contém o resultado do cadastro do produto. Se aBoolean for true, significa
                        // que o cadastro do produto foi feito corretamente. Indicamos isso ao usuário
                        // através de uma mensagem do tipo toast e finalizamos a Activity. Quando
                        // finalizamos a Activity, voltamos para a tela home, que mostra a lista de
                        // produtos.
                        if (aBoolean == true) {
                            Toast.makeText(EditarAnuncioActivity.this, "Anuncio editado com sucesso", Toast.LENGTH_LONG).show();
                            // indica que a Activity terminou com resultado positivo e a finaliza
                            setResult(RESULT_OK);
                            finish();

                            Intent i = new Intent(EditarAnuncioActivity.this, HomeActivity.class);
                            startActivity(i);
                        } else {
                            // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                            // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                            // Reabilitamos também o botão de adicionar, para permitir que o usuário
                            // tente realizar uma nova adição de produto.
                            v.setEnabled(true);
                            Toast.makeText(EditarAnuncioActivity.this, "Ocorreu um erro ao editar o anuncio", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
    });
}


/**
 * Esse método exibe um pequeno menu de opções que permite que o usuário escolha de onde virá
 * a imagem do produto: câmera ou galeria.
 */
private void dispatchGalleryOrCameraIntent () {

    // Primeiro, criamos o arquivo que irá guardar a imagem.
    File f = null;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(EditarAnuncioActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Se o arquivo foi criado com sucesso...
        if (f != null) {

            // setamos o endereço do arquivo criado dentro do ViewModel
            EditarAnuncioViewModel addProductViewModel = new ViewModelProvider(this).get(EditarAnuncioViewModel.class);
            addProductViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            // Criamos e configuramos o INTENT que dispara a câmera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(EditarAnuncioActivity.this, "camila.davi.isabelly.yasmin.domos.fileprovider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            // Criamos e configuramos o INTENT que dispara a escolha de imagem via galeria
            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            // Criamos o INTENT que gera o menu de escolha. Esse INTENT contém os dois INTENTS
            // anteriores e permite que o usuário esolha entre câmera e galeria de fotos.
            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);
        } else {
            Toast.makeText(EditarAnuncioActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }


    }

    /**
     * Método que cria um arquivo vazio, onde será guardada a imagem escolhida. O arquivo é
     * criado dentro do espaço interno da app, no diretório PICTURES. O nome do arquivo usa a
     * data e hora do momento da criação do arquivo. Isso garante que sempre que esse método for
     * chamado, não haverá risco de sobrescrever o arquivo anterior.
     */
    private File createImageFile () throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    /**
     * Esse método é chamado depois que o usuário escolhe a foto
     */
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {

            // Pegamos o endereço do arquivo vazio que foi criado para guardar a foto escolhida
            EditarAnuncioViewModel editarAnuncioViewModel = new ViewModelProvider(this).get(EditarAnuncioViewModel.class);
            String currentPhotoPath = editarAnuncioViewModel.getCurrentPhotoPath();

            // Se a foto foi efetivamente escolhida pelo usuário...
            if (resultCode == Activity.RESULT_OK) {
                ImageButton imvPhoto = findViewById(R.id.btnUpImgEditarAnuncio);

                // se o usuário escolheu a câmera, então quando esse método é chamado, a foto tirada
                // já está salva dentro do arquivo currentPhotoPath. Entretanto, se o usuário
                // escolheu uma foto da galeria, temos que obter o URI da foto escolhida:
                Uri selectedPhoto = data.getData();
                if (selectedPhoto != null) {
                    try {
                        // carregamos a foto escolhida em um bitmap
                        Bitmap bitmap = Util.getBitmap(this, selectedPhoto);
                        // salvamos o bitmao dentro do arquivo currentPhotoPath
                        Util.saveImage(bitmap, currentPhotoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                // Carregamos a foto salva em currentPhotoPath com a escala correta e setamos no ImageView
                Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
                imvPhoto.setImageBitmap(bitmap);
            } else {
                // Se a imagem não foi escolhida, deletamos o arquivo que foi criado para guardá-la
                File f = new File(currentPhotoPath);
                f.delete();
                editarAnuncioViewModel.setCurrentPhotoPath("");
            }
        }
    }
}
