package camila.davi.isabelly.yasmin.domos.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class AvisosPagingSource extends ListenableFuturePagingSource<Integer, Aviso> {

    DomosRepository domosRepository;
    String importancia;

    Integer initialLoadSize = 0;

    public AvisosPagingSource(DomosRepository domosRepository, String importancia) {
        this.domosRepository = domosRepository;
        this.importancia = importancia;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Aviso> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Aviso>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        // calcula os parâmetros de limit e offset que serão enviados ao servidor web
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
            initialLoadSize = loadParams.getLoadSize();
        }

        Integer offSet = 0;
        if(nextPageNumber == 2) {
            offSet = initialLoadSize;
        }
        else {
            offSet = ((nextPageNumber - 1) * loadParams.getLoadSize()) + (initialLoadSize - loadParams.getLoadSize());
        }

        // cria uma nova linha de execução
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        Integer finalOffSet = offSet;
        Integer finalNextPageNumber = nextPageNumber;

        // executa a nova linha de execução.
        ListenableFuture<LoadResult<Integer, Aviso>> lf = service.submit(new Callable<LoadResult<Integer, Aviso>>() {
            /**
             * Tudo que estiver dentro dessa função será executado na nova linha de execução.
             */
            @Override
            public LoadResult<Integer, Aviso> call() {
                List<Aviso> avisoList = null;
                // envia uma requisição para o servidor web pedindo por uma nova página de dados (bloco de produtos)
                avisoList = domosRepository.loadAvisos(loadParams.getLoadSize(), finalOffSet, importancia);
                Integer nextKey = null;
                if(avisoList.size() >= loadParams.getLoadSize()) {
                    nextKey = finalNextPageNumber + 1;
                }
                // monta uma página do padrão da biblioteca Paging 3.
                return new LoadResult.Page<Integer, Aviso>(avisoList,
                        null,
                        nextKey);
            }
        });

        return lf;
    }
}
