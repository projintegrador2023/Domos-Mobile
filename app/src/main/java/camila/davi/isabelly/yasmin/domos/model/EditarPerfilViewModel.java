package camila.davi.isabelly.yasmin.domos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditarPerfilViewModel extends AndroidViewModel {

    String currentPhotoPath = "";

    public EditarPerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public LiveData<Boolean> editarPerfil(String nome, String cpf, String email, String senha, String img) {

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        // Cria uma nova linha de execução (thread). O android obriga que chamadas de rede sejam feitas
        // em uma linha de execução separada da principal.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Executa a nova linha de execução. Dentro dessa linha, iremos realizar as requisições ao
        // servidor web.
        executorService.execute(new Runnable() {
            /**
             * Tudo o que colocármos dentro da função run abaixo será executada dentro da nova linha
             * de execução.
             */
            @Override
            public void run() {

                // Criamos uma instância de ProductsRepository. É dentro dessa classe que estão os
                // métodos que se comunicam com o servidor web.
                DomosRepository domosRepository = new DomosRepository(getApplication());

                // O método addProduct envia os dados de um novo produto ao servidor. Ele retorna
                // um booleano indicando true caso o produto tenha sido cadastrado e false
                // em caso contrário
                boolean b = domosRepository.editarPerfil(nome, email, senha, img);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(b);
            }
        });

        return result;
    }

}

