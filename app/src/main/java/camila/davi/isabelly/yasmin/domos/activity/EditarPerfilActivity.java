package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;
import camila.davi.isabelly.yasmin.domos.bd.Usuario;
import camila.davi.isabelly.yasmin.domos.model.CadastroUsuarioViewModel;
import camila.davi.isabelly.yasmin.domos.model.CriarAnuncioViewModel;
import camila.davi.isabelly.yasmin.domos.model.EditarPerfilViewModel;
import camila.davi.isabelly.yasmin.domos.model.PerfilViewModel;
import camila.davi.isabelly.yasmin.domos.util.ImageCache;
import camila.davi.isabelly.yasmin.domos.util.Util;

public class EditarPerfilActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        TextView etNomeEditar = findViewById(R.id.etNomeEditar);
        TextView etCpfEditar = findViewById(R.id.etCpfEditar);
        TextView etEmailCadastro = findViewById(R.id.etEmailCadastro);
        TextView etSenhaEditar = findViewById(R.id.etSenhaEditar);

        PerfilViewModel perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        LiveData<Usuario> resultLD = perfilViewModel.loadPerfil();

        resultLD.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {

                if(usuario != null) {
                    etNomeEditar.setText(usuario.nome);
                    etCpfEditar.setText(usuario.cpf);
                    etEmailCadastro.setText(usuario.email);

                    if (usuario.imagem != null) {
                        int h = (int) EditarPerfilActivity.this.getResources().getDimension(R.dimen.img_height_anuncio);
                        ImageView imgPerfil = findViewById(R.id.imbEditarPerfil);
                        ImageCache.loadImageUrlToImageView(EditarPerfilActivity.this, usuario.imagem, imgPerfil, -1, h);
                    }

                }
            }
        });

        ImageButton btnAlterarImagem = findViewById(R.id.imbEditarPerfil);

        btnAlterarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryOrCameraIntent();
            }
        });

        Button btnSalvarEditar = findViewById(R.id.btnSalvarEditar);
        btnSalvarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeEditar.getText().toString();
                if (nome.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir um nome",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String cpf = etCpfEditar.getText().toString();
                if (cpf.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir um cpf",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String email = etEmailCadastro.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir email",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                String senha = etSenhaEditar.getText().toString();
                if (senha.isEmpty()) {
                    Toast.makeText(EditarPerfilActivity.this, "É necessário inserir uma senha",
                            Toast.LENGTH_LONG).show();
                    return;
                }



                EditarPerfilViewModel editarPerfilViewModel = new ViewModelProvider(EditarPerfilActivity.this).get(EditarPerfilViewModel.class);

                String currentPhotoPath = editarPerfilViewModel.getCurrentPhotoPath();
                if(!currentPhotoPath.isEmpty()) {
                    ImageView imvPhoto = findViewById(R.id.imbEditarPerfil);
                    Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
                    imvPhoto.setImageBitmap(bitmap);
                }

                try {
                    int h = (int) getResources().getDimension(R.dimen.img_height_anuncio);
                    Util.scaleImage(currentPhotoPath, -1, 2*h);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                LiveData<Boolean> resultLD = editarPerfilViewModel.editarPerfil(nome, cpf, email, senha, currentPhotoPath);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o cadastro do produto deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(EditarPerfilActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean == true) {
                            Toast.makeText(EditarPerfilActivity.this, "Perfil editado com sucesso", Toast.LENGTH_LONG).show();
                            // indica que a Activity terminou com resultado positivo e a finaliza
                            setResult(RESULT_OK);
                            finish();

                            Intent i = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        else {
                            v.setEnabled(true);
                            Toast.makeText(EditarPerfilActivity.this, "Ocorreu um erro ao editar o perfil", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                Intent i = new Intent(EditarPerfilActivity.this, HomeActivity.class);
                startActivity(i);
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
            Toast.makeText(EditarPerfilActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Se o arquivo foi criado com sucesso...
        if(f != null) {

            // setamos o endereço do arquivo criado dentro do ViewModel
            EditarPerfilViewModel editarPerfilViewModel = new ViewModelProvider(EditarPerfilActivity.this).get(EditarPerfilViewModel.class);
            editarPerfilViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            // Criamos e configuramos o INTENT que dispara a câmera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(EditarPerfilActivity.this, "camila.davi.isabelly.yasmin.domos.fileprovider", f);
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
            Toast.makeText(EditarPerfilActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
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
            EditarPerfilViewModel editarPerfilViewModel = new ViewModelProvider(this).get(EditarPerfilViewModel.class);
            String currentPhotoPath = editarPerfilViewModel.getCurrentPhotoPath();

            // Se a foto foi efetivamente escolhida pelo usuário...
            if(resultCode == Activity.RESULT_OK) {
                ImageButton imvPhoto = findViewById(R.id.imbEditarPerfil);

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
                editarPerfilViewModel.setCurrentPhotoPath("");
            }
        }
    }
}
