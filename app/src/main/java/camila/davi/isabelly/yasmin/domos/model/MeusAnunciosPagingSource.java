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

import camila.davi.isabelly.yasmin.domos.bd.Anuncio;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class MeusAnunciosPagingSource extends ListenableFuturePagingSource<Integer, Anuncio> {

    DomosRepository domosRepository;
    String tag;
    Integer initialLoadSize = 0;

    public MeusAnunciosPagingSource(DomosRepository domosRepository, String tag) {
        this.domosRepository = domosRepository;
        this.tag = tag;
    }
    public MeusAnunciosPagingSource(DomosRepository domosRepository) {
        this.domosRepository = domosRepository;
    }


    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Anuncio> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, Anuncio>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
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
        ListenableFuture<LoadResult<Integer, Anuncio>> lf = service.submit(new Callable<LoadResult<Integer, Anuncio>>() {
            /**
             * Tudo que estiver dentro dessa função será executado na nova linha de execução.
             */
            @Override
            public LoadResult<Integer, Anuncio> call() {
                List<Anuncio> anuncioList = null;
                // envia uma requisição para o servidor web pedindo por uma nova página de dados (bloco de produtos)
                anuncioList = domosRepository.loadMeusAnuncios(loadParams.getLoadSize(), finalOffSet);
                Integer nextKey = null;
                if(anuncioList.size() >= loadParams.getLoadSize()) {
                    nextKey = finalNextPageNumber + 1;
                }
                // monta uma página do padrão da biblioteca Paging 3.
                return new LoadResult.Page<Integer, Anuncio>(anuncioList,
                        null,
                        nextKey);
            }
        });

        return lf;
    }

}
