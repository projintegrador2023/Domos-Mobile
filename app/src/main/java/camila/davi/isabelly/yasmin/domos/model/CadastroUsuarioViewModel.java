package camila.davi.isabelly.yasmin.domos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import camila.davi.isabelly.yasmin.domos.bd.NumDivCondominio;

public class CadastroUsuarioViewModel extends AndroidViewModel {

    public CadastroUsuarioViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Boolean> cadastro(String cpf, String nome, String email, String senha, String codigoCondominio, String nApto, String divisao) {

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

                // O método login envia os dados de novo usuário ao servidor. Ele retorna
                // um booleano indicando true caso o cadastro de novo usuário tenha sido feito com sucesso e false
                // em caso contrário
                boolean b = domosRepository.register(cpf, nome, email, senha, codigoCondominio, nApto, divisao);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(b);
            }
        });

        return result;
    }

    public LiveData<NumDivCondominio> pegarNumDiv(String codigo_condominio) {

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<NumDivCondominio> result = new MutableLiveData<>();

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

                // O método login envia os dados de novo usuário ao servidor. Ele retorna
                // um booleano indicando true caso o cadastro de novo usuário tenha sido feito com sucesso e false
                // em caso contrário
                NumDivCondominio b = domosRepository.loadNumDiv(codigo_condominio);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(b);
            }
        });

        return result;
    }
}
