package camila.davi.isabelly.yasmin.domos.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.DomosRepository;
import camila.davi.isabelly.yasmin.domos.util.Util;

public class CriarAnuncioActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);


        // obtenção do ViewModel
        CriarAnuncioViewModel criarAnuncioViewModel = new ViewModelProvider(this).get(CriarAnuncioViewModel.class);

        // O ViewModel guarda o local da última foto escolhida pelo usuário.
        // Aqui, verificamos se já existe uma foto selecionada pelo usuário. Se sim, nós setamos
        // essa foto no ImageView.
        String currentPhotoPath = criarAnuncioViewModel.getCurrentPhotoPath();
        if(!currentPhotoPath.isEmpty()) {
            ImageView imvPhoto = findViewById(R.id.btnUpImgCriarAnuncio);
            // aqui carregamos a foto que está guardada dentro do arquivo currentPhotoPath dentro
            // de um objeto do tipo Bitmap. A imagem é carregada e sofre uma escala pra ficar
            // exatamente do tamanho do ImageView
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
            imvPhoto.setImageBitmap(bitmap);
        }

        TextView etTituloCriarAnuncio = findViewById(R.id.etTitCriarAnuncio);
        TextView etDescCriarAnuncio = findViewById(R.id.etDescCriarAnuncio);
        Spinner spTagCriarAnuncio = findViewById(R.id.spTagCriarAnuncio);
        Button btnPublicarCriarAnuncio = findViewById(R.id.btnPublicarCriarAnuncio);

        btnPublicarCriarAnuncio.setEnabled(false);

        LiveData<List<String>> resultLD = criarAnuncioViewModel.pegarTags();

        resultLD.observe(CriarAnuncioActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> listaTag) {

                if(listaTag != null) {
                    ArrayAdapter adapterTag = new ArrayAdapter<String>(CriarAnuncioActivity.this,android.R.layout.simple_spinner_item, listaTag);
                    spTagCriarAnuncio.setAdapter(adapterTag);

                    btnPublicarCriarAnuncio.setEnabled(true);


                }
            }
        });


        ImageButton btnUpImgCriarAnuncio = findViewById(R.id.btnUpImgCriarAnuncio);
        // define a ação que será feita quando o botão for acionado
        btnUpImgCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchGalleryOrCameraIntent();
            }
        });

        btnPublicarCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                String titulo = etTituloCriarAnuncio.getText().toString();
                if (titulo.isEmpty()) {
                    Toast.makeText(CriarAnuncioActivity.this, "É necessário inserir um título",
                            Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }
                String descricao = etDescCriarAnuncio.getText().toString();
                if (descricao.isEmpty()) {
                    Toast.makeText(CriarAnuncioActivity.this, "É necessário inserir uma descrição",
                            Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }
                String tag = spTagCriarAnuncio.getSelectedItem().toString();
                if (tag.isEmpty()) {
                    Toast.makeText(CriarAnuncioActivity.this, "É necessário escolher uma tag",
                            Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                String currentPhotoPath = criarAnuncioViewModel.getCurrentPhotoPath();
                if(currentPhotoPath.isEmpty()) {
                    Toast.makeText(CriarAnuncioActivity.this, "O campo Foto do Produto não foi preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                // Neste ponto, já verificamos que todos os campos foram preenchidos corretamente.
                // Antes enviar esses dados ao servidor, nós fazemos uma escala na imagem escolhida
                // para o produto. Fazemos isso porque a câmera do celular produz imagens muito grandes,
                // com resolução muito mais alta do que aquela que realmente precisamos. Logo, na
                // prática, o que fazemos aqui é diminuir o tamanho da imagem antes de enviá-la ao
                // servidor. Isso garante que será usado menos recurso de rede e de banco de dados
                // no servidor.
                //
                // A imagem é escalada de forma que sua altura fique em 300dp (tamanho do ImageView
                // que exibe os detalhes de um produto. A largura vai possuir
                // um tamanho proporcional ao tamamnho original.
                try {
                    int h = (int) getResources().getDimension(R.dimen.img_height);
                    Util.scaleImage(currentPhotoPath, -1, 2*h);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                // O ViewModel possui o método addProduct, que envia os dados do novo produto para o
                // servidor web.O servidor web recebe esses dados e cadastra um novo produto. Se o
                // produto foi cadastrado com sucesso, a app recebe o valor true. Se não o servidor
                // retorna o valor false.
                //
                // O método de addProduct retorna um LiveData, que na prática é um container que avisa
                // quando o resultado do servidor chegou.
                LiveData<Boolean> resultLD = criarAnuncioViewModel.criarAnuncio(titulo, tag, descricao, currentPhotoPath);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o cadastro do produto deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(CriarAnuncioActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true) {
                            Toast.makeText(CriarAnuncioActivity.this, "Anuncio criado com sucesso", Toast.LENGTH_LONG).show();
                            // indica que a Activity terminou com resultado positivo e a finaliza
                            setResult(RESULT_OK);
                            finish();

                            Intent i = new Intent(CriarAnuncioActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        else {
                            v.setEnabled(true);
                            Toast.makeText(CriarAnuncioActivity.this, "Ocorreu um erro ao criar o anuncio", Toast.LENGTH_LONG).show();

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
    private void dispatchGalleryOrCameraIntent() {

        // Primeiro, criamos o arquivo que irá guardar a imagem.
        File f = null;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(CriarAnuncioActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Se o arquivo foi criado com sucesso...
        if(f != null) {

            // setamos o endereço do arquivo criado dentro do ViewModel
            CriarAnuncioViewModel addProductViewModel = new ViewModelProvider(this).get(CriarAnuncioViewModel.class);
            addProductViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            // Criamos e configuramos o INTENT que dispara a câmera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(CriarAnuncioActivity.this, "camila.davi.isabelly.yasmin.domos.fileprovider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            // Criamos e configuramos o INTENT que dispara a escolha de imagem via galeria
            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            // Criamos o INTENT que gera o menu de escolha. Esse INTENT contém os dois INTENTS
            // anteriores e permite que o usuário esolha entre câmera e galeria de fotos.
            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);
        }
        else {
            Toast.makeText(CriarAnuncioActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }


    }

    /**
     * Método que cria um arquivo vazio, onde será guardada a imagem escolhida. O arquivo é
     * criado dentro do espaço interno da app, no diretório PICTURES. O nome do arquivo usa a
     * data e hora do momento da criação do arquivo. Isso garante que sempre que esse método for
     * chamado, não haverá risco de sobrescrever o arquivo anterior.
     */
    private File createImageFile() throws IOException {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TAKE_PICTURE) {

            // Pegamos o endereço do arquivo vazio que foi criado para guardar a foto escolhida
            CriarAnuncioViewModel criarAnuncioViewModel = new ViewModelProvider(this).get(CriarAnuncioViewModel.class);
            String currentPhotoPath = criarAnuncioViewModel.getCurrentPhotoPath();

            // Se a foto foi efetivamente escolhida pelo usuário...
            if(resultCode == Activity.RESULT_OK) {
                ImageButton imvPhoto = findViewById(R.id.btnUpImgCriarAnuncio);

                // se o usuário escolheu a câmera, então quando esse método é chamado, a foto tirada
                // já está salva dentro do arquivo currentPhotoPath. Entretanto, se o usuário
                // escolheu uma foto da galeria, temos que obter o URI da foto escolhida:
                Uri selectedPhoto = data.getData();
                if(selectedPhoto != null) {
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
            }
            else {
                // Se a imagem não foi escolhida, deletamos o arquivo que foi criado para guardá-la
                File f = new File(currentPhotoPath);
                f.delete();
                criarAnuncioViewModel.setCurrentPhotoPath("");
            }
        }
    }
}
